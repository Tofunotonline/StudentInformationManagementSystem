package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import datastructure.MyLinkedList;

/**
 * Utility class for reading and writing CSV files.
 * Handles all file I/O operations for the system.
 */
public class FileHandler {

    /**
     * Read all lines from a CSV file.
     * Returns a MyLinkedList of strings (each string is one line).
     */
    public static MyLinkedList<String> readFile(String filePath) {
        MyLinkedList<String> lines = new MyLinkedList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return lines;
    }

    /**
     * Write all lines to a CSV file (overwrites existing content).
     */
    public static void writeFile(String filePath, MyLinkedList<String> lines) {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Append a single line to a CSV file.
     */
    public static void appendToFile(String filePath, String line) {
        File file = new File(filePath);

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }

    /**
     * Check if a file exists.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
