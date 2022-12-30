package CatalogUsers;

import CatalogPatterns.Notification;
import CatalogPatterns.Observer;

public class Parent extends User implements Observer {
    public Parent(String firstName, String lastName, String CNP) {
        super(firstName, lastName, CNP);
    }
    @Override
    public void update(Notification notification) {
        Student stud = notification.getGrade().getStudent();
        if (stud.getFather() == null)
            return;
        if (stud.getMother() == null)
            return;
        if (stud.getFather().equals(this) || stud.getMother().equals(this)) {
            System.out.println(notification);
        }
    }
}