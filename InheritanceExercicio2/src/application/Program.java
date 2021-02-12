package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Company;
import entities.Individual;
import entities.TaxPayer;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<TaxPayer> taxPayers = getTaxPayers(sc);

        System.out.println("\nTAXES PAID:");
        for (TaxPayer payer : taxPayers) {
            System.out.printf("%s: $ %.2f\n", payer.getName(), payer.tax());
        }
        System.out.printf("\nTOTAL TAXES: $ %.2f\n",taxPayers.stream().mapToDouble(t -> t.tax()).sum());
        sc.close();
    }

    public static List<TaxPayer> getTaxPayers(Scanner sc) {
        List<TaxPayer> taxPayers = new ArrayList<>();
        System.out.print("Enter the number of tax payers: ");
        int numberOfTaxPayers = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfTaxPayers; ++i) {
            System.out.print("Individual or Company (i/c)? ");
            char type = sc.nextLine().trim().charAt(0);

            System.out.print("Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Annual Income: ");
            double income = sc.nextDouble();
            sc.nextLine();

            switch (type) {
            case 'c':
                System.out.print("Number of employees: ");
                int numberOfEmployees = sc.nextInt();
                taxPayers.add(new Company(name, income, numberOfEmployees));
                break;
            default:
                System.out.print("Health expenditures: ");
                double healthExpense = sc.nextDouble();
                taxPayers.add(new Individual(name, income, healthExpense));
                break;
            }
            sc.nextLine();
        }
        return taxPayers;
    }

}
