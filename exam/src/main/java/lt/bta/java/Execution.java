package lt.bta.java;

import lt.bta.java.entities.Grade;
import lt.bta.java.entities.Student;

import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

import static java.util.Locale.UK;


/**
 * Vykdymo klase
 *
 * @author Rabinas
 */
public class Execution {

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/dienynas?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private String user = "root";
    private String password = "mysql";
    private NumberFormat nf;

    {
        nf = NumberFormat.getNumberInstance(UK);
        nf.setMaximumFractionDigits(1);

    }


    public static void main(String args[]) throws IOException {

        Execution ex = new Execution();

        //studentu map is duomenu bazes
        Map<Integer, Student> students = ex.getData();

        //studentu isvedimas didejimo tvarka
        ex.studentsAscendingOrder(students);

        //kurso bendras vidurkis
        System.out.println(ex.nf.format(ex.courseAverage(2018)));
    }


    /**
     * Prisijungia prie duomenų bazes ir uzkrauna
     * duomenis i kolekcija
     *
     * @return Map tipo kolekcija, kurios key yra
     * studento id, o value objektas Student
     */
    private Map<Integer, Student> getData() {

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection conn = DriverManager.getConnection(url, user, password)
        ) {

            String select = "SELECT S.*, P.studentas_id, P.data, P.pazymys, P.id AS paz_id FROM  studentai S  JOIN pazymiai P ON S.id = P.studentas_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);

            Map<Integer, Student> students = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");

                if (!students.containsKey(id)) {
                    Student student = new Student();
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
                grade.setId(rs.getInt("paz_id"));
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

    /**
     * Isveda studentu sarasa didejimo tvarka: pagal pavarde,
     * jei pavarde vienoda, tuomet pagal vardą
     *
     * @param students Map tipo kolekcija, kurios key yra
     *                 studento id, o value objektas Student
     */
    private void studentsAscendingOrder(Map<Integer, Student> students) {
        students.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry<Integer, Student>::getValue).reversed())
                .forEach(e -> System.out.println(e.getValue()));
    }


    /**
     * Suskaiciuoja bendra visu studentu pazymiu vidurki
     *
     * @param year metai, pagal kuriuos skaiciuojamas
     *             pazymiu vidurkis
     * @return vidurki
     */
    private double courseAverage(int year) {
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


