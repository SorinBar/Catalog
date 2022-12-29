package CatalogAux;

import CatalogUsers.*;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private Assistant assistant;
    private String ID;
    private Comparator<Student> comp;
    ArrayList<String> studentsData;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
        studentsData = new ArrayList<>();
    }
    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
        comp = null;
        studentsData = new ArrayList<>();
    }
    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public Assistant getAssistant() {
        return assistant;
    }
    public String getID() {
        return ID;
    }
    public ArrayList<String> getStudentsData() {
        return studentsData;
    }
    @Override
    public boolean add(Student student) {
        if (contains(student))
            return false;

        boolean status = super.add(student);
        if (status) {
            studentsData.add(student.toString());
            Collections.sort(studentsData);
            if (comp != null)
                sort(comp);
        }

        return status;
    }
}
