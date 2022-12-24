package CatalogUsers;

public abstract class User {
    private String firstName, lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!getLastName().equals(((User)obj).getLastName()))
            return false;
        if (!getFirstName().equals(((User)obj).getFirstName()))
            return false;

        return true;
    }
}
