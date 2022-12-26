import CatalogDatabase.UsersDatabase;
import CatalogGUI.CatalogApp;
import CatalogGUI.Mediator;
import CatalogMain.Catalog;


public class SetUp {
    private Mediator mediator;
    private UsersDatabase usersDatabase;
    private Catalog catalog;
    private CatalogApp catalogApp;
    private boolean loadedUsersDatabase;
    private boolean loadedCatalog;
    public SetUp() {
        catalogApp = new CatalogApp();
        mediator  = catalogApp.getMediator();
        catalog = Catalog.getInstance();
        usersDatabase = UsersDatabase.getInstance();
        mediator.setCatalog(catalog);
        mediator.setUsersDatabase(usersDatabase);
        loadedUsersDatabase = false;
        loadedCatalog = false;
    }

    public void loadUsersDatabase() {
        // Load
        loadedUsersDatabase = true;
    }

    public void loadCatalog() {
        if (!loadedUsersDatabase) {
            System.out.println("Users database is not loaded!");
            return;
        }
        // load
        loadedCatalog = true;
    }
    public void startApp() {
        if (!loadedCatalog) {
            System.out.println("Catalog is not loaded!");
            return;
        }
        catalogApp.startGUI();
    }
    public static void main(String[] args) {
        SetUp setUp = new SetUp();
        setUp.loadUsersDatabase();
        setUp.loadCatalog();
        setUp.startApp();
    }
}
