package lt.bta.java;

import lt.bta.java.entities.Grade;
import lt.bta.java.entities.Student;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Execution {

    public static void main(String args[]) throws IOException {

        List<Student> students = getData();

        System.out.println(students);


    }

    protected static List<Student> getData() throws IOException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dienynas?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String user = "root";
        String password = "mysql";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(url, user, password)

        ) {


            String select = "SELECT S.*, P.* FROM  studentai S  JOIN pazymiai P ON S.id = P.studentas_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);


            List<Student> students = new ArrayList<>();
            Student student = null;

            while (rs.next()) {
                int id = rs.getInt("id");

                if (student == null) {
                    student = new Student();
                    List<Grade> grades = new ArrayList<>();
                    student.setId(id);
                    student.setFirstName(rs.getString("vardas"));
                    student.setLastName(rs.getString("pavarde"));
                    student.setEmail(rs.getString("el_pastas"));
                    student.setGrades(grades);
                    students.add(student);
                }

                int studId = rs.getInt("studentas_id");


                    Grade grade = new Grade();
                    //grade.setId(rs.getInt("id"));
                    grade.setStudentId(studId);
                    grade.setDate(LocalDate.parse(rs.getString("data")));
                    grade.setGrade(rs.getInt("pazymys"));
                    students.get(studId).getGrades().add(grade);

            }
            return students;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
