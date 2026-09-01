package model;

/**
 * Model class representing a Subject (Course).
 * Stores subject information like credits, prerequisites, etc.
 */
public class Subject {
    private String subjectId;
    private String name;
    private int credits;
    private String faculty;
    private String prerequisite; // prerequisite subject ID, empty if none
    private int maxCapacity;
    private int currentEnrollment;

    public Subject(String subjectId, String name, int credits, String faculty,
                   String prerequisite, int maxCapacity) {
        this.subjectId = subjectId;
        this.name = name;
        this.credits = credits;
        this.faculty = faculty;
        this.prerequisite = prerequisite;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
    }

    public Subject(String subjectId, String name, int credits, String faculty,
                   String prerequisite, int maxCapacity, int currentEnrollment) {
        this.subjectId = subjectId;
        this.name = name;
        this.credits = credits;
        this.faculty = faculty;
        this.prerequisite = prerequisite;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = currentEnrollment;
    }

    // Getters
    public String getSubjectId() { return subjectId; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public String getFaculty() { return faculty; }
    public String getPrerequisite() { return prerequisite; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }

    // Setters
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }
    public void setName(String name) { this.name = name; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }

    /**
     * Check if the subject still has available slots.
     */
    public boolean hasAvailableSlots() {
        return currentEnrollment < maxCapacity;
    }

    /**
     * Increment enrollment count when a student registers.
     */
    public void incrementEnrollment() {
        if (currentEnrollment < maxCapacity) {
            currentEnrollment++;
        }
    }

    /**
     * Decrement enrollment count when a student drops.
     */
    public void decrementEnrollment() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        }
    }

    /**
     * Convert to CSV format for file storage.
     */
    public String toCsv() {
        return subjectId + "," + name + "," + credits + "," + faculty + ","
                + prerequisite + "," + maxCapacity + "," + currentEnrollment;
    }

    /**
     * Create Subject from CSV line.
     */
    public static Subject fromCsv(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 7) {
            throw new IllegalArgumentException("Invalid CSV format for Subject: " + csvLine);
        }
        return new Subject(
            parts[0].trim(),
            parts[1].trim(),
            Integer.parseInt(parts[2].trim()),
            parts[3].trim(),
            parts[4].trim(),
            Integer.parseInt(parts[5].trim()),
            Integer.parseInt(parts[6].trim())
        );
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-30s | %-7d | %-15s | %-12s | %d/%d |",
                subjectId, name, credits, faculty, prerequisite, currentEnrollment, maxCapacity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subject subject = (Subject) obj;
        return subjectId.equals(subject.subjectId);
    }

    @Override
    public int hashCode() {
        return subjectId.hashCode();
    }
}
