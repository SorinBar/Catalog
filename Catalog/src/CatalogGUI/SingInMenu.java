package CatalogGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingInMenu {
    Mediator mediator;
    private JPanel panel = new JPanel();

    public SingInMenu(Mediator mediator) {
        this.mediator = mediator;
        // Left side
        JPanel left = new JPanel();

        JRadioButton adminButton = new JRadioButton("Administrator");


        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("Sing In");
        titleLabel.setFont(new Font("Open Sans", Font.BOLD, 18));
        JButton button = new JButton("click me!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mediator.showSingUpMenu();
            }
        });
        // Position
        right.add(titleLabel);
        right.add(button);
        panel.setLayout(new FlowLayout());
        panel.add(left);
        panel.add(right);
    }

    public JPanel getPanel() {
        return panel;
    }
}
