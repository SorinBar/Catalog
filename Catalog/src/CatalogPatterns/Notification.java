package CatalogPatterns;

import CatalogAux.Grade;

import java.util.Date;

public class Notification {
    private Date date;
    private Grade grade;

    public Notification(Grade grade) {
        this.grade = grade;
        date = new Date();
    }
    public Date getDate() {
        return date;
    }
    public Grade getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return date + ": partial: " + grade.getPartialScore() +
                ", exam: " + grade.getExamScore();
    }
}
