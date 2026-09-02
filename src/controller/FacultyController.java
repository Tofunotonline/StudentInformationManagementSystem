package controller;

import datastructure.MyLinkedList;
import model.Faculty;
import util.FileHandler;

/**
 * Controller for managing Faculty operations.
 */
public class FacultyController {

    private MyLinkedList<Faculty> faculties;
    private final String filePath = "data/faculties.csv";

    public FacultyController() {
        this.faculties = new MyLinkedList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        MyLinkedList<String> lines = FileHandler.readFile(filePath);
        for (String line : lines) {
            try {
                Faculty faculty = Faculty.fromCsv(line);
                faculties.add(faculty);
            } catch (Exception e) {
                System.out.println("Error parsing faculty: " + line);
            }
        }
    }

    public void saveToFile() {
        MyLinkedList<String> lines = new MyLinkedList<>();
        for (Faculty faculty : faculties) {
            lines.add(faculty.toCsv());
        }
        FileHandler.writeFile(filePath, lines);
    }

    public boolean addFaculty(Faculty faculty) {
        for (Faculty f : faculties) {
            if (f.getFacultyId().equals(faculty.getFacultyId())) {
                System.out.println("Faculty ID already exists!");
                return false;
            }
        }
        faculties.add(faculty);
        saveToFile();
        return true;
    }

    public Faculty getFacultyById(String id) {
        for (Faculty f : faculties) {
            if (f.getFacultyId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public boolean updateFaculty(String id, String name, String dean) {
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getFacultyId().equals(id)) {
                Faculty f = faculties.get(i);
                f.setName(name);
                f.setDean(dean);
                faculties.set(i, f);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteFaculty(String id) {
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getFacultyId().equals(id)) {
                faculties.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public MyLinkedList<Faculty> getAllFaculties() {
        return faculties;
    }

    public int getFacultyCount() {
        return faculties.size();
    }
}
