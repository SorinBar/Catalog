package CatalogUsers;

import CatalogPatterns.Element;
import CatalogPatterns.Visitor;

public class Teacher extends User implements Element {
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
