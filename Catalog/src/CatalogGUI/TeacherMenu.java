package CatalogGUI;

import CatalogUsers.Teacher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherMenu {
    private Mediator mediator;
    private final JPanel panel;
    private Teacher teacher;
    private final JList<String> gradesList;
    private final DefaultListModel<String> gradesModel;
    private final JScrollPane gradesPane;
    private final JButton validateGradesButton;

    public TeacherMenu(Mediator mediator) {
        this.mediator = mediator;
        panel = new JPanel();
        gradesModel = new DefaultListModel<>();
        gradesList = new JList<>(gradesModel);
        gradesPane = new JScrollPane(gradesList);
        validateGradesButton = new JButton("Validate All Grades");
        validateGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == validateGradesButton) {
                    teacher.accept(mediator.getScoreVisitor());
                    gradesModel.clear();
                    gradesModel.addAll(mediator.getScoreVisitor().getScoresData(teacher));
                }
            }
        });

        // Down panel
        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));
        down.add(validateGradesButton);

        // Main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(gradesPane);
        panel.add(down);
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        gradesModel.clear();
        gradesModel.addAll(mediator.getScoreVisitor().getScoresData(teacher));
    }
}
