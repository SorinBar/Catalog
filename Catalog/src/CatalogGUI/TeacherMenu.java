package CatalogGUI;

import CatalogUsers.Teacher;

import javax.swing.*;

public class TeacherMenu {
    Mediator mediator;
    private final JPanel panel;
    private Teacher teacher;
    public TeacherMenu(Mediator mediator) {
        this.mediator = mediator;
        panel = new JPanel();


        // Main panel
        panel.add(new JLabel("testtt"));
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
