package CatalogGUI;

import CatalogDatabase.UsersDatabase;
import CatalogMain.Catalog;

import java.awt.*;

public class Mediator {
    private Catalog catalog;
    private UsersDatabase usersDatabase;
    private CatalogApp catalogApp;
    private SingInMenu singInMenu;
    private SingUpMenu singUpMenu;
    private AdminMenu adminMenu;

    public Mediator(CatalogApp catalogApp) {
        this.catalogApp = catalogApp;
        catalog = Catalog.getInstance();
        usersDatabase = UsersDatabase.getInstance();
    }
    public void create() {
        singInMenu = new SingInMenu(this);
        singUpMenu = new SingUpMenu(this);
        adminMenu = new AdminMenu(this);
    }
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
    public void setUsersDatabase(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }
    public Catalog getCatalog() {
        return catalog;
    }
    public UsersDatabase getUsersDatabase() {
        return usersDatabase;
    }

    public CatalogApp getCatalogApp() {
        return catalogApp;
    }
    public void showSingInMenu() {
        catalogApp.setTitle("Catalog - Sing In");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(singInMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showSingUpMenu() {
        catalogApp.setTitle("Catalog - Sing Up");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(singUpMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showAdminMenu() {
        catalogApp.setTitle("Catalog - Admin");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(adminMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
}
