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

    public int compareTo(Object o) {
        return 0;
    }
}
