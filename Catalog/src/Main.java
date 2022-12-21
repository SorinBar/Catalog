import CatalogCourses.*;
import CatalogUsers.*;
public class Main {
    public static void main(String[] args) {
        Grade g1 = new Grade();
        g1.setPartialScore(2.3);
        Grade g2 = null;
        try {
            g2 = (Grade) g1.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("problem");
        }
        g1.setExamScore(3.4000000000002);
        g2.setExamScore(3.4000000000001);
        System.out.println(g1.compareTo(g2));
    }

}
