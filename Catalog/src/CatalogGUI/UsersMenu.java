package CatalogGUI;

import CatalogUsers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class UsersMenu{
    Mediator mediator;
    private final JPanel panel;
    private final JRadioButton teacherButton;
    private final JRadioButton assistantButton;
    private final JRadioButton studentButton;
    private final JRadioButton parentButton;
    private final JRadioSelect radioSelect;
    private final JScrollPane usersPane;
    private final JList<String> usersList;
    private final DefaultListModel<String> usersModel;
    private final JButton addButton;
    private final JButton backButton;
    private final JButtonClick buttonClick;
    private UserFactory.UserType userType;
    private final JPanel addPanel;
    private final JTextField cnpField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField fatherCnpField;
    private final JTextField motherCnpField;
    private final JTextFieldSelect textFieldSelect;

    public UsersMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        teacherButton = new JRadioButton("Teacher");
        assistantButton = new JRadioButton("Assistant");
        studentButton = new JRadioButton("Student");
        parentButton = new JRadioButton("Parent");
        radioSelect = new JRadioSelect();
        usersModel = new DefaultListModel<String>();
        usersList = new JList<>(usersModel);
        usersPane = new JScrollPane(usersList);
        addButton = new JButton("Add User");
        backButton = new JButton("Go Back");
        buttonClick = new JButtonClick();
        addPanel = new JPanel();
        cnpField = new JTextField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        fatherCnpField = new JTextField();
        motherCnpField = new JTextField();
        textFieldSelect = new JTextFieldSelect();


        userType = UserFactory.UserType.Teacher;
        usersModel.addAll(mediator.getUsersDatabase().getTeachersData());

        cnpField.setName("CNP");
        cnpField.setText(cnpField.getName());
        cnpField.setForeground(Color.LIGHT_GRAY);
        firstNameField.setName("First Name");
        firstNameField.setText(firstNameField.getName());
        firstNameField.setForeground(Color.LIGHT_GRAY);
        lastNameField.setName("Last Name");
        lastNameField.setText(lastNameField.getName());
        lastNameField.setForeground(Color.LIGHT_GRAY);
        fatherCnpField.setName("Father CNP");
        fatherCnpField.setText(fatherCnpField.getName());
        fatherCnpField.setForeground(Color.LIGHT_GRAY);
        motherCnpField.setName("Mother CNP");
        motherCnpField.setText(motherCnpField.getName());
        motherCnpField.setForeground(Color.LIGHT_GRAY);

        cnpField.addFocusListener(textFieldSelect);
        firstNameField.addFocusListener(textFieldSelect);
        lastNameField.addFocusListener(textFieldSelect);
        fatherCnpField.addFocusListener(textFieldSelect);
        motherCnpField.addFocusListener(textFieldSelect);

        teacherButton.setSelected(true);
        fatherCnpField.setEditable(false);
        motherCnpField.setEditable(false);

        // Up panel
        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up, BoxLayout.X_AXIS));

        teacherButton.addActionListener(radioSelect);
        assistantButton.addActionListener(radioSelect);
        studentButton.addActionListener(radioSelect);
        parentButton.addActionListener(radioSelect);

        ButtonGroup group = new ButtonGroup();
        group.add(teacherButton);
        group.add(assistantButton);
        group.add(studentButton);
        group.add(parentButton);

        up.add(teacherButton);
        up.add(assistantButton);
        up.add(studentButton);
        up.add(parentButton);

        // Down panel
        JPanel down = new JPanel();
        down.setLayout(new BoxLayout(down, BoxLayout.X_AXIS));

        addButton.addActionListener(buttonClick);
        backButton.addActionListener(buttonClick);

        down.add(addButton);
        down.add(backButton);

        // Main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(up);
        panel.add(usersPane);
        panel.add(down);

        // Add panel
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setPreferredSize(new Dimension(100, 120));
        addPanel.add(cnpField);
        addPanel.add(firstNameField);
        addPanel.add(lastNameField);
        addPanel.add(fatherCnpField);
        addPanel.add(motherCnpField);
    }
    public JPanel getPanel() {
        return panel;
    }
    private boolean isInputValid() {
        if (cnpField.getForeground() == Color.LIGHT_GRAY)
            return false;
        if (firstNameField.getForeground() == Color.LIGHT_GRAY)
            return false;
        if (lastNameField.getForeground() == Color.LIGHT_GRAY)
            return false;
        if (cnpField.getText().length() != 13)
            return false;
        for (int i = 0; i < cnpField.getText().length(); i++)
            if (!Character.isDigit(cnpField.getText().charAt(i)))
                return false;
        if (userType == UserFactory.UserType.Student) {
            if (fatherCnpField.getForeground() == Color.LIGHT_GRAY)
                return false;
            if (motherCnpField.getForeground() == Color.LIGHT_GRAY)
                return false;
            for (int i = 0; i < fatherCnpField.getText().length(); i++)
                if (!Character.isDigit(fatherCnpField.getText().charAt(i)))
                    return false;
            for (int i = 0; i < motherCnpField.getText().length(); i++)
                if (!Character.isDigit(motherCnpField.getText().charAt(i)))
                    return false;
        }

        return true;
    }
    private class JRadioSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JRadioButton radioButton = (JRadioButton) (actionEvent.getSource());
            if (radioButton == teacherButton) {
                userType = UserFactory.UserType.Teacher;
                usersModel.clear();
                usersModel.addAll(mediator.getUsersDatabase().getTeachersData());
                fatherCnpField.setEditable(false);
                motherCnpField.setEditable(false);
            }
            if (radioButton == assistantButton) {
                userType = UserFactory.UserType.Assistant;
                usersModel.clear();
                usersModel.addAll(mediator.getUsersDatabase().getAssistantsData());
                fatherCnpField.setEditable(false);
                motherCnpField.setEditable(false);
            }
            if (radioButton == studentButton) {
                userType = UserFactory.UserType.Student;
                usersModel.clear();
                usersModel.addAll(mediator.getUsersDatabase().getStudentsData());
                fatherCnpField.setEditable(true);
                motherCnpField.setEditable(true);
            }
            if (radioButton == parentButton) {
                userType = UserFactory.UserType.Parent;
                usersModel.clear();
                usersModel.addAll(mediator.getUsersDatabase().getParentsData());
                fatherCnpField.setEditable(false);
                motherCnpField.setEditable(false);
            }
        }
    }
    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == addButton) {
                // Add user
                int result = JOptionPane.showConfirmDialog(null, addPanel,
                        "Enter " + userType + " Credentials", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (isInputValid()) {
                        User user = UserFactory.getUser(userType, firstNameField.getText(),
                                lastNameField.getText(), cnpField.getText());
                        if (userType == UserFactory.UserType.Teacher) {
                            if (mediator.getUsersDatabase().getTeacher(cnpField.getText()) != null) {
                                JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                        "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                mediator.getUsersDatabase().add((Teacher) user);
                            }
                            usersModel.clear();
                            usersModel.addAll(mediator.getUsersDatabase().getTeachersData());
                        }
                        if (userType == UserFactory.UserType.Assistant) {
                            if (mediator.getUsersDatabase().getAssistant(cnpField.getText()) != null) {
                                JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                        "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                mediator.getUsersDatabase().add((Assistant) user);
                            }
                            usersModel.clear();
                            usersModel.addAll(mediator.getUsersDatabase().getAssistantsData());
                        }
                        if (userType == UserFactory.UserType.Student) {
                            Student student = (Student) user;
                            if (user != null) {
                                student.setFather(mediator.getUsersDatabase().getParent(fatherCnpField.getText()));
                                student.setMother(mediator.getUsersDatabase().getParent(motherCnpField.getText()));
                            }
                            if (mediator.getUsersDatabase().getStudent(cnpField.getText()) != null) {
                                JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                        "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                if (student.getFather() == null || student.getMother() == null) {
                                    JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                            "Father/Mother CNP not found!",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else {
                                    mediator.getUsersDatabase().add(student);
                                }
                            }
                            usersModel.clear();
                            usersModel.addAll(mediator.getUsersDatabase().getStudentsData());
                        }
                        if (userType == UserFactory.UserType.Parent) {
                            if (mediator.getUsersDatabase().getParent(cnpField.getText()) != null) {
                                JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                                        "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                mediator.getUsersDatabase().add((Parent) user);
                            }
                            usersModel.clear();
                            usersModel.addAll(mediator.getUsersDatabase().getParentsData());
                        }
                    }
                }
            }
            if (actionEvent.getSource() == backButton) {
                mediator.showAdminMenu();
            }
            // Reset text fields
            cnpField.setText(cnpField.getName());
            cnpField.setForeground(Color.LIGHT_GRAY);
            firstNameField.setText(firstNameField.getName());
            firstNameField.setForeground(Color.LIGHT_GRAY);
            lastNameField.setText(lastNameField.getName());
            lastNameField.setForeground(Color.LIGHT_GRAY);
            fatherCnpField.setText(fatherCnpField.getName());
            fatherCnpField.setForeground(Color.LIGHT_GRAY);
            motherCnpField.setText(motherCnpField.getName());
            motherCnpField.setForeground(Color.LIGHT_GRAY);
        }
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
