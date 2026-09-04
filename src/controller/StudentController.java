package controller;

import datastructure.MyLinkedList;
import model.Student;
import util.FileHandler;

// Controller for managing Student operations
// Handles CRUD and file loading/saving
public class StudentController {

    private MyLinkedList<Student> students;
    private final String filePath = "data/students.csv";

    public StudentController() {
        this.students = new MyLinkedList<>();
        loadFromFile();
    }

    // load students from file
    private void loadFromFile() {
        MyLinkedList<String> lines = FileHandler.readFile(filePath);
        for (String line : lines) {
            try {
                Student student = Student.fromCsv(line);
                students.add(student);
            } catch (Exception e) {
                System.out.println("Error parsing student: " + line);
            }
        }
    }

    // save to file
    public void saveToFile() {
        MyLinkedList<String> lines = new MyLinkedList<>();
        for (Student student : students) {
            lines.add(student.toCsv());
        }
        FileHandler.writeFile(filePath, lines);
    }

    // add student
    public boolean addStudent(Student student) {
        if (student == null) {
            System.out.println("Student cannot be null!");
            return false;
        }
        // Check duplicate ID
        for (Student s : students) {
            if (s.getStudentId().equals(student.getStudentId())) {
                System.out.println("Student ID already exists!");
                return false;
            }
        }
        students.add(student);
        saveToFile();
        return true;
    }

    // get student by ID
    public Student getStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    // update student info
    public boolean updateStudent(String id, String name, String email, String faculty) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(id)) {
                Student s = students.get(i);
                s.setName(name);
                s.setEmail(email);
                s.setFaculty(faculty);
                students.set(i, s);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // delete student by ID
    public boolean deleteStudent(String id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(id)) {
                students.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // calc GPA based on enrollments and subjects
    public boolean calculateAndUpdateGpa(String studentId, MyLinkedList<model.Enrollment> enrollments, MyLinkedList<model.Subject> subjects) {
        Student s = getStudentById(studentId);
        if (s == null) return false;

        double totalPoints = 0;
        int totalCredits = 0;

        for (model.Enrollment e : enrollments) {
            // Only count if it's for this student and has a valid grade
            if (e.getStudentId().equals(studentId) && e.getGrade() >= 0) {
                // Find subject to get credits
                for (model.Subject sub : subjects) {
                    if (sub.getSubjectId().equals(e.getSubjectId())) {
                        totalPoints += e.getGrade() * sub.getCredits();
                        totalCredits += sub.getCredits();
                        break;
                    }
                }
            }
        }

        double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0.0;
        s.setGpa(gpa);
        saveToFile();
        return true;
    }

    // get all students
    public MyLinkedList<Student> getAllStudents() {
        return students;
    }

    // total student count
    public int getStudentCount() {
        return students.size();
    }
}
