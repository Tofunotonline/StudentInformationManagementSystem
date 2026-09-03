package view;

import java.util.Scanner;

import controller.StudentController;
import controller.SubjectController;
import controller.FacultyController;
import controller.EnrollmentController;
import datastructure.MyLinkedList;
import model.Student;
import model.Subject;
import model.Faculty;
import model.Enrollment;
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
                case 2: subjectMenu(); break;
                case 3: facultyMenu(); break;
                case 4: enrollmentMenu(); break;
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

        // show enrolled courses
        MyLinkedList<Enrollment> enrollments = enrollmentCtrl.getStudentEnrollments(id);
        System.out.println("\nEnrolled courses:");
        if (enrollments.isEmpty()) {
            System.out.println("  No courses enrolled.");
        } else {
            for (Enrollment e : enrollments) {
                Subject sub = subjectCtrl.getSubjectById(e.getSubjectId());
                String subName = (sub != null) ? sub.getName() : "Unknown";
                System.out.println("  - " + e.getSubjectId() + " " + subName + " [" + e.getStatus() + "]");
            }
        }
    }

    // ==================== SUBJECT MENU ====================

    private void subjectMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Subject Management ---");
            System.out.println("  1. View all subjects");
            System.out.println("  2. Add subject");
            System.out.println("  3. Update subject");
            System.out.println("  4. Delete subject");
            System.out.println("  5. Search subject");
            System.out.println("  6. View subject info");
            System.out.println("  0. Back");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1: viewAllSubjects(); break;
                case 2: addSubject(); break;
                case 3: updateSubject(); break;
                case 4: deleteSubject(); break;
                case 5: searchSubject(); break;
                case 6: viewSubjectInfo(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllSubjects() {
        MyLinkedList<Subject> subjects = subjectCtrl.getAllSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }
        System.out.println("\n" + String.format("| %-10s | %-30s | %-7s | %-15s | %-12s | %-7s |",
                "ID", "Name", "Credits", "Faculty", "Prerequisite", "Slots"));
        System.out.println("-".repeat(98));
        for (Subject s : subjects) {
            System.out.println(s);
        }
    }

    private void addSubject() {
        System.out.println("\n--- Add New Subject ---");
        String id = InputValidator.readString(scanner, "Subject ID (e.g. SUB009): ");
        String name = InputValidator.readString(scanner, "Name: ");
        int credits = InputValidator.readInt(scanner, "Credits: ");
        String faculty = InputValidator.readString(scanner, "Faculty: ");
        String prereq = InputValidator.readString(scanner, "Prerequisite (none if no): ");
        int capacity = InputValidator.readInt(scanner, "Max capacity: ");

        Subject subject = new Subject(id, name, credits, faculty, prereq, capacity);
        if (subjectCtrl.addSubject(subject)) {
            System.out.println("Subject added!");
        }
    }

    private void updateSubject() {
        String id = InputValidator.readString(scanner, "Enter subject ID to update: ");
        Subject existing = subjectCtrl.getSubjectById(id);
        if (existing == null) {
            System.out.println("Subject not found!");
            return;
        }
        System.out.println("Current: " + existing);
        String name = InputValidator.readString(scanner, "New name: ");
        int credits = InputValidator.readInt(scanner, "New credits: ");
        String faculty = InputValidator.readString(scanner, "New faculty: ");
        String prereq = InputValidator.readString(scanner, "New prerequisite: ");
        int capacity = InputValidator.readInt(scanner, "New max capacity: ");
        if (subjectCtrl.updateSubject(id, name, credits, faculty, prereq, capacity)) {
            System.out.println("Updated!");
        }
    }

    private void deleteSubject() {
        String id = InputValidator.readString(scanner, "Enter subject ID to delete: ");
        if (subjectCtrl.deleteSubject(id)) {
            System.out.println("Deleted!");
        } else {
            System.out.println("Subject not found!");
        }
    }

    private void searchSubject() {
        String keyword = InputValidator.readString(scanner, "Search by name or ID: ");
        MyLinkedList<Subject> subjects = subjectCtrl.getAllSubjects();
        boolean found = false;
        for (Subject s : subjects) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                s.getSubjectId().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("No results found.");
    }

    private void viewSubjectInfo() {
        String id = InputValidator.readString(scanner, "Enter subject ID: ");
        Subject s = subjectCtrl.getSubjectById(id);
        if (s == null) {
            System.out.println("Subject not found!");
            return;
        }
        System.out.println("\n=== Subject Info ===");
        System.out.println("ID:           " + s.getSubjectId());
        System.out.println("Name:         " + s.getName());
        System.out.println("Credits:      " + s.getCredits());
        System.out.println("Faculty:      " + s.getFaculty());
        System.out.println("Prerequisite: " + s.getPrerequisite());
        System.out.println("Enrollment:   " + s.getCurrentEnrollment() + "/" + s.getMaxCapacity());
    }

    // ==================== FACULTY MENU ====================

    private void facultyMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Faculty Management ---");
            System.out.println("  1. View all faculties");
            System.out.println("  2. Add faculty");
            System.out.println("  3. Update faculty");
            System.out.println("  4. Delete faculty");
            System.out.println("  0. Back");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1: viewAllFaculties(); break;
                case 2: addFaculty(); break;
                case 3: updateFaculty(); break;
                case 4: deleteFaculty(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllFaculties() {
        MyLinkedList<Faculty> faculties = facultyCtrl.getAllFaculties();
        if (faculties.isEmpty()) {
            System.out.println("No faculties found.");
            return;
        }
        System.out.println("\n" + String.format("| %-10s | %-30s | %-20s | %-8s |",
                "ID", "Name", "Dean", "Students"));
        System.out.println("-".repeat(80));
        for (Faculty f : faculties) {
            System.out.println(f);
        }
    }

    private void addFaculty() {
        System.out.println("\n--- Add New Faculty ---");
        String id = InputValidator.readString(scanner, "Faculty ID (e.g. F004): ");
        String name = InputValidator.readString(scanner, "Name: ");
        String dean = InputValidator.readString(scanner, "Dean: ");

        Faculty faculty = new Faculty(id, name, dean);
        if (facultyCtrl.addFaculty(faculty)) {
            System.out.println("Faculty added!");
        }
    }

    private void updateFaculty() {
        String id = InputValidator.readString(scanner, "Enter faculty ID to update: ");
        Faculty existing = facultyCtrl.getFacultyById(id);
        if (existing == null) {
            System.out.println("Faculty not found!");
            return;
        }
        String name = InputValidator.readString(scanner, "New name: ");
        String dean = InputValidator.readString(scanner, "New dean: ");
        if (facultyCtrl.updateFaculty(id, name, dean)) {
            System.out.println("Updated!");
        }
    }

    private void deleteFaculty() {
        String id = InputValidator.readString(scanner, "Enter faculty ID to delete: ");
        if (facultyCtrl.deleteFaculty(id)) {
            System.out.println("Deleted!");
        } else {
            System.out.println("Faculty not found!");
        }
    }

    // ==================== ENROLLMENT MENU ====================

    private void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("  1. Register course");
            System.out.println("  2. Drop course");
            System.out.println("  3. View my enrollments");
            System.out.println("  4. Undo last action");
            System.out.println("  5. Redo last action");
            System.out.println("  0. Back");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1: registerCourse(); break;
                case 2: dropCourse(); break;
                case 3: viewEnrollments(); break;
                case 4: enrollmentCtrl.undo(); break;
                case 5: enrollmentCtrl.redo(); break;
                case 0: back = true; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void registerCourse() {
        String studentId = InputValidator.readString(scanner, "Student ID: ");
        if (studentCtrl.getStudentById(studentId) == null) {
            System.out.println("Student not found!");
            return;
        }
        String subjectId = InputValidator.readString(scanner, "Subject ID: ");
        if (subjectCtrl.getSubjectById(subjectId) == null) {
            System.out.println("Subject not found!");
            return;
        }
        String semester = InputValidator.readString(scanner, "Semester (e.g. 2026-1): ");
        enrollmentCtrl.registerCourse(studentId, subjectId, semester);
    }

    private void dropCourse() {
        String studentId = InputValidator.readString(scanner, "Student ID: ");
        String subjectId = InputValidator.readString(scanner, "Subject ID to drop: ");
        enrollmentCtrl.dropCourse(studentId, subjectId);
    }

    private void viewEnrollments() {
        String studentId = InputValidator.readString(scanner, "Student ID: ");
        MyLinkedList<Enrollment> list = enrollmentCtrl.getStudentEnrollments(studentId);
        if (list.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        System.out.println("\n" + String.format("| %-12s | %-10s | %-10s | %-10s | %-10s |",
                "EnrollID", "Student", "Subject", "Semester", "Status"));
        System.out.println("-".repeat(65));
        for (Enrollment e : list) {
            System.out.println(e);
        }
    }
}
