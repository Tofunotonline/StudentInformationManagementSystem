package view;

import java.util.Scanner;

import controller.StudentController;
import controller.SubjectController;
import controller.FacultyController;
import controller.EnrollmentController;
import datastructure.MyLinkedList;
import model.Student;
import util.InputValidator;
import util.SortUtils;

/**
 * Console view - main menu
 */
public class ConsoleView {

    private Scanner scanner;
    private StudentController studentCtrl;
    private SubjectController subjectCtrl;
    private FacultyController facultyCtrl;
    private EnrollmentController enrollmentCtrl;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
        this.studentCtrl = new StudentController();
        this.subjectCtrl = new SubjectController();
        this.facultyCtrl = new FacultyController();
        this.enrollmentCtrl = new EnrollmentController();
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1: studentMenu(); break;
                case 2: System.out.println("Coming soon..."); break;
                case 3: System.out.println("Coming soon..."); break;
                case 4: System.out.println("Coming soon..."); break;
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("\n==========================================");
        System.out.println("  Student Information Management System");
        System.out.println("==========================================");
        System.out.println("  1. Student Management");
        System.out.println("  2. Subject Management");
        System.out.println("  3. Faculty Management");
        System.out.println("  4. Enrollment Management");
        System.out.println("  0. Exit");
        System.out.println("==========================================");
    }

    // ==================== STUDENT MENU ====================

    private void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("  1. View all students");
            System.out.println("  2. Add student");
            System.out.println("  3. Update student");
            System.out.println("  4. Delete student");
            System.out.println("  5. Search student");
            System.out.println("  6. Sort students by GPA");
            System.out.println("  7. Sort students by name");
            System.out.println("  8. View student profile");
            System.out.println("  0. Back");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1: viewAllStudents(); break;
                case 2: addStudent(); break;
                case 3: updateStudent(); break;
                case 4: deleteStudent(); break;
                case 5: searchStudent(); break;
                case 6: sortStudentsByGpa(); break;
                case 7: sortStudentsByName(); break;
                case 8: viewStudentProfile(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllStudents() {
        MyLinkedList<Student> students = studentCtrl.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n" + String.format("| %-10s | %-20s | %-25s | %-15s | %-5s |",
                "ID", "Name", "Email", "Faculty", "GPA"));
        System.out.println("-".repeat(87));
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("Total: " + students.size() + " students");
    }

    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = InputValidator.readString(scanner, "Student ID (e.g. S006): ");
        String name = InputValidator.readString(scanner, "Name: ");
        String email = InputValidator.readString(scanner, "Email: ");
        String faculty = InputValidator.readString(scanner, "Faculty: ");

        Student student = new Student(id, name, email, faculty);
        if (studentCtrl.addStudent(student)) {
            System.out.println("Student added successfully!");
        }
    }

    private void updateStudent() {
        String id = InputValidator.readString(scanner, "Enter student ID to update: ");
        Student existing = studentCtrl.getStudentById(id);
        if (existing == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("Current: " + existing);
        String name = InputValidator.readString(scanner, "New name: ");
        String email = InputValidator.readString(scanner, "New email: ");
        String faculty = InputValidator.readString(scanner, "New faculty: ");
        if (studentCtrl.updateStudent(id, name, email, faculty)) {
            System.out.println("Updated successfully!");
        }
    }

    private void deleteStudent() {
        String id = InputValidator.readString(scanner, "Enter student ID to delete: ");
        if (studentCtrl.deleteStudent(id)) {
            System.out.println("Deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    private void searchStudent() {
        String keyword = InputValidator.readString(scanner, "Search by name or ID: ");
        MyLinkedList<Student> students = studentCtrl.getAllStudents();
        boolean found = false;
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                s.getStudentId().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("No results found.");
    }

    private void sortStudentsByGpa() {
        MyLinkedList<Student> students = studentCtrl.getAllStudents();
        SortUtils.bubbleSort(students, (a, b) -> Double.compare(b.getGpa(), a.getGpa()));
        System.out.println("Sorted by GPA (highest first):");
        viewAllStudents();
    }

    private void sortStudentsByName() {
        MyLinkedList<Student> students = studentCtrl.getAllStudents();
        SortUtils.insertionSort(students, (a, b) -> a.getName().compareTo(b.getName()));
        System.out.println("Sorted by name (A-Z):");
        viewAllStudents();
    }

    private void viewStudentProfile() {
        String id = InputValidator.readString(scanner, "Enter student ID: ");
        Student s = studentCtrl.getStudentById(id);
        if (s == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.println("\n=== Student Profile ===");
        System.out.println("ID:      " + s.getStudentId());
        System.out.println("Name:    " + s.getName());
        System.out.println("Email:   " + s.getEmail());
        System.out.println("Faculty: " + s.getFaculty());
        System.out.println("GPA:     " + s.getGpa());
    }
}
