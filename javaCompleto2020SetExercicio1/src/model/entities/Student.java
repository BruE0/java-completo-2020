package model.entities;

import java.util.Objects;

public class Student implements Comparable<Student> {

    private final int id;

    public Student(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Student other) {
        return id == other.id ? 0 : id > other.id ? +1 : -1;
    }

    @Override
    public String toString() {
        return "Student<id:" + id + ">";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        return id == other.id;
    }

}
