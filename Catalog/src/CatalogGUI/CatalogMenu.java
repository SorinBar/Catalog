package CatalogGUI;

import CatalogCourses.Course;
import CatalogUsers.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class CatalogMenu {

    Mediator mediator;
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
    public JPanel getPanel() {
        return panel;
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == addButton) {
                int result = JOptionPane.showConfirmDialog(mediator.getCatalogApp(), addCourseMenu.addPanel,
                        "Add Course", JOptionPane.OK_CANCEL_OPTION);
            }
            if (actionEvent.getSource() == editButton) {
                System.out.println("edit");
            }
            if (actionEvent.getSource() == removeButton) {
                int option = JOptionPane.showConfirmDialog(mediator.getCatalogApp(),
                        "Remove " + selectedCourse.getName() + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    mediator.getCatalog().removeCourse(selectedCourse);
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

        private AddCourseMenu() {
            addPanel = new JPanel();

            addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
            addPanel.setPreferredSize(new Dimension(400, 400));
            addPanel.add(new JLabel("test"));
        }

        private class JTextFieldSelect implements FocusListener {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                JTextField field = (JTextField) (focusEvent.getSource());
                if (field.isEditable() && field.getForeground() == Color.LIGHT_GRAY) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent focusEvent) {
                JTextField field = (JTextField) (focusEvent.getSource());
                if (field.getText().equals("")){
                    field.setForeground(Color.LIGHT_GRAY);
                    field.setText(field.getName());
                }
            }
        }
    }
}
