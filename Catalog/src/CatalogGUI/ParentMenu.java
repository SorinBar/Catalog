package CatalogGUI;

import CatalogUsers.Parent;
import CatalogUsers.Student;

import javax.swing.*;

public class ParentMenu {
    private Mediator mediator;
    private final JPanel panel;
    private Parent parent;
    private final JList<String> notList;
    private final DefaultListModel<String> notModel;
    private final JScrollPane notPane;

    public ParentMenu(Mediator mediator) {
        this.mediator = mediator;
        panel = new JPanel();
        notModel = new DefaultListModel<String>();
        notList = new JList<>(notModel);
        notPane = new JScrollPane(notList);

        // Main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(notPane);
    }
    public JPanel getPanel() {
        return panel;
    }
    public void setParent(Parent parent) {
        this.parent = parent;
        notModel.clear();
        notModel.addAll(mediator.getNotificationsDatabase().getNotificationsData(parent.getCNP()));
    }

}
