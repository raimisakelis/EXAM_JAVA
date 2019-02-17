package lt.bta.java.entities;

import java.util.List;
import java.util.Objects;

public class Student implements Comparable<Student> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student stud = (Student) o;

        return stud.id == id;
    }

    @Override

    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Student o) {
        int cmp = this.lastName.compareToIgnoreCase(o.lastName);

        if (cmp != 0)
            return cmp;//nes pagal zodyna trumpesnis zodis eina pirmiau nei ilgesnis turintis tokia pacia sakni

        cmp = this.firstName.compareToIgnoreCase(o.firstName);
        return cmp;
    }


    //studento pazymiu vidurkis
    public double averageStudentGrade(int year) {
        double sum = grades.stream()
                .filter(grade -> grade.getDate().getYear() == year)
                .mapToDouble(Grade::getGrade)
                .sum();

        int count = (int) grades.stream()
                .filter(grade -> grade.getDate().getYear() == year)
                .count();

        double average = 0;

        if (count != 0){
            average = sum / count;
        }
        return average;
    }



        @Override
        public String toString () {
            return id + " " + firstName + " " + lastName + " " + email + " " + grades;
        }
    }
