import CatalogUsers.*;
public class Main {
    public static void main(String[] args) {
        User u = UserFactory.getUser(UserFactory.UserType.Parent, "a", "b");
        System.out.println(u.getClass());
    }
}
