package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Contract;
import model.entities.Installment;
import model.services.PaypalPaymentService;
import model.services.ProcessingService;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter contract data\nNumber: ");
        int number = sc.nextInt();
        sc.nextLine();

        DateTimeFormatter fmtDMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Date (dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(sc.nextLine().trim(), fmtDMY);

        System.out.print("Contract value: $");
        double value = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter number of installments: ");
        int numberOfMonths = sc.nextInt();
        sc.nextLine();

        Contract contract = new Contract(number, date, value);
        ProcessingService paypalService = new ProcessingService(new PaypalPaymentService());
        paypalService.processMonthlyInstallments(contract, numberOfMonths);

        System.out.println("Installments:");
        for (Installment installment : contract.getInstallments()) {
            System.out.printf("%s - $%.2f\n", installment.getDueDate().format(fmtDMY), installment.getAmount());
        }

        sc.close();
        System.out.println();
    }

}
