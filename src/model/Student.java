package model;

/**
 * Model class representing a Student.
 * Stores student information and enrolled subjects.
 */
public class Student {
    private String studentId;
    private String name;
    private String email;
    private String faculty;
    private double gpa;

    public Student(String studentId, String name, String email, String faculty) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.gpa = 0.0;
    }

    // Constructor with GPA (used when loading from file)
    public Student(String studentId, String name, String email, String faculty, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.gpa = gpa;
    }

    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getFaculty() { return faculty; }
    public double getGpa() { return gpa; }

    // Setters
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    /**
     * Convert to CSV format for file storage.
     */
    public String toCsv() {
        return studentId + "," + name + "," + email + "," + faculty + "," + gpa;
    }

    /**
     * Create Student from CSV line.
     */
    public static Student fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid CSV format for Student: " + csvLine);
        }
        return new Student(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim(),
            parts[3].trim(),
            Double.parseDouble(parts[4].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-25s | %-15s | %-5.2f |",
                studentId, name, email, faculty, gpa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
