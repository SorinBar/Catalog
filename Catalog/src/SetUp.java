import CatalogDatabase.*;
import CatalogGUI.CatalogApp;
import CatalogGUI.Mediator;
import CatalogMain.Catalog;
import CatalogUsers.Student;

import javax.sql.rowset.RowSetMetaDataImpl;

public class SetUp {
    private Mediator mediator;
    private CatalogApp catalogApp;
    private boolean loadedUsersDatabase;
    private boolean loadedCatalog;
    private boolean loadedScoreDatabase;
    private boolean loadedNotificationsDatabase;
    private boolean createdAppFrames;
    public SetUp() {
        catalogApp = new CatalogApp();
        mediator  = catalogApp.getMediator();
        loadedUsersDatabase = false;
        loadedCatalog = false;
        loadedScoreDatabase = false;
        createdAppFrames = false;
        loadedNotificationsDatabase = false;
    }

    public void loadUsersDatabase() {
        mediator.getUsersDatabase().load();

        loadedUsersDatabase = true;
    }

    public void loadCatalog() {
        if (!loadedUsersDatabase) {
            System.out.println("Users database is not loaded!");
            return;
        }
        CatalogData.load(mediator.getCatalog(), mediator.getUsersDatabase(), Catalog.catalogPath);
        CatalogData.addParents(mediator.getCatalog(), mediator.getUsersDatabase());

        loadedCatalog = true;
    }
    public void loadScoreDatabase() {
        if (!loadedCatalog) {
            System.out.println("Catalog is not loaded!");
            return;
        }
        mediator.getScoreVisitor().load(mediator.getUsersDatabase());

        loadedScoreDatabase = true;
    }
    public void loadNotificationsDatabase() {
        if (!loadedScoreDatabase) {
            System.out.println("Scores database is not loaded!");
            return;
        }
        mediator.getNotificationsDatabase().load();
        mediator.getNotificationsDatabase().setParents();

        loadedNotificationsDatabase = true;
    }
    public void createAppFrames() {
        if (!loadedNotificationsDatabase) {
            System.out.println("Notifications database is not loaded!");
            return;
        }
        mediator.create();

        createdAppFrames = true;
    }
    public void startGUI() {
        if (!createdAppFrames) {
            System.out.println("App frames are not created!");
            return;
        }
        catalogApp.startGUI();
    }
    public void startApp() {
        loadUsersDatabase();
        loadCatalog();
        loadScoreDatabase();
        loadNotificationsDatabase();
        createAppFrames();
        startGUI();
    }
    public static void main(String[] args) {
        SetUp setUp = new SetUp();
        setUp.startApp();
    }
}
