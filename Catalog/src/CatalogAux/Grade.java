package CatalogAux;

import CatalogUsers.Student;

public class Grade implements Comparable, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade() {
        partialScore = null;
        examScore = null;
    }
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
    @Override
    public int compareTo(Object o) {
        double cmp = getTotal() - ((Grade)o).getTotal();
        if (cmp > 0)
            return 1;
        if (cmp < 0)
            return -1;
        return 0;
    }
    @Override
    public Object clone(){
        Grade copy = new Grade();
        copy.setStudent(student);
        copy.setCourse(course);
        copy.setExamScore(examScore + 0.0);
        copy.setPartialScore(partialScore + 0.0);

        return copy;
    }
    @Override
    public String toString() {
        String part, exam;
        if (partialScore == null)
            part = "null";
        else
            part = partialScore.toString();
        if (examScore == null)
            exam = "null";
        else
            exam = examScore.toString();

        return "Grade: " + "partial: " + partialScore + ", exam: " + examScore;
    }
}
