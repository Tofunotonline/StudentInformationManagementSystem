package controller;

import datastructure.MyLinkedList;
import model.Student;
import util.FileHandler;

/**
 * Controller for managing Student operations.
 * Handles CRUD and file loading/saving.
 */
public class StudentController {

    private MyLinkedList<Student> students;
    private final String filePath = "data/students.csv";

    public StudentController() {
        this.students = new MyLinkedList<>();
        loadFromFile();
    }

    /**
     * Load students from CSV file into linked list.
     */
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

    /**
     * Save all students to CSV file.
     */
    public void saveToFile() {
        MyLinkedList<String> lines = new MyLinkedList<>();
        for (Student student : students) {
            lines.add(student.toCsv());
        }
        FileHandler.writeFile(filePath, lines);
    }

    /**
     * Add a new student.
     */
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

    /**
     * Get student by ID.
     */
    public Student getStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Update student info.
     */
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

    /**
     * Delete student by ID.
     */
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

    /**
     * Get all students.
     */
    public MyLinkedList<Student> getAllStudents() {
        return students;
    }

    /**
     * Get total student count.
     */
    public int getStudentCount() {
        return students.size();
    }
}
