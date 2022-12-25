package CatalogGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingUpMenu {

    Mediator mediator;
    private JPanel panel = new JPanel();

    public SingUpMenu(Mediator mediator) {
        this.mediator = mediator;

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        JButton button = new JButton("click me!!!!");
        JPanel panel = new JPanel();
        panel.add(button);
        this.panel.add(panel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mediator.showSingInMenu();
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
