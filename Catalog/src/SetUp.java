import CatalogDatabase.*;
import CatalogGUI.CatalogApp;
import CatalogGUI.Mediator;
import CatalogMain.Catalog;
import Encryption.Digest;


public class SetUp {
    private Mediator mediator;
    private CatalogApp catalogApp;
    private boolean loadedUsersDatabase;
    private boolean loadedCatalog;
    private boolean createdAppFrames;
    public SetUp() {
        catalogApp = new CatalogApp();
        mediator  = catalogApp.getMediator();
        loadedUsersDatabase = false;
        loadedCatalog = false;
        createdAppFrames = false;
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
        loadedCatalog = true;
    }
    public void createAppFrames() {
        if (!loadedCatalog) {
            System.out.println("Catalog is not loaded!");
            return;
        }
        mediator.create();
        createdAppFrames = true;
    }
    public void startApp() {
        if (!createdAppFrames) {
            System.out.println("App frames are not created!");
            return;
        }
        catalogApp.startGUI();
    }
    public static void main(String[] args) {
        SetUp setUp = new SetUp();
        setUp.loadUsersDatabase();
        setUp.loadCatalog();
        setUp.createAppFrames();
        setUp.startApp();
    }
}
