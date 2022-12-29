package CatalogGUI;

import CatalogCourses.Course;
import CatalogCourses.FullCourse;
import CatalogCourses.PartialCourse;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CatalogMenu {

    private Mediator mediator;
    private final JPanel panel;
    private final JScrollPane coursesPane;
    private final JList<String> coursesList;
    private final DefaultListModel<String> coursesModel;
    private final JListSelect jListSelect;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton removeButton;
    private final JButton backButton;
    private final JButtonClick buttonClick;
    private Course selectedCourse;
    private final AddCourseMenu addCourseMenu;

    public CatalogMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        coursesModel = new DefaultListModel<String>();
        coursesList = new JList<>(coursesModel);
        coursesPane = new JScrollPane(coursesList);
        jListSelect = new JListSelect();
        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        removeButton = new JButton("Remove");
        backButton = new JButton("Go Back");
        buttonClick = new JButtonClick();
        selectedCourse = null;
        addCourseMenu = new AddCourseMenu();

        coursesModel.addAll(mediator.getCatalog().getCoursesNames());
        coursesList.addListSelectionListener(jListSelect);

        // Font
        Font titleFont = new Font("Open Sans", Font.BOLD, 16);

        // Up panel
        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));

        JLabel titleLabel = new JLabel("Courses");
        titleLabel.setFont(titleFont);
        up.add(titleLabel);

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
        panel.add(coursesPane);
        panel.add(down);
    }
    public void updateCourses() {

    }
    public void updateTeachers() {
        // Update current teachers
        addCourseMenu.teacherBox.removeAllItems();
        for (String teacherData : mediator.getUsersDatabase().getTeachersData())
            addCourseMenu.teacherBox.addItem(teacherData);
    }

    public JPanel getPanel() {
        updateTeachers();
        return panel;
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == addButton) {
                int option = JOptionPane.showConfirmDialog(mediator.getCatalogApp(), addCourseMenu.addPanel,
                        "Add Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    if (addCourseMenu.nameField.getText().isBlank()) {
                        JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                "Course name should not be empty!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else if (addCourseMenu.teacherBox.getSelectedIndex() != -1){
                        Course newCourse;
                        String courseName;
                        String teacherCNP;
                        int credit;

                        courseName = addCourseMenu.nameField.getText();
                        credit = addCourseMenu.creditBox.getSelectedIndex() + 1;
                        teacherCNP = ((String) (addCourseMenu.teacherBox.getSelectedItem()))
                                .substring(0, 13);

                        if (addCourseMenu.typeBox.getSelectedIndex() == 0) {
                            newCourse = new FullCourse.FullCourseBuilder(courseName)
                                    .teacher(mediator.getUsersDatabase().getTeacher(teacherCNP))
                                    .credit(credit)
                                    .build();
                        }
                        else {
                            newCourse = new PartialCourse.PartialCourseBuilder(courseName)
                                    .teacher(mediator.getUsersDatabase().getTeacher(teacherCNP))
                                    .credit(credit)
                                    .build();
                        }
                        mediator.getCatalog().addCourse(newCourse);
                        // Update courses
                        coursesModel.clear();
                        coursesModel.addAll(mediator.getCatalog().getCoursesNames());
                    }
                }
                addCourseMenu.refresh();
            }
            if (actionEvent.getSource() == editButton) {
                mediator.showCourseMenu(selectedCourse);
            }
            if (actionEvent.getSource() == removeButton) {
                int option = JOptionPane.showConfirmDialog(mediator.getCatalogApp(),
                        "Remove " + selectedCourse.getName() + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    mediator.getCatalog().removeCourse(selectedCourse);
                    // Update courses
                    coursesModel.clear();
                    coursesModel.addAll(mediator.getCatalog().getCoursesNames());
                    // Reset buttons
                    editButton.setEnabled(false);
                    removeButton.setEnabled(false);
                }
            }
            if (actionEvent.getSource() == backButton) {
                mediator.showAdminMenu();
            }
        }
    }

    private class JListSelect implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            if (!listSelectionEvent.getValueIsAdjusting()) {
                selectedCourse = mediator.getCatalog().getCourse(coursesList.getSelectedValue());
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        }
    }

    private class AddCourseMenu {
        private JPanel addPanel;

        private final JComboBox<String> typeBox;
        private final JComboBox<Integer> creditBox;
        private final JComboBox<String> teacherBox;
        private final JTextField nameField;
        private final String[] types = {"Full Course", "Partial Course"};
        private final Integer[] credits = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        private AddCourseMenu() {
            addPanel = new JPanel();
            typeBox = new JComboBox<>(types);
            teacherBox = new JComboBox<>(mediator.getUsersDatabase().getTeachersData().toArray(new String[0]));
            nameField = new JTextField();
            creditBox = new JComboBox<>(credits);

            addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
            addPanel.setPreferredSize(new Dimension(280, 180));

            JPanel typePanel = new JPanel(new BorderLayout());
            JPanel creditPanel = new JPanel(new BorderLayout());
            JPanel teacherBoxPanel = new JPanel(new BorderLayout());
            JPanel namePanel = new JPanel(new BorderLayout());

            typePanel.add(new JLabel("Type:"), BorderLayout.WEST);
            creditPanel.add(new JLabel("Credit:"), BorderLayout.WEST);
            teacherBoxPanel.add(new JLabel("Teacher:"), BorderLayout.WEST);
            namePanel.add(new JLabel("Course name:"), BorderLayout.WEST);

            addPanel.add(typePanel);
            addPanel.add(typeBox);
            addPanel.add(creditPanel);
            addPanel.add(creditBox);
            addPanel.add(teacherBoxPanel);
            addPanel.add(teacherBox);
            addPanel.add(namePanel);
            addPanel.add(nameField);
        }
        private void refresh() {
            typeBox.setSelectedIndex(0);
            creditBox.setSelectedIndex(0);
            if (teacherBox.getSelectedIndex() != -1)
                teacherBox.setSelectedIndex(0);
            nameField.setText("");
        }
    }
}
