package CatalogGUI;

import javax.swing.*;
import java.awt.*;

public class CatalogMenu {

    Mediator mediator;
    private JPanel panel;
    private JScrollPane coursesPane;
    private JList<String> coursesList;
    private DefaultListModel<String> coursesModel;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JRadioButton teacherButton;
    private JRadioButton assistantButton;
    private JRadioButton studentButton;
    private JRadioButton parentButton;

    public CatalogMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        coursesModel = new DefaultListModel<String>();
        coursesList = new JList<>(coursesModel);
        coursesPane = new JScrollPane(coursesList);
        addButton = new JButton("+");
        editButton = new JButton("S");
        removeButton = new JButton("X");
        teacherButton = new JRadioButton("Teacher");
        assistantButton = new JRadioButton("Assistant");
        studentButton = new JRadioButton("Student");
        parentButton = new JRadioButton("Parent");

        coursesModel.addAll(mediator.getCatalog().getCoursesNames());
        // Left Panel
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JPanel upLeft = new JPanel();
        upLeft.setLayout(new BoxLayout(upLeft, BoxLayout.X_AXIS));
        JLabel coursesLabel = new JLabel("Courses   ");

        upLeft.add(coursesLabel);
        upLeft.add(addButton);
        upLeft.add(editButton);
        upLeft.add(removeButton);

        left.add(upLeft);
        left.add(coursesPane);
        // Right Panel
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JPanel upRight = new JPanel();
        upRight.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        ButtonGroup group = new ButtonGroup();
        group.add(teacherButton);
        group.add(assistantButton);
        group.add(studentButton);
        group.add(parentButton);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        JLabel usersLabel = new JLabel("Users");
        upRight.add(teacherButton, constraints);
        upRight.add(assistantButton, constraints);
        upRight.add(studentButton, constraints);
        upRight.add(parentButton, constraints);

        right.add(upRight);


        // Main Panel
        panel.setLayout(new GridLayout(1, 2));
        panel.add(left);
        panel.add(right);
    }

    public JPanel getPanel() {
        return panel;
    }
}
