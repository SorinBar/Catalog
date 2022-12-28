package CatalogDatabase;

import CatalogAux.Grade;
import CatalogPatterns.Notification;
import CatalogUsers.Parent;
import CatalogUsers.User;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsDatabase {
    UsersDatabase usersDatabase;
    HashMap<String, ArrayList<Notification>> notifications;
    public final String path = "src/CatalogDatabase/Database/notifications.txt";

    public NotificationsDatabase (UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
        notifications = new HashMap<>();
    }
    public void addNotification (String parentCnp, Notification notification){
        ArrayList<Notification> notificationArray = notifications.get(parentCnp);
        if (notificationArray == null) {
            notificationArray = new ArrayList<>();
            notificationArray.add(notification);
            notifications.put(parentCnp, notificationArray);
        }
        else
            notificationArray.add(notification);
    }
    public ArrayList<Notification> getNotification (String parentCnp) {
        return notifications.get(parentCnp);
    }
    public void load() {
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading notifications database!");
            return;
        }
        int index = 0;
        int parentsNr;
        int notificationsNr;
        String parentCnp;
        String aux;
        Grade grade;
        Notification notification;

        parentsNr = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < parentsNr; i++) {
            parentCnp = lines.get(index++);
            notificationsNr = Integer.parseInt(lines.get(index++));
            for (int j = 0; j < notificationsNr; j++) {
                grade = new Grade();
                grade.setStudent(usersDatabase.getStudent(lines.get(index++)));
                grade.setCourse(lines.get(index++));
                aux = lines.get(index++);
                if (!aux.equals("null"))
                    grade.setPartialScore(Double.parseDouble(aux));
                aux = lines.get(index++);
                if (!aux.equals("null"))
                    grade.setExamScore(Double.parseDouble(aux));
                notification = new Notification(grade);
                notification.setDate(lines.get(index++));
                addNotification(parentCnp, notification);
            }
        }

        System.out.println(notifications);
    }
    public void update() {
        System.out.println("update");
    }
}
