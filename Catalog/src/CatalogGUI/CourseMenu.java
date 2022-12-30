package CatalogGUI;

import CatalogAux.Grade;
import CatalogAux.Group;
import CatalogCourses.Course;
import CatalogUsers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CourseMenu {
    private final Mediator mediator;
    private final JPanel panel;
    private final JScrollPane studentsPane;
    private final JList<String> studentsList;
    private final DefaultListModel<String> studentsModel;
    private final JListSelect jListSelect;
    private final JButtonClick buttonClick;
    private Course selectedCourse;
    private Group selectedGroup;
    private Student selectedStudent;
    private final JLabel titleLabel;
    private final JComboBox<String> groupsBox;
    private final JButton groupsButton;
    private final JLabel assistantLabel;
    private final JComboBoxSelect jComboBoxSelect;
    private final AddGroupMenu addGroupMenu;
    private final JButton addButton;
    private final JButton gradeButton;
    private final JButton backButton;
    private final AddStudentMenu addStudentMenu;
    private final AddGradeMenu addGradeMenu;

    public CourseMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        studentsModel = new DefaultListModel<>();
        studentsList = new JList<>(studentsModel);
        studentsPane = new JScrollPane(studentsList);
        jListSelect = new JListSelect();
        buttonClick = new JButtonClick();
        selectedCourse = null;
        selectedGroup = null;
        selectedStudent = null;
        titleLabel = new JLabel();
        groupsBox = new JComboBox<>();
        groupsButton = new JButton("Add Group");
        assistantLabel = new JLabel("Assistant: -");
        jComboBoxSelect = new JComboBoxSelect();
        addGroupMenu = new AddGroupMenu();
        addButton = new JButton("Add Student");
        gradeButton = new JButton("Add Grade");
        backButton = new JButton("Go Back");
        addStudentMenu = new AddStudentMenu();
        addGradeMenu = new AddGradeMenu();

        studentsList.addListSelectionListener(jListSelect);
        groupsBox.addActionListener(jComboBoxSelect);
        groupsButton.addActionListener(buttonClick);

        // Font
        Font titleFont = new Font("Open Sans", Font.BOLD, 14);

        // Up panel
        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));

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

        gradeButton.addActionListener(buttonClick);
        addButton.addActionListener(buttonClick);
        backButton.addActionListener(buttonClick);

        gradeButton.setEnabled(false);

        down.add(gradeButton);
        down.add(addButton);
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
        addButton.setEnabled(false);
        selectedCourse = course;
        groupsBox.removeAllItems();
        for (String groupID : selectedCourse.getGroupsData())
            groupsBox.addItem(groupID);
        titleLabel.setText(course.getName() + " - " + course.getTeacher());
    }
    public JPanel getPanel() {
        return panel;
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == groupsButton) {
                addGroupMenu.update();
                int result = JOptionPane.showConfirmDialog(mediator.getCatalogApp(), addGroupMenu.addPanel,
                        "Enter Group Info", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if (addGroupMenu.groupIdField.getText().isBlank())
                        JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                "Group ID should not be empty!",
                                "Wrong Input", JOptionPane.ERROR_MESSAGE);
                    else {
                        String assistantCnp = (String) addGroupMenu.assistansBox.getSelectedItem();
                        assistantCnp = assistantCnp.substring(0, 13);
                        selectedCourse.addGroup(addGroupMenu.groupIdField.getText(),
                                mediator.getUsersDatabase().getAssistant(assistantCnp));
                        // Update Groups
                        groupsBox.removeAllItems();
                        for (String groupID : selectedCourse.getGroupsData())
                            groupsBox.addItem(groupID);
                        selectedGroup = selectedCourse.getGroup(addGroupMenu.groupIdField.getText());
                    }
                }
            }
            if (actionEvent.getSource() == addButton) {
                addStudentMenu.update();
                int result = JOptionPane.showConfirmDialog(mediator.getCatalogApp(), addStudentMenu.addPanel,
                        "Choose Student", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    if (addStudentMenu.studentsBox.getSelectedItem() != null) {
                        String studentCnp = (String) addStudentMenu.studentsBox.getSelectedItem();
                        studentCnp = studentCnp.substring(0, 13);
                        if (selectedGroup != null) {
                            // Add student
                            Student student = mediator.getUsersDatabase().getStudent(studentCnp);
                            selectedCourse.addStudent(selectedGroup.getID(), student);
                            // Add student grade
                            Grade grade = new Grade();
                            grade.setCourse(selectedCourse.getName());
                            grade.setStudent(student);
                            selectedCourse.addGrade(grade);
                            // Update students list
                            studentsModel.clear();
                            studentsModel.addAll(selectedGroup.getStudentsData());
                        }
                    }
                }
            }
            if (actionEvent.getSource() == gradeButton) {
                addGradeMenu.update(selectedCourse.getGrade(selectedStudent));
                int result = JOptionPane.showConfirmDialog(mediator.getCatalogApp(), addGradeMenu.gradePanel,
                        "Add Grade", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    if (addGradeMenu.isInputValid()) {
                        if (addGradeMenu.partScoreField.isEditable() &&
                                !addGradeMenu.partScoreField.getText().isBlank()) {
                            mediator.getScoreVisitor().addPartialScore(selectedGroup.getAssistant(),
                                    selectedStudent, selectedCourse.getName(),
                                    Double.parseDouble(addGradeMenu.partScoreField.getText()));
                        }
                        if (addGradeMenu.examScoreField.isEditable() &&
                                !addGradeMenu.examScoreField.getText().isBlank()) {
                            mediator.getScoreVisitor().addExamScore(selectedCourse.getTeacher(),
                                    selectedStudent, selectedCourse.getName(),
                                    Double.parseDouble(addGradeMenu.examScoreField.getText()));
                        }
                    } else {
                        JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                "Partial score ∈ [0, 6]!\nExam score ∈ [0, 4]!",
                                "Wrong Input", JOptionPane.ERROR_MESSAGE);
                    }
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
                if (studentsList.getSelectedValue() != null) {
                    selectedStudent = mediator.getUsersDatabase()
                            .getStudent(studentsList.getSelectedValue().substring(0, 13));
                    gradeButton.setEnabled(true);
                }
                else {
                    selectedStudent = null;
                    gradeButton.setEnabled(false);
                }
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
                addButton.setEnabled(false);
            }
            else {
                selectedGroup = selectedCourse.getGroup((String)groupsBox.getSelectedItem());
                assistantLabel.setText("Assistant: " + selectedGroup.getAssistant());
                if (!selectedGroup.isEmpty())
                    studentsModel.addAll(selectedGroup.getStudentsData());
                addButton.setEnabled(true);
            }
        }
    }

    private class AddGroupMenu {
        private final JPanel addPanel;
        private final JComboBox<String> assistansBox;
        private final JTextField groupIdField;
        private AddGroupMenu() {
            addPanel = new JPanel();
            assistansBox = new JComboBox<>();
            groupIdField = new JTextField();

            update();

            JPanel a = new JPanel();
            JPanel b = new JPanel();
            JPanel c = new JPanel();
            JPanel d = new JPanel();

            a.setLayout(new BorderLayout());
            a.add(new JLabel("Group ID:"), BorderLayout.WEST);
            b.setLayout(new GridBagLayout());
            b.add(groupIdField);
            c.setLayout(new BorderLayout());
            c.add(new JLabel("Assistant:"), BorderLayout.WEST);
            d.setLayout(new GridBagLayout());
            d.add(assistansBox);

            groupIdField.setPreferredSize(new Dimension(250, 20));

            // Main panel
            addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
            addPanel.add(a);
            addPanel.add(b);
            addPanel.add(c);
            addPanel.add(d);
        }
        private void update() {
            groupIdField.setText("");
            assistansBox.removeAllItems();
            for (String data : mediator.getUsersDatabase().getAssistantsData()) {
                assistansBox.addItem(data);
            }
        }
    }
    private class AddStudentMenu {
        private final JPanel addPanel;
        private final JComboBox<String> studentsBox;
        private AddStudentMenu() {
            addPanel = new JPanel();
            studentsBox = new JComboBox<>();

            update();

            // Main panel
            addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.X_AXIS));
            addPanel.add(studentsBox);
        }
        private void update() {
            studentsBox.removeAllItems();
            for (String data : mediator.getUsersDatabase().getStudentsData())
                studentsBox.addItem(data);
        }
    }
    private class AddGradeMenu {
        private final JPanel gradePanel;
        private final JTextField partScoreField;
        private final JTextField examScoreField;

        private AddGradeMenu() {
            gradePanel = new JPanel();
            partScoreField = new JTextField();
            examScoreField = new JTextField();

            // Partial panel
            JPanel partPanel = new JPanel(new BorderLayout());
            partPanel.add(new JLabel("Partial:"), BorderLayout.WEST);
            partPanel.add(partScoreField, BorderLayout.EAST);
            partScoreField.setPreferredSize(new Dimension(150, 20));

            // Exam panel
            JPanel examPanel = new JPanel(new BorderLayout());
            examPanel.add(new JLabel("Exam:"), BorderLayout.WEST);
            examPanel.add(examScoreField, BorderLayout.EAST);
            examScoreField.setPreferredSize(new Dimension(150, 20));

            // Main panel
            gradePanel.setLayout(new BoxLayout(gradePanel, BoxLayout.Y_AXIS));
            gradePanel.add(partPanel);
            gradePanel.add(examPanel);
        }

        private void update(Grade studGrade) {
            if (studGrade.getPartialScore() != null) {
                partScoreField.setText(studGrade.getPartialScore().toString());
                partScoreField.setEditable(false);
                partScoreField.setForeground(Color.LIGHT_GRAY);
            }
            else {
                partScoreField.setText("");
                partScoreField.setEditable(true);
                partScoreField.setForeground(Color.BLACK);
            }
            if (studGrade.getExamScore() != null) {
                examScoreField.setText(studGrade.getExamScore().toString());
                examScoreField.setEditable(false);
                examScoreField.setForeground(Color.LIGHT_GRAY);
            }
            else {
                examScoreField.setText("");
                examScoreField.setEditable(true);
                examScoreField.setForeground(Color.BLACK);
            }
        }
        private boolean isInputValid() {
            double partScore = 0.0;
            double examScore = 0.0;
            if (partScoreField.isEditable() && !partScoreField.getText().isBlank()) {
                try {
                    partScore = Double.parseDouble(partScoreField.getText());
                } catch (Exception e) {
                    return false;
                }
            }
            if (examScoreField.isEditable() && !examScoreField.getText().isBlank()) {
                try {
                    examScore = Double.parseDouble(examScoreField.getText());
                } catch (Exception e) {
                    return false;
                }
            }
            if (partScore < 0.0 || 6.0 < partScore)
                return false;
            if (examScore < 0.0 || 4.0 < examScore)
                return false;

            return true;
        }
    }
}
