package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Employee;

public class Program {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String pathString = sc.nextLine().trim();

        if (pathString.isEmpty()) {
            pathString = "input.txt";
        }

        Path path = Paths.get(pathString);

        List<Employee> employees = Files.lines(path)
                .map(line -> {
                    String[] parts = line.split(",");
                    String name = parts[0].trim();
                    String email = parts[1].trim();
                    double salary = Double.parseDouble(parts[2].trim());
                    return new Employee(name, email, salary);
                })
                .sorted((e1, e2) -> e1.getName().toUpperCase().compareTo(e2.getName().toUpperCase()))
                .collect(Collectors.toList());

        System.out.print("Enter salary: $");
        double salary = sc.nextDouble();
        sc.nextLine();

        System.out.printf("Email of people whose salary is more than $%.2f\n", salary);

        employees.stream().filter(e -> e.getSalary() > salary).forEach(e -> System.out.println(e.getEmail()));

        double sumOfSalary = employees.stream()
                .filter(e -> e.getName().toUpperCase().startsWith("M"))
                .mapToDouble(e -> e.getSalary()).sum();

        System.out.printf("Sum of salary of people whose name starts with 'M': $%.2f\n", sumOfSalary);

        sc.close();
    }

}
