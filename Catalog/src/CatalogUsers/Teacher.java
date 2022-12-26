package CatalogUsers;

import CatalogPatterns.Element;
import CatalogPatterns.Visitor;

public class Teacher extends User implements Element {
    public Teacher(String firstName, String lastName, String CNP) {
        super(firstName, lastName, CNP);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
