package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Department;
import entities.HourContract;
import entities.Worker;
import entities.enums.WorkerLevel;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Worker worker = workerFromInput(sc);
        List<HourContract> contracts = contractsFromInput(sc);
        for (HourContract c : contracts) {
            worker.addContract(c);
        }
        
        System.out.print("\nEnter month and year to calculate income (MM/YYYY): ");
        String[] date = sc.nextLine().trim().split("/");
        sc.close();
        
        int month = Integer.parseInt(date[0]);
        int year = Integer.parseInt(date[1]);
        System.out.println("Name: " + worker.getName());
        System.out.println("Department: " + worker.getDepartment().getName());
        System.out.printf("Income for %02d/%04d: $ %.2f\n", month, year, worker.income(year, month));
        
    }

    public static Worker workerFromInput(Scanner sc) {
        System.out.print("Enter Department's Name: ");
        Department department = new Department(sc.nextLine());

        System.out.println("Enter worker data:");
        System.out.print("Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Level ");
        System.out.print(Arrays.toString(WorkerLevel.values()));
        System.out.print(": ");
        WorkerLevel level = WorkerLevel.valueOf(sc.nextLine().trim().toUpperCase());

        System.out.print("Base Salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        return new Worker(name, level, department, salary);
    }

    public static List<HourContract> contractsFromInput(Scanner sc) {
        System.out.print("How many contracts to this worker? ");
        int numberOfContracts = sc.nextInt();
        sc.nextLine();

        DateTimeFormatter brlDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;
        double valuePerHour;
        int hours;
        List<HourContract> contracts = new ArrayList<>();

        for (int i = 1; i <= numberOfContracts; ++i) {
            System.out.printf("Enter contract #%d data:\n", i);
            System.out.print("Date (DD/MM/YYYY): ");
            date = LocalDate.parse(sc.nextLine().trim(), brlDateFormat);

            System.out.print("Value per hour: ");
            valuePerHour = sc.nextDouble();
            sc.nextLine();

            System.out.print("Duration (hours): ");
            hours = sc.nextInt();
            sc.nextLine();

            contracts.add(new HourContract(date, valuePerHour, hours));
        }
        return contracts;
    }

}
