package CatalogUsers;

public class UserFactory {
    public enum UserType{
        Student,
        Parent,
        Assistant,
        Teacher
    }
    private UserFactory() {}
    public static User getUser(UserType type, String firstName, String lastName, String CNP) {
        switch (type) {
            case Student:
                return new Student(firstName, lastName, CNP);
            case Parent:
                return new Parent(firstName, lastName, CNP);
            case Assistant:
                return new Assistant(firstName, lastName, CNP);
            case Teacher:
                return new Teacher(firstName, lastName, CNP);
        }
        return null;
    }
}
