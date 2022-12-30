package CatalogUsers;

import CatalogDatabase.NotificationsDatabase;
import CatalogMain.Catalog;
import CatalogPatterns.Notification;
import CatalogPatterns.Observer;

public class Parent extends User implements Observer {
    NotificationsDatabase notificationsDatabase;

    public Parent(String firstName, String lastName, String CNP) {
        super(firstName, lastName, CNP);
        notificationsDatabase = null;
    }
    public void setNotificationsDatabase(NotificationsDatabase notificationsDatabase) {
        this.notificationsDatabase = notificationsDatabase;
    }
    @Override
    public void update(Notification notification) {
        Student stud = notification.getGrade().getStudent();
        if (stud.getFather() == null)
            return;
        if (stud.getMother() == null)
            return;
        if (stud.getFather().equals(this) || stud.getMother().equals(this)) {
            notificationsDatabase.addNotification(getCNP(), notification);
        }
    }
}