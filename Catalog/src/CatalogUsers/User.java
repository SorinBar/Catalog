package CatalogUsers;

public abstract class User {
    private String firstName, lastName;
    private String CNP;
    private String hashPass;
    public User(String firstName, String lastName, String CNP) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        hashPass = "null";
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
    public void setHashPass(String hashPass) {
        this.hashPass = hashPass;
    }
    public String getHashPass() {
        return hashPass;
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
