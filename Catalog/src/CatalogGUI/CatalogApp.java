package CatalogGUI;

import CatalogDatabase.CatalogData;
import CatalogMain.Catalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CatalogApp extends JFrame {
    private Mediator mediator;

    public CatalogApp() {
        mediator = new Mediator(this);
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Update Database
                mediator.getUsersDatabase().update();
                CatalogData.update(mediator.getCatalog(), Catalog.catalogPath);
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
