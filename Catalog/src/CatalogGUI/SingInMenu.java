package CatalogGUI;

import Encryption.Digest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SingInMenu{
    Mediator mediator;
    private final JPanel panel;
    private final JRadioButton adminButton;
    private final JRadioButton teacherButton;
    private final JRadioButton assistantButton;
    private final JRadioButton studentButton;
    private final JRadioButton parentButton;
    private final JRadioSelect radioSelect;
    private final JTextField cnpField;
    private final JPasswordField passField;
    private final JButton singInButton;
    private final JTextFieldSelect textFieldSelect;
    private final JButtonClick buttonClick;
    private String userCNP;
    private String userPassHash;

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
        passField = new JPasswordField();
        singInButton = new JButton("Sing In");
        textFieldSelect = new JTextFieldSelect();
        buttonClick = new JButtonClick();

        userCNP = "";
        userPassHash = "";

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
        passField.setEchoChar((char)0);

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
                cnpField.setText(cnpField.getName());
                cnpField.setForeground(Color.LIGHT_GRAY);
                cnpField.setEditable(false);

                passField.setText(passField.getName());
                passField.setForeground(Color.LIGHT_GRAY);
                passField.setEchoChar((char)0);
            }
            else {
                cnpField.setText(cnpField.getName());
                cnpField.setForeground(Color.LIGHT_GRAY);
                cnpField.setEditable(true);

                passField.setText(passField.getName());
                passField.setForeground(Color.LIGHT_GRAY);
                passField.setEchoChar((char)0);
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
                if (field == passField)
                    passField.setEchoChar('*');
            }
        }
        @Override
        public void focusLost(FocusEvent focusEvent) {
            JTextField field = (JTextField) (focusEvent.getSource());
            if (field.getText().equals("")){
                field.setForeground(Color.LIGHT_GRAY);
                field.setText(field.getName());
                if (field == passField)
                    passField.setEchoChar((char)0);
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
            passField.setEchoChar((char)0);

            if (adminButton.isSelected()) {
                if (!mediator.getUsersDatabase().getAdminPassword().equals(userPassHash))
                    JOptionPane.showMessageDialog(mediator.getCatalogApp(), "Invalid Password");
                else
                    mediator.showAdminMenu();
            }
        }
    }
}
