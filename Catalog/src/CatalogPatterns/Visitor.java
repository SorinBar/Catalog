package CatalogPatterns;

import CatalogUsers.Assistant;
import CatalogUsers.Teacher;

public interface Visitor {
    void visit(Assistant assistant);
    void visit(Teacher teacher);
}