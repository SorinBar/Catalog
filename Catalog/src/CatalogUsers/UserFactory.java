package CatalogUsers;

public class UserFactory {
    public enum UserType{
        Student,
        Parent,
        Assistant,
        Teacher
    }
    private UserFactory() {}
    public static User getUser(UserType type, String firstName, String lastName) {
        switch (type) {
            case Student:
                return new Student(firstName, lastName);
            case Parent:
                return new Parent(firstName, lastName);
            case Assistant:
                return new Assistant(firstName, lastName);
            case Teacher:
                return new Teacher(firstName, lastName);
        }
        return null;
    }
}
