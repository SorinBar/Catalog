package CatalogGUI;

import Encryption.Digest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu{

    private Mediator mediator;
    private final JPanel panel;
    private final JButton coursesButton;
    private final JButton usersButton;
    private final JButton passButton;
    private final JButtonClick jButtonClick;

    public AdminMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        coursesButton = new JButton("Edit courses");
        usersButton = new JButton("Edit users");
        passButton = new JButton("Change pass");
        jButtonClick = new JButtonClick();

        // Center pane
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.add(coursesButton);
        center.add(usersButton);
        center.add(passButton);

        coursesButton.addActionListener(jButtonClick);
        usersButton.addActionListener(jButtonClick);
        passButton.addActionListener(jButtonClick);

        // Main panel
        panel.setLayout(new GridBagLayout());
        panel.add(center);
    }
    public JPanel getPanel() {
        return panel;
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource() == coursesButton)
                mediator.showCatalogMenu();
            if (actionEvent.getSource() == usersButton)
                mediator.showUsersMenu();
            if (actionEvent.getSource() == passButton) {
               String password = JOptionPane.showInputDialog(mediator.getCatalogApp(),
                        "", "Enter New Password", JOptionPane.PLAIN_MESSAGE);
               if (password != null) {
                   if (password.isBlank())
                       JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                               "Password should not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                   else {
                       password = Digest.SHA256(password);
                       mediator.getUsersDatabase().setAdminPassword(password);
                       JOptionPane.showMessageDialog(mediator.getCatalogApp(),
                               "Password updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                   }
               }
            }
        }
    }
}
