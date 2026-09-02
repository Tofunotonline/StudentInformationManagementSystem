package controller;

import datastructure.MyLinkedList;
import model.Subject;
import util.FileHandler;

/**
 * Controller for managing Subject operations.
 */
public class SubjectController {

    private MyLinkedList<Subject> subjects;
    private final String filePath = "data/subjects.csv";

    public SubjectController() {
        this.subjects = new MyLinkedList<>();
        loadFromFile();
    }

    private void loadFromFile() {
        MyLinkedList<String> lines = FileHandler.readFile(filePath);
        for (String line : lines) {
            try {
                Subject subject = Subject.fromCsv(line);
                subjects.add(subject);
            } catch (Exception e) {
                System.out.println("Error parsing subject: " + line);
            }
        }
    }

    public void saveToFile() {
        MyLinkedList<String> lines = new MyLinkedList<>();
        for (Subject subject : subjects) {
            lines.add(subject.toCsv());
        }
        FileHandler.writeFile(filePath, lines);
    }

    public boolean addSubject(Subject subject) {
        for (Subject s : subjects) {
            if (s.getSubjectId().equals(subject.getSubjectId())) {
                System.out.println("Subject ID already exists!");
                return false;
            }
        }
        subjects.add(subject);
        saveToFile();
        return true;
    }

    public Subject getSubjectById(String id) {
        for (Subject s : subjects) {
            if (s.getSubjectId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public boolean updateSubject(String id, String name, int credits, String faculty,
                                  String prerequisite, int maxCapacity) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(id)) {
                Subject s = subjects.get(i);
                s.setName(name);
                s.setCredits(credits);
                s.setFaculty(faculty);
                s.setPrerequisite(prerequisite);
                s.setMaxCapacity(maxCapacity);
                subjects.set(i, s);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteSubject(String id) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(id)) {
                subjects.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public MyLinkedList<Subject> getAllSubjects() {
        return subjects;
    }

    public int getSubjectCount() {
        return subjects.size();
    }
}
