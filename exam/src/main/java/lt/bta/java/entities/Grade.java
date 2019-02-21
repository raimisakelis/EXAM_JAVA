package lt.bta.java.entities;

import java.time.LocalDate;

/**
 * Klase skirta dirbti su studento pazymiais.
 */
public class Grade {

    private int id;
    private int studentId;
    private LocalDate date;
    private int grade;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }


    @Override
    public String toString() {
        return " " + grade + " id= " + id;
    }
}
