package CatalogGUI;

import java.awt.*;

public class Mediator {
    private CatalogApp catalogApp;
    private SingInMenu singInMenu;
    private SingUpMenu singUpMenu;

    public Mediator(CatalogApp catalogApp) {
        this.catalogApp = catalogApp;
    }
    public void setSingInMenu(SingInMenu singInMenu) {
        this.singInMenu = singInMenu;
    }
    public void setSingUpMenu(SingUpMenu singUpMenu) {
        this.singUpMenu = singUpMenu;
    }
    public SingInMenu getSingInMenu() {
        return singInMenu;
    }
    public SingUpMenu getSingUpMenu() {
        return singUpMenu;
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
}
