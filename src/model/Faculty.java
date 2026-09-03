package model;

/**
 * Faculty model class
 */
public class Faculty {
    private String facultyId;
    private String name;
    private String dean;
    private int totalStudents;

    public Faculty(String facultyId, String name, String dean) {
        this.facultyId = facultyId;
        this.name = name;
        this.dean = dean;
        this.totalStudents = 0;
    }

    public Faculty(String facultyId, String name, String dean, int totalStudents) {
        this.facultyId = facultyId;
        this.name = name;
        this.dean = dean;
        this.totalStudents = totalStudents;
    }

    // Getters
    public String getFacultyId() { return facultyId; }
    public String getName() { return name; }
    public String getDean() { return dean; }
    public int getTotalStudents() { return totalStudents; }

    // Setters
    public void setFacultyId(String facultyId) { this.facultyId = facultyId; }
    public void setName(String name) { this.name = name; }
    public void setDean(String dean) { this.dean = dean; }
    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }

    /**
     * Convert to CSV format for file storage.
     */
    public String toCsv() {
        return facultyId + "," + name + "," + dean + "," + totalStudents;
    }

    /**
     * Create Faculty from CSV line.
     */
    public static Faculty fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid CSV format for Faculty: " + csvLine);
        }
        return new Faculty(
            parts[0].trim(),
            parts[1].trim(),
            parts[2].trim(),
            Integer.parseInt(parts[3].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-30s | %-20s | %-8d |",
                facultyId, name, dean, totalStudents);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Faculty faculty = (Faculty) obj;
        return facultyId.equals(faculty.facultyId);
    }

    @Override
    public int hashCode() {
        return facultyId.hashCode();
    }
}
