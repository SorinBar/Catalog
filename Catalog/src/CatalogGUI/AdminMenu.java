package CatalogGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminMenu {

    Mediator mediator;
    private JPanel panel;
    private JScrollPane coursesScrollPane;
    private JList<String> coursesPanel;
    private DefaultListModel<String> coursesModel;

    public AdminMenu(Mediator mediator) {
        // Set UP
        this.mediator = mediator;
        panel = new JPanel();
        // Left Panel
        JPanel left = new JPanel();
        System.out.println(mediator.getCatalog().getCoursesNames());
        //ArrayList<String> coursesNames = mediator.getCatalog().getCoursesNames();
        //System.out.println(coursesNames);






        JButton button = new JButton("click me!!!!");
        panel.add(button);
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
