package CatalogAux;

import CatalogUsers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Group extends ArrayList<Student> {
    private Assistant assistant;
    private String ID;
    private Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
        comp = null;
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

    @Override
    public boolean add(Student student) {
        if (contains(student))
            return false;

        boolean status = super.add(student);
        if (status && comp != null)
            Collections.sort(this, comp);

        return status;
    }
}
