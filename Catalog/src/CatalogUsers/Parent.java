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
        if (!stud.getFather().equals(this))
            return;
        if (!stud.getMother().equals(this))
            return;
        System.out.print(notification.getDate() + ": ");
        System.out.println(notification.getGrade().getTotal());
    }
}