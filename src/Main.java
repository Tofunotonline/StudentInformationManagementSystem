import datastructure.MyLinkedList;
import datastructure.MyStack;
import model.Student;

/**
 * Main entry point for Student Information Management System.
 * This class starts the application.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Student Information Management System");
        System.out.println("==========================================");
        System.out.println();

        // Quick test: LinkedList
        MyLinkedList<Student> students = new MyLinkedList<>();
        students.add(new Student("S001", "Nguyen Van An", "an@uni.edu", "IT", 3.25));
        students.add(new Student("S002", "Tran Thi Binh", "binh@uni.edu", "IT", 3.50));

        System.out.println("Students loaded: " + students.size());
        for (Student s : students) {
            System.out.println("  - " + s.getName() + " (GPA: " + s.getGpa() + ")");
        }

        // Quick test: Stack
        MyStack<String> actionHistory = new MyStack<>();
        actionHistory.push("Registered S001 to SUB001");
        actionHistory.push("Registered S002 to SUB003");
        System.out.println("\nAction history: " + actionHistory);
        System.out.println("Last action: " + actionHistory.pop());

        System.out.println("\n--- System ready! Full menu coming soon ---");
    }
}
