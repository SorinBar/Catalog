package CatalogGUI;

import CatalogCourses.Course;
import CatalogDatabase.NotificationsDatabase;
import CatalogDatabase.UsersDatabase;
import CatalogMain.Catalog;
import CatalogPatterns.ScoreVisitor;
import CatalogUsers.Teacher;

import java.awt.*;

public class Mediator {
    private final Catalog catalog;
    private final UsersDatabase usersDatabase;
    private final CatalogApp catalogApp;
    private final ScoreVisitor scoreVisitor;
    private final NotificationsDatabase notificationsDatabase;
    private SingInMenu singInMenu;
    private AdminMenu adminMenu;
    private UsersMenu usersMenu;
    private CatalogMenu catalogMenu;
    private CourseMenu courseMenu;
    private TeacherMenu teacherMenu;

    public Mediator(CatalogApp catalogApp) {
        this.catalogApp = catalogApp;
        catalog = Catalog.getInstance();
        usersDatabase = UsersDatabase.getInstance();
        scoreVisitor = new ScoreVisitor(catalog);
        notificationsDatabase = new NotificationsDatabase(usersDatabase);
    }
    public void create() {
        singInMenu = new SingInMenu(this);
        adminMenu = new AdminMenu(this);
        usersMenu = new UsersMenu(this);
        catalogMenu = new CatalogMenu(this);
        courseMenu = new CourseMenu(this);
        teacherMenu = new TeacherMenu(this);
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
    public ScoreVisitor getScoreVisitor() {
        return scoreVisitor;
    }
    public NotificationsDatabase getNotificationsDatabase() {
        return notificationsDatabase;
    }
    public void showSingInMenu() {
        catalogApp.setTitle("Catalog - Sing In");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(singInMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showAdminMenu() {
        catalogApp.setTitle("Catalog - Admin Menu");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(adminMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showUsersMenu() {
        catalogApp.setTitle("Catalog - Users");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(usersMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showCatalogMenu() {
        catalogApp.setTitle("Catalog");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(catalogMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showCourseMenu(Course course) {
        courseMenu.setSelectedCourse(course);
        catalogApp.setTitle("Catalog");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(courseMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }
    public void showTeacherMenu(Teacher teacher) {
        teacherMenu.setTeacher(teacher);
        catalogApp.setTitle("Catalog - Grades");
        catalogApp.getContentPane().removeAll();
        catalogApp.getContentPane().add(teacherMenu.getPanel(), BorderLayout.CENTER);
        catalogApp.getContentPane().doLayout();
        catalogApp.update(catalogApp.getGraphics());
        catalogApp.pack();
    }

}
