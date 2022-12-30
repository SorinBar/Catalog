package CatalogGUI;

import CatalogDatabase.CatalogData;
import CatalogMain.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CatalogApp extends JFrame {
    private final Mediator mediator;

    public CatalogApp() {
        mediator = new Mediator(this);
        setMinimumSize(new Dimension(600, 300));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Update Database
                mediator.getUsersDatabase().update();
                CatalogData.update(mediator.getCatalog(), Catalog.catalogPath);
                mediator.getScoreVisitor().update();
                mediator.getNotificationsDatabase().update();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
    public void startGUI() {
        mediator.showSingInMenu();
        setVisible(true);
    }
    public Mediator getMediator() {
        return mediator;
    }


}
