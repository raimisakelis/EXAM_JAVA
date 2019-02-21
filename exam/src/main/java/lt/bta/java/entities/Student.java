package lt.bta.java.entities;

import java.util.List;
import java.util.Objects;


/**
 * Klase aprasanti studenta. Joje yra keletas metodu
 * skirtu studentu palyginimui bei studento pazymiu
 * vidurkio skaiciavimui.
 */
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


    /**
     * Metodas, skirtas nustatyti objekto unikaluma.
     * Įvertinimas atliekamas, lyginant unikalu Student
     * objekto id lauka.
     *
     * @param o Student tipo objektas
     * @return true arba false
     */
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


    /**
     * Metodas, skirtas lyginti skirtingus Student tipo objektus,
     * siekiant juos surikiuoti. Pirmiausia lyginamos pavardes,
     * jei jos lygios, lyginami vardai.
     *
     * @param o Student tipo objektas
     * @return teigiama, neigiama skaiciu
     * arba 0
     */
    @Override
    public int compareTo(Student o) {
        int cmp = this.lastName.compareToIgnoreCase(o.lastName);
        if (cmp != 0) return cmp;
        cmp = this.firstName.compareToIgnoreCase(o.firstName);
        return cmp;
    }


    /**
     * Suskaiciuoja studento pazymių vidurki
     *
     * @param year metai, pagal kuriuos skaiciuojamas
     *             pazymiu vidurkis
     * @return vidurki
     */
    public double averageStudentGrade(int year) {
        double sum = grades.stream()
                .filter(grade -> grade.getDate().getYear() == year)
                .mapToDouble(Grade::getGrade)
                .sum();

        int count = (int) grades.stream()
                .filter(grade -> grade.getDate().getYear() == year)
                .count();

        double average = 0;

        if (count != 0) {
            average = sum / count;
        }
        return average;
    }


    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + email + " " + grades;
    }
}
