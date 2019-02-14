package lt.bta.java.entities;

import java.io.Serializable;
import java.util.List;

public class Student  {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Grade> grades;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "id " + id + " vardas " + firstName + " pavarde " + lastName + " el pastas " + email + " pazymiai " + grades;
    }
}
