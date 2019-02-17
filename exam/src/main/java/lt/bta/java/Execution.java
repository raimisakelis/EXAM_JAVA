package lt.bta.java;

import lt.bta.java.entities.Grade;
import lt.bta.java.entities.Student;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Execution {

    public static void main(String args[]) throws IOException {

        //studentu map is duomenu bazes
        Map<Integer, Student> students = getData();

        //studentu isvedimas didejimo tvarka
        studentsAscendingOrder(students);

        //kurso bendras vidurkis
        System.out.println(courseAverage(2018));


    }


    protected static Map<Integer, Student> getData() {

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


            Map<Integer, Student> students = new HashMap<>();
            Student student = null;
            while (rs.next()) {
                int id = rs.getInt("id");

                if (students == null || !students.containsKey(id)) {
                    student = new Student();
                    student.setId(id);
                    student.setFirstName(rs.getString("vardas"));
                    student.setLastName(rs.getString("pavarde"));
                    student.setEmail(rs.getString("el_pastas"));
                    List<Grade> grades = new ArrayList<>();
                    student.setGrades(grades);
                    students.put(id, student);
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

    private static void studentsAscendingOrder(Map<Integer, Student> students) {
        students.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry<Integer, Student>::getValue).reversed())
                .forEach(e -> System.out.println(e.getValue()));

    }


    private static double courseAverage(int year) {
        double averageCourse = 0;
        double sumAllStudentAverage = 0;
        Map<Integer, Student> students = getData();

        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            sumAllStudentAverage += entry.getValue().averageStudentGrade(year);
        }

        if (!students.isEmpty()) {
            averageCourse = sumAllStudentAverage / students.size();
        }
        return averageCourse;
    }

}


