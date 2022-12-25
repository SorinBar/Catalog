package CatalogUsers;

public abstract class User {
    private String firstName, lastName;
    private String CNP;
    public User(String firstName, String lastName, String CNP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getCNP() {
        return CNP;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
    @Override
    public boolean equals(Object obj) {
        return getCNP().equals(((User)obj).getCNP());
    }
}
