package CatalogGUI;

import CatalogUsers.Student;

import javax.swing.*;

public class StudentMenu {
    private Mediator mediator;
    private final JPanel panel;
    private final JList<String> gradesList;
    private final DefaultListModel<String> gradesModel;
    private final JScrollPane gradesPane;

    public StudentMenu(Mediator mediator) {
        this.mediator = mediator;
        panel = new JPanel();
        gradesModel = new DefaultListModel<>();
        gradesList = new JList<>(gradesModel);
        gradesPane = new JScrollPane(gradesList);

        // Main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(gradesPane);
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setStudent(Student student) {
        gradesModel.clear();
        gradesModel.addAll(mediator.getCatalog().getStudentData(student));
    }
}