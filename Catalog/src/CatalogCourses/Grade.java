package CatalogCourses;

import CatalogUsers.Student;

public class Grade implements Comparable, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public Double getTotal() {
        return partialScore + examScore;
    }

    public int compareTo(Object o) {
        double cmp = getTotal() - ((Grade)o).getTotal();
        if (cmp > 0)
            return 1;
        if (cmp < 0)
            return -1;
        return 0;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
