package CatalogPatterns;

import CatalogAux.Triple;
import CatalogDatabase.ReadFile;
import CatalogDatabase.UsersDatabase;
import CatalogMain.Catalog;
import CatalogAux.Grade;
import CatalogUsers.Assistant;
import CatalogUsers.Student;
import CatalogUsers.Teacher;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreVisitor implements Visitor{
    private final Catalog catalog;
    private final HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    private final HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores;
    public final String path = "src/CatalogDatabase/Database/unverifiedGrades.txt";
    public ScoreVisitor(Catalog catalog) {
        this.catalog = catalog;
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }
    @Override
    public void visit(Teacher teacher) {
        ArrayList<Tuple<Student, String, Double>> dataList = examScores.get(teacher);
        if (dataList == null)
            return;
        Grade grade;
        for(Tuple<Student, String, Double> data : dataList) {
            catalog.getCourse(data.getY()).getGrade(data.getX()).setExamScore(data.getZ());
            // Grade for notification
            grade = new Grade();
            grade.setStudent(data.getX());
            grade.setCourse(data.getY());
            grade.setExamScore(data.getZ());
            catalog.notifyObservers(grade);
        }
    }
    @Override
    public void visit(Assistant assistant) {
        ArrayList<Tuple<Student, String, Double>> dataList = partialScores.get(assistant);
        if (dataList == null)
            return;
        Grade grade;
        for(Tuple<Student, String, Double> data : dataList) {
            catalog.getCourse(data.getY()).getGrade(data.getX()).setPartialScore(data.getZ());
            // Grade for notification
            grade = new Grade();
            grade.setStudent(data.getX());
            grade.setCourse(data.getY());
            grade.setExamScore(data.getZ());
            catalog.notifyObservers(grade);
        }
    }

    public void addExamScore(Teacher teacher, Student student, String course, Double score) {
        Tuple<Student, String, Double> data = new Tuple<>(student, course, score);
        ArrayList<Tuple<Student, String, Double>> dataList = examScores.get(teacher);
        if (dataList == null) {
            dataList = new ArrayList<Tuple<Student, String, Double>>();
            dataList.add(data);
            examScores.put(teacher, dataList);
        }
        else
            dataList.add(data);
    }

    public void addPartialScore(Assistant assistant, Student student, String course, Double score) {
        Tuple<Student, String, Double> data = new Tuple<>(student, course, score);
        ArrayList<Tuple<Student, String, Double>> dataList = partialScores.get(assistant);
        if (dataList == null) {
            dataList = new ArrayList<Tuple<Student, String, Double>>();
            dataList.add(data);
            partialScores.put(assistant, dataList);
        }
        else
            dataList.add(data);
    }
    public ArrayList<Triple<Student, String, Double>> getScores(Assistant assistant) {
        ArrayList<Tuple<Student, String, Double>> scores = partialScores.get(assistant);
        if (scores == null)
            return null;
        ArrayList<Triple<Student, String, Double>> scoresAux = new ArrayList<>();
        for (Tuple<Student, String, Double> data : scores) {
            scoresAux.add(new Triple<>(data.getX(), data.getY(), data.getZ()));
        }
        return scoresAux;
    }
    public ArrayList<Triple<Student, String, Double>> getScores(Teacher teacher) {
        ArrayList<Tuple<Student, String, Double>> scores = examScores.get(teacher);
        if (scores == null)
            return null;
        ArrayList<Triple<Student, String, Double>> scoresAux = new ArrayList<>();
        for (Tuple<Student, String, Double> data : scores) {
            scoresAux.add(new Triple<>(data.getX(), data.getY(), data.getZ()));
        }
        return scoresAux;
    }

    public void load(UsersDatabase usersDatabase){
        ArrayList<String> lines = ReadFile.fromPath(path);
        if (lines.size() == 0) {
            System.out.println("Error loading scores!");
            return;
        }
        int index = 0;
        int teachersNumber;
        int assistantsNumber;
        int studentsNumber;
        Teacher teacher;
        Assistant assistant;
        Student student;
        String cnp;
        String courseName;
        double score;
        // Teachers
        teachersNumber = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < teachersNumber; i++) {
            cnp = lines.get(index++);
            teacher = usersDatabase.getTeacher(cnp);
            studentsNumber = Integer.parseInt(lines.get(index++));
            for (int j = 0; j < studentsNumber; j++) {
                cnp = lines.get(index++);
                courseName = lines.get(index++);
                score = Double.parseDouble(lines.get(index++));
                student = usersDatabase.getStudent(cnp);
                addExamScore(teacher, student, courseName, score);
            }
        }
        index++;
        // Assistants
        assistantsNumber = Integer.parseInt(lines.get(index++));
        for (int i = 0; i < assistantsNumber; i++) {
            cnp = lines.get(index++);
            assistant = usersDatabase.getAssistant(cnp);
            studentsNumber = Integer.parseInt(lines.get(index++));
            for (int j = 0; j < studentsNumber; j++) {
                cnp = lines.get(index++);
                courseName = lines.get(index++);
                score = Double.parseDouble(lines.get(index++));
                student = usersDatabase.getStudent(cnp);
                addPartialScore(assistant, student, courseName, score);
            }
        }
    }
    public void update() {
        try {
            FileWriter fileWriter = new FileWriter(path);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            // Teachers
            printWriter.println(examScores.size());
            for (Map.Entry<Teacher, ArrayList<Tuple<Student, String, Double>>> entry : examScores.entrySet()) {
                printWriter.println(entry.getKey().getCNP());
                printWriter.println(entry.getValue().size());
                for (Tuple<Student, String, Double> tuple : entry.getValue()) {
                    printWriter.println(tuple.getX().getCNP());
                    printWriter.println(tuple.getY());
                    printWriter.println(tuple.getZ());
                }
            }
            printWriter.println();
            // Assistants
            printWriter.println(partialScores.size());
            for (Map.Entry<Assistant, ArrayList<Tuple<Student, String, Double>>> entry : partialScores.entrySet()) {
                printWriter.println(entry.getKey().getCNP());
                printWriter.println(entry.getValue().size());
                for (Tuple<Student, String, Double> tuple : entry.getValue()) {
                    printWriter.println(tuple.getX().getCNP());
                    printWriter.println(tuple.getY());
                    printWriter.println(tuple.getZ());
                }
            }

            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error updating scores!");
        }
    }
    // Testing
    public void print() {
        System.out.println("Exam: " + examScores);
        System.out.println("Partial: " + partialScores);
    }

    private class Tuple<X, Y, Z> {
        private X x;
        private Y y;
        private Z z;

        public Tuple(){}
        public Tuple(X x, Y y, Z z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public void setX(X x) {
            this.x = x;
        }
        public void setY(Y y) {
            this.y = y;
        }
        public void setZ(Z z) {
            this.z = z;
        }
        public X getX() {
            return x;
        }
        public Y getY() {
            return y;
        }
        public Z getZ() {
            return z;
        }

        @Override
        public String toString() {
            return x + " " + y + " " + z;
        }
    }
}
