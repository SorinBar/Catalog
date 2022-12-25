package CatalogUsers;

public class Student extends User {
    private Parent mother, father;

    public Student(String firstName, String lastName, String CNP) {
        super(firstName, lastName, CNP);
        mother = null;
        father = null;
    }
    public void setMother(Parent mother) {
        this.mother = mother;
    }
    public void setFather(Parent father) {
        this.father = father;
    }
    public Parent getMother() {
        return mother;
    }
    public Parent getFather() {
        return father;
    }
}
