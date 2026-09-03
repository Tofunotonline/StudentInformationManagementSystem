package controller;

import datastructure.MyLinkedList;
import datastructure.MyStack;
import model.Enrollment;
import util.FileHandler;

/**
 * Controller for course registration.
 * Also handles undo/redo using stack.
 */
public class EnrollmentController {

    private MyLinkedList<Enrollment> enrollments;
    private MyStack<String> undoStack;  // stores actions for undo
    private MyStack<String> redoStack;  // stores actions for redo
    private final String filePath = "data/enrollments.csv";
    private int nextId;

    public EnrollmentController() {
        this.enrollments = new MyLinkedList<>();
        this.undoStack = new MyStack<>();
        this.redoStack = new MyStack<>();
        loadFromFile();
        this.nextId = enrollments.size() + 1;
    }

    private void loadFromFile() {
        MyLinkedList<String> lines = FileHandler.readFile(filePath);
        for (String line : lines) {
            try {
                enrollments.add(Enrollment.fromCsv(line));
            } catch (Exception e) {
                System.out.println("Error parsing enrollment: " + line);
            }
        }
    }

    public void saveToFile() {
        MyLinkedList<String> lines = new MyLinkedList<>();
        for (Enrollment e : enrollments) {
            lines.add(e.toCsv());
        }
        FileHandler.writeFile(filePath, lines);
    }

    /**
     * Register a student for a subject.
     */
    public boolean registerCourse(String studentId, String subjectId, String semester) {
        // check if already enrolled
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId) &&
                e.getSubjectId().equals(subjectId) &&
                e.getStatus().equals("ACTIVE")) {
                System.out.println("Already enrolled in this subject!");
                return false;
            }
        }

        String enrollId = "E" + String.format("%03d", nextId++);
        Enrollment enrollment = new Enrollment(enrollId, studentId, subjectId, semester, "ACTIVE");
        enrollments.add(enrollment);
        saveToFile();

        // save to undo stack
        undoStack.push("REGISTER:" + enrollId + ":" + studentId + ":" + subjectId + ":" + semester);
        redoStack.clear(); // clear redo when new action happens

        System.out.println("Registered successfully! ID: " + enrollId);
        return true;
    }

    /**
     * Drop a course.
     */
    public boolean dropCourse(String studentId, String subjectId) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getStudentId().equals(studentId) &&
                e.getSubjectId().equals(subjectId) &&
                e.getStatus().equals("ACTIVE")) {

                e.setStatus("DROPPED");
                enrollments.set(i, e);
                saveToFile();

                undoStack.push("DROP:" + e.getEnrollmentId() + ":" + studentId + ":" + subjectId);
                redoStack.clear();

                System.out.println("Course dropped successfully!");
                return true;
            }
        }
        System.out.println("Enrollment not found!");
        return false;
    }

    /**
     * Update grade for an active enrollment.
     */
    public boolean updateGrade(String studentId, String subjectId, double grade) {
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getStudentId().equals(studentId) && 
                e.getSubjectId().equals(subjectId) && 
                e.getStatus().equals("ACTIVE")) {
                
                e.setGrade(grade);
                enrollments.set(i, e);
                saveToFile();
                System.out.println("Grade updated successfully!");
                return true;
            }
        }
        System.out.println("Active enrollment not found!");
        return false;
    }

    /**
     * Undo last action.
     */
    public boolean undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo!");
            return false;
        }

        String action = undoStack.pop();
        String[] parts = action.split(":");

        if (parts[0].equals("REGISTER")) {
            // undo register = remove enrollment
            String enrollId = parts[1];
            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getEnrollmentId().equals(enrollId)) {
                    enrollments.remove(i);
                    break;
                }
            }
            saveToFile();
            redoStack.push(action);
            System.out.println("Undo: registration removed");
        } else if (parts[0].equals("DROP")) {
            // undo drop = set back to ACTIVE
            String enrollId = parts[1];
            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getEnrollmentId().equals(enrollId)) {
                    enrollments.get(i).setStatus("ACTIVE");
                    enrollments.set(i, enrollments.get(i));
                    break;
                }
            }
            saveToFile();
            redoStack.push(action);
            System.out.println("Undo: course re-activated");
        }
        return true;
    }

    /**
     * Redo last undone action.
     */
    public boolean redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo!");
            return false;
        }

        String action = redoStack.pop();
        String[] parts = action.split(":");

        if (parts[0].equals("REGISTER")) {
            // redo register = add back
            String enrollId = parts[1];
            Enrollment e = new Enrollment(enrollId, parts[2], parts[3], parts[4], "ACTIVE");
            enrollments.add(e);
            saveToFile();
            undoStack.push(action);
            System.out.println("Redo: registration restored");
        } else if (parts[0].equals("DROP")) {
            // redo drop = set to DROPPED again
            String enrollId = parts[1];
            for (int i = 0; i < enrollments.size(); i++) {
                if (enrollments.get(i).getEnrollmentId().equals(enrollId)) {
                    enrollments.get(i).setStatus("DROPPED");
                    enrollments.set(i, enrollments.get(i));
                    break;
                }
            }
            saveToFile();
            undoStack.push(action);
            System.out.println("Redo: course dropped again");
        }
        return true;
    }

    public MyLinkedList<Enrollment> getStudentEnrollments(String studentId) {
        MyLinkedList<Enrollment> result = new MyLinkedList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentId().equals(studentId)) {
                result.add(e);
            }
        }
        return result;
    }

    public MyLinkedList<Enrollment> getSubjectEnrollments(String subjectId) {
        MyLinkedList<Enrollment> result = new MyLinkedList<>();
        for (Enrollment e : enrollments) {
            if (e.getSubjectId().equals(subjectId) && e.getStatus().equals("ACTIVE")) {
                result.add(e);
            }
        }
        return result;
    }

    public MyLinkedList<Enrollment> getAllEnrollments() {
        return enrollments;
    }
}
