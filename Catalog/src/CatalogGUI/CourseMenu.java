package CatalogGUI;

import CatalogAux.Group;
import CatalogCourses.Course;
import CatalogCourses.FullCourse;
import CatalogCourses.PartialCourse;
import CatalogUsers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CourseMenu {
    Mediator mediator;
    private final JPanel panel;
    private final JScrollPane studentsPane;
    private final JList<String> studentsList;
    private final DefaultListModel<String> studentsModel;
    private final JListSelect jListSelect;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton removeButton;
    private final JButton backButton;
    private final JButtonClick buttonClick;
    private Course selectedCourse;
    private Group selectedGroup;
    private Student selectedStudent;
    private final JComboBox<String> groupsBox;
    private final JButton groupsButton;
    private final JLabel assistantLabel;
    private final JButton assistantButton;
    private final JComboBoxSelect jComboBoxSelect;

    public CourseMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        studentsModel = new DefaultListModel<String>();
        studentsList = new JList<>(studentsModel);
        studentsPane = new JScrollPane(studentsList);
        jListSelect = new JListSelect();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        backButton = new JButton("Go Back");
        buttonClick = new JButtonClick();
        selectedCourse = null;
        selectedGroup = null;
        selectedStudent = null;
        groupsBox = new JComboBox<String>();
        groupsButton = new JButton("Add Group");
        assistantLabel = new JLabel("Assistant: -");
        assistantButton = new JButton("Set Assistant");
        jComboBoxSelect = new JComboBoxSelect();

        studentsList.addListSelectionListener(jListSelect);
        groupsBox.addActionListener(jComboBoxSelect);
        groupsButton.addActionListener(buttonClick);

        // Font
        Font titleFont = new Font("Open Sans", Font.BOLD, 16);

        // Up panel
        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));

        JLabel titleLabel = new JLabel("Groups");
        titleLabel.setFont(titleFont);
        up.add(titleLabel);

        // Groups panel
        JPanel groupsPanel = new JPanel();
        groupsPanel.setLayout(new BoxLayout(groupsPanel, BoxLayout.X_AXIS));
        groupsPanel.add(groupsBox);
        groupsPanel.add(groupsButton);

        // Assistant panel
        JPanel aPanel = new JPanel();
        aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.X_AXIS));
        aPanel.add(assistantLabel);

        // Students panel
        JPanel studPanel = new JPanel();
        studPanel.setLayout(new BorderLayout());
        studPanel.add(studentsPane, BorderLayout.CENTER);

        // Down panel
        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));

        addButton.addActionListener(buttonClick);
        editButton.addActionListener(buttonClick);
        removeButton.addActionListener(buttonClick);
        backButton.addActionListener(buttonClick);

        editButton.setEnabled(false);
        removeButton.setEnabled(false);

        down.add(addButton);
        down.add(editButton);
        down.add(removeButton);
        down.add(backButton);

        // Main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(up);
        panel.add(groupsPanel);
        panel.add(aPanel);
        panel.add(studPanel);
        panel.add(down);
    }
    public void setSelectedCourse(Course course) {
        selectedCourse = course;
        groupsBox.removeAllItems();
        for (String groupID : course.getGroupsData())
            groupsBox.addItem(groupID);
    }
    public JPanel getPanel() {
        return panel;
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == groupsButton) {
                System.out.println("adauga grupa");
            }
            if (actionEvent.getSource() == addButton) {
                System.out.println("add");
                System.out.println(selectedCourse);
            }
            if (actionEvent.getSource() == editButton) {
                System.out.println("edit Course");
            }
            if (actionEvent.getSource() == removeButton) {
                int option = JOptionPane.showConfirmDialog(mediator.getCatalogApp(),
                        "Remove " + selectedCourse.getName() + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.out.println("deleted");
                }
            }
            if (actionEvent.getSource() == backButton) {
                mediator.showCatalogMenu();
            }
        }
    }
    private class JListSelect implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            if (!listSelectionEvent.getValueIsAdjusting()) {
                if (studentsList.getSelectedValue() != null)
                    selectedStudent = mediator.getUsersDatabase()
                            .getStudent(studentsList.getSelectedValue().substring(0, 13));
                else
                    selectedStudent = null;
            }
        }
    }
    private class JComboBoxSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            studentsModel.clear();
            if (groupsBox.getSelectedIndex() == -1) {
                selectedGroup = null;
                assistantLabel.setText("Assistant: -");
            }
            else {
                selectedGroup = selectedCourse.getGroup((String)groupsBox.getSelectedItem());
                assistantLabel.setText("Assistant: " + selectedGroup.getAssistant());
                studentsModel.addAll(selectedGroup.getStudentsData());
            }
        }
    }
}
