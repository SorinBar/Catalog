package CatalogGUI;

import Encryption.Digest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SingInMenu{
    Mediator mediator;
    private JPanel panel;
    private JRadioButton adminButton;
    private JRadioButton teacherButton;
    private JRadioButton assistantButton;
    private JRadioButton studentButton;
    private JRadioButton parentButton;
    private JRadioSelect radioSelect;
    private JTextField cnpField;
    private JTextField passField;
    private JButton singInButton;
    private JTextFieldSelect textFieldSelect;
    private JButtonClick buttonClick;
    private String userCNP;
    private String userPassHash;
    private String test;

    public SingInMenu(Mediator mediator) {
        // Set up
        this.mediator = mediator;
        panel = new JPanel();
        adminButton = new JRadioButton("Admin");
        teacherButton = new JRadioButton("Teacher");
        assistantButton = new JRadioButton("Assistant");
        studentButton = new JRadioButton("Student");
        parentButton = new JRadioButton("Parent");
        radioSelect = new JRadioSelect();
        cnpField = new JTextField();
        passField = new JTextField();
        singInButton = new JButton("Sing In");
        textFieldSelect = new JTextFieldSelect();
        buttonClick = new JButtonClick();


        userCNP = "";
        userPassHash = "";
        test = Digest.SHA256("1234");

        // Font
        Font listFont = new Font("Open Sans", Font.PLAIN, 14);

        // Left panel
        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        adminButton.setSelected(true);

        adminButton.setFont(listFont);
        teacherButton.setFont(listFont);
        assistantButton.setFont(listFont);
        studentButton.setFont(listFont);
        parentButton.setFont(listFont);

        adminButton.addActionListener(radioSelect);
        teacherButton.addActionListener(radioSelect);
        assistantButton.addActionListener(radioSelect);
        studentButton.addActionListener(radioSelect);
        parentButton.addActionListener(radioSelect);

        ButtonGroup group = new ButtonGroup();
        group.add(adminButton);
        group.add(teacherButton);
        group.add(assistantButton);
        group.add(studentButton);
        group.add(parentButton);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        left.add(adminButton, constraints);
        left.add(teacherButton, constraints);
        left.add(assistantButton, constraints);
        left.add(studentButton, constraints);
        left.add(parentButton, constraints);

        // Right panel
        JPanel right = new JPanel(new GridBagLayout());

        Dimension customDim = new Dimension(200, 30);

        cnpField.setPreferredSize(customDim);
        passField.setPreferredSize(customDim);
        singInButton.setPreferredSize(customDim);


        cnpField.setName(" CNP");
        passField.setName(" Password");
        cnpField.setText(cnpField.getName());
        passField.setText(passField.getName());
        cnpField.setForeground(Color.LIGHT_GRAY);
        passField.setForeground(Color.LIGHT_GRAY);

        cnpField.setEditable(false);
        cnpField.addFocusListener(textFieldSelect);
        passField.addFocusListener(textFieldSelect);
        singInButton.addActionListener(buttonClick);

        right.add(cnpField, constraints);
        right.add(passField, constraints);
        right.add(singInButton, constraints);

        // Main panel
        panel.setLayout(new GridLayout(1, 2));
        panel.add(left);
        panel.add(right);
    }
    public JPanel getPanel() {
        return panel;
    }
    private class JRadioSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JRadioButton button = (JRadioButton) (actionEvent.getSource());
            if (button == adminButton) {
                cnpField.setName(cnpField.getName());
                cnpField.setForeground(Color.LIGHT_GRAY);
                cnpField.setEditable(false);

                passField.setName(passField.getName());
                passField.setForeground(Color.LIGHT_GRAY);
            }
            else {
                cnpField.setName(cnpField.getName());
                cnpField.setForeground(Color.LIGHT_GRAY);
                cnpField.setEditable(true);

                passField.setName(passField.getName());
                passField.setForeground(Color.LIGHT_GRAY);
            }

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
    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            userCNP = cnpField.getText();
            userPassHash = Digest.SHA256(passField.getText());

            cnpField.setText(cnpField.getName());
            cnpField.setForeground(Color.LIGHT_GRAY);
            passField.setText(passField.getName());
            passField.setForeground(Color.LIGHT_GRAY);

            if (adminButton.isSelected()) {
                if (!test.equals(userPassHash))
                    JOptionPane.showMessageDialog(mediator.getCatalogApp(), "Invalid Password");
                else
                    mediator.showSingUpMenu();
            }
        }
    }
}
