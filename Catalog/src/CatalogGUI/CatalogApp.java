package CatalogGUI;

import javax.swing.*;
import java.awt.*;

public class CatalogApp extends JFrame {
    private Mediator mediator;

    public CatalogApp() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));
        mediator = new Mediator(this);
        mediator.setSingInMenu(new SingInMenu(mediator));
        mediator.setSingUpMenu(new SingUpMenu(mediator));
    }

    public void startGUI() {
        mediator.showSingInMenu();
        setVisible(true);
    }

    public static void main(String[] args) {
        CatalogApp app = new CatalogApp();
        app.startGUI();
    }

}
