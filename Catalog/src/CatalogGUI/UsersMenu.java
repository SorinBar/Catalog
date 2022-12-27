package CatalogGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersMenu{
    Mediator mediator;
    private final JPanel panel;

    public UsersMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();

        // Main Panel
        panel.add(new JButton("test"));
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

        }
    }
    public JPanel getPanel() {
        return panel;
    }
}
