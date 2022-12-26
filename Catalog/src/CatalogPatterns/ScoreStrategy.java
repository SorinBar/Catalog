package CatalogPatterns;

import CatalogAux.Grade;
import CatalogUsers.Student;

import java.util.ArrayList;

public interface ScoreStrategy {
    public Student getBestStudent(ArrayList<Grade> grades);
}
