package CatalogPatterns;

import CatalogAux.Grade;

import java.util.Date;

public class Notification {
    private String date;
    private Grade grade;

    public Notification(Grade grade) {
        this.grade = grade;
        date = new Date().toString();
    }
    public String getDate() {
        return date;
    }
    public Grade getGrade() {
        return grade;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + " " + grade;
    }
}
