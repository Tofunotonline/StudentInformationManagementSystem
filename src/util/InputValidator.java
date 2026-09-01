package util;

import java.util.Scanner;

/**
 * Utility class for validating user input.
 * Prevents invalid data from entering the system.
 */
public class InputValidator {

    /**
     * Check if a string is not null and not empty.
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Validate student ID format (e.g., S001, S002).
     */
    public static boolean isValidStudentId(String id) {
        if (id == null) return false;
        return id.matches("S\\d{3}");
    }

    /**
     * Validate subject ID format (e.g., SUB001, SUB002).
     */
    public static boolean isValidSubjectId(String id) {
        if (id == null) return false;
        return id.matches("SUB\\d{3}");
    }

    /**
     * Validate faculty ID format (e.g., F001, F002).
     */
    public static boolean isValidFacultyId(String id) {
        if (id == null) return false;
        return id.matches("F\\d{3}");
    }

    /**
     * Validate email format (basic check).
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    /**
     * Validate that a number is a positive integer.
     */
    public static boolean isPositiveInteger(String input) {
        try {
            return Integer.parseInt(input.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate GPA range (0.0 - 4.0).
     */
    public static boolean isValidGpa(String input) {
        try {
            double gpa = Double.parseDouble(input.trim());
            return gpa >= 0.0 && gpa <= 4.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validate credits range (1 - 6).
     */
    public static boolean isValidCredits(String input) {
        try {
            int credits = Integer.parseInt(input.trim());
            return credits >= 1 && credits <= 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Read a non-empty string from user with prompt.
     */
    public static String readString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("  -> Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    /**
     * Read an integer from user with prompt.
     */
    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  -> Invalid number. Please try again.");
            }
        }
    }

    /**
     * Read a double from user with prompt.
     */
    public static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  -> Invalid number. Please try again.");
            }
        }
    }
}
