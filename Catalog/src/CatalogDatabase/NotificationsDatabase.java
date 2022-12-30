package CatalogDatabase;

import CatalogAux.Grade;
import CatalogPatterns.Notification;
import CatalogUsers.Parent;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationsDatabase {
    private UsersDatabase usersDatabase;
    private HashMap<String, ArrayList<Notification>> notifications;
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
    public ArrayList<String> getNotificationsData (String parentCnp) {
        ArrayList<String> notificationsData = new ArrayList<>();
        ArrayList<Notification> notArray = notifications.get(parentCnp);
        if (notArray == null)
            return notificationsData;
        String data;
        for (Notification not : notArray) {
            data = not.getDate();
            data += ", Student: " + not.getGrade().getStudent();
            data += ", Course: " + not.getGrade().getCourse();
            if (not.getGrade().getPartialScore() != null) {
                data += ", Partial: " + not.getGrade().getPartialScore();
            }
            if (not.getGrade().getExamScore() != null) {
                data += ", Exam: " + not.getGrade().getExamScore();
            }
            notificationsData.add(data);
        }
        return notificationsData;
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
    }
    public void update() {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            Grade grade;
            printWriter.println(notifications.size());
            for(Map.Entry<String, ArrayList<Notification>> entry : notifications.entrySet()) {
                printWriter.println(entry.getKey());
                printWriter.println(entry.getValue().size());
                for (Notification notification : entry.getValue()) {
                    grade = notification.getGrade();
                    printWriter.println(grade.getStudent().getCNP());
                    printWriter.println(grade.getCourse());
                    printWriter.println(grade.getPartialScore());
                    printWriter.println(grade.getExamScore());
                    printWriter.println(notification.getDate());
                }
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating notifications!");
        }
    }
    public void setParents() {
        for (Map.Entry<String, Parent> entry : usersDatabase.getParents().entrySet()) {
           entry.getValue().setNotificationsDatabase(this);
        }
    }
    // Testing
    public void print() {
        System.out.println(notifications);
    }
}
