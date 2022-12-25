package CatalogUsers;

import CatalogPatterns.Element;
import CatalogPatterns.Visitor;

public class Assistant extends User implements Element {
    public Assistant(String firstName, String lastName, String CNP) {
        super(firstName, lastName, CNP);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
