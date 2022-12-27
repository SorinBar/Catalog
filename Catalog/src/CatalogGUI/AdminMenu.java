package CatalogGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu{

    Mediator mediator;
    private final JPanel panel;
    private final JButton coursesButton;
    private final JButton usersButton;
    private final JButtonClick jButtonClick;

    public AdminMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        coursesButton = new JButton("Edit courses");
        usersButton = new JButton("Edit users");
        jButtonClick = new JButtonClick();

        // Main Panel
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.add(coursesButton);
        center.add(usersButton);

        coursesButton.addActionListener(jButtonClick);
        usersButton.addActionListener(jButtonClick);

        panel.setLayout(new GridBagLayout());
        panel.add(center);
    }

    private class JButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JButton button = (JButton) (actionEvent.getSource());
            if (button == coursesButton)
                mediator.showCatalogMenu();
            else
                mediator.showUsersMenu();
        }
    }
    public JPanel getPanel() {
        return panel;
    }
}
