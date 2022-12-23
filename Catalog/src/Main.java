import CatalogCourses.*;
import CatalogUsers.*;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        Group group = new Group("321CC", new Assistant("marcel", "popescu"), new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1.getLastName().compareTo(s2.getLastName()) == 0)
                    return s1.getFirstName().compareTo(s2.getFirstName());
                else
                    return s1.getLastName().compareTo(s2.getLastName());
            }
        });
        group.add(new Student("AASorin", "B"));
        group.add(new Student("Amalia", "B"));
        group.add(new Student("David", "C"));
        group.add(new Student("Aicusor", "D"));

        System.out.println(group);
    }

}
