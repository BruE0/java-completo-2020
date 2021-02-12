package application;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.entities.Student;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        HashSet<Student> courseA = new HashSet<Student>();
        HashSet<Student> courseB = new HashSet<Student>();
        HashSet<Student> courseC = new HashSet<Student>();

        List<HashSet<Student>> courses = List.of(courseA, courseB, courseC);

        for (int i = 0; i < courses.size(); ++i) {
            System.out.print("How many students for course " + (char) ('A' + i) + "? ");
            int numOfStudents = sc.nextInt();
            sc.nextLine();
            for (int j = 1; j <= numOfStudents; ++j) {
                System.out.print("Student #" + j + ": ");
                courses.get(i).add(new Student(sc.nextInt()));
                sc.nextLine();
            }
        }

        int totalStudents = Stream.of(courseA.stream(), courseB.stream(), courseC.stream())
                .flatMap(s -> s)
                .collect(Collectors.toSet()).size();

        System.out.println("Total students: " + totalStudents);

        sc.close();
    }
}
