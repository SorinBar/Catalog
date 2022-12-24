package CatalogUsers;

public class Student extends User {
    private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
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
    @Override
    public boolean equals(Object obj) {
        boolean result = super.equals(obj);
        if (!result)
            return false;
        if (father != null && ((Student)obj).getFather() != null)
            if (!getFather().equals(((Student)obj).getFather()))
                return false;
        if (mother != null && ((Student)obj).getMother() != null)
            if (!getMother().equals(((Student)obj).getMother()))
                return false;

        return true;
    }
}
