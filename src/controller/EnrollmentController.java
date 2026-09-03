package controller;

import datastructure.MyLinkedList;
import model.Enrollment;
import util.FileHandler;

/**
 * Controller for course registration.
 */
public class EnrollmentController {

    private MyLinkedList<Enrollment> enrollments;
    private final String filePath = "data/enrollments.csv";
    private int nextId;

    public EnrollmentController() {
        this.enrollments = new MyLinkedList<>();
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
                System.out.println("Course dropped successfully!");
                return true;
            }
        }
        System.out.println("Enrollment not found!");
        return false;
    }

    // TODO: add undo redo later

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
