package CatalogPatterns;

import CatalogAux.Grade;
import CatalogUsers.Student;

import java.util.ArrayList;

public class BestTotalScore implements ScoreStrategy{
    @Override
    public Student getBestStudent(ArrayList<Grade> grades) {
        if (grades.isEmpty())
            return null;
        Grade bestGrade = grades.get(0);
        for (Grade grade : grades)
            if (bestGrade.getTotal() < grade.getTotal())
                bestGrade = grade;

        return bestGrade.getStudent();
    }
}
