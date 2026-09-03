package model;

/**
 * Model class representing an Enrollment record.
 * Links a Student to a Subject they registered for.
 */
public class Enrollment {
    private String enrollmentId;
    private String studentId;
    private String subjectId;
    private String semester;
    private String status; // ACTIVE, DROPPED, COMPLETED
    private double grade; // -1 means not graded yet

    public Enrollment(String enrollmentId, String studentId, String subjectId,
                      String semester, String status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.semester = semester;
        this.status = status;
        this.grade = -1.0;
    }

    public Enrollment(String enrollmentId, String studentId, String subjectId,
                      String semester, String status, double grade) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.semester = semester;
        this.status = status;
        this.grade = grade;
    }

    // Getters
    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getSubjectId() { return subjectId; }
    public String getSemester() { return semester; }
    public String getStatus() { return status; }
    public double getGrade() { return grade; }

    // Setters
    public void setEnrollmentId(String enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setSemester(String semester) { this.semester = semester; }
    public void setStatus(String status) { this.status = status; }
    public void setGrade(double grade) { this.grade = grade; }

    /**
     * Convert to CSV format for file storage.
     */
    public String toCsv() {
        return enrollmentId + "," + studentId + "," + subjectId + "," + semester + "," + status + "," + grade;
    }

    /**
     * Create Enrollment from CSV line.
     */
    public static Enrollment fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid CSV format for Enrollment: " + csvLine);
        }
        double grade = -1.0;
        if (parts.length > 5 && !parts[5].trim().isEmpty()) {
            grade = Double.parseDouble(parts[5].trim());
        }
        return new Enrollment(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim(),
            parts[3].trim(),
            parts[4].trim(),
            grade
        );
    }

    @Override
    public String toString() {
        String gradeStr = (grade >= 0) ? String.format("%.1f", grade) : "N/A";
        return String.format("| %-12s | %-10s | %-10s | %-10s | %-10s | %-5s |",
                enrollmentId, studentId, subjectId, semester, status, gradeStr);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment enrollment = (Enrollment) obj;
        return enrollmentId.equals(enrollment.enrollmentId);
    }

    @Override
    public int hashCode() {
        return enrollmentId.hashCode();
    }
}
