package application;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import models.entities.Account;
import models.exceptions.IllegalWithdrawalException;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        try {
            Account account;
            System.out.println("Enter account data:");
            System.out.print("Number: ");
            int accNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Holder: ");
            String holderName = sc.nextLine().trim();

            System.out.print("Initial balance: ");
            double balance = sc.nextDouble();
            sc.nextLine();

            System.out.print("Withdrawal limit: ");
            double withdrawLimit = sc.nextDouble();
            sc.nextLine();

            account = new Account(accNumber, holderName, balance, withdrawLimit);

            System.out.print("\nEnter amount for withdrawal: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            
            account.withdraw(amount);
            System.out.printf("New balance: $%.2f\n", account.getBalance());
        } catch (IllegalWithdrawalException e) {
            System.out.println("Withdraw Error: " + e.getMessage());
        } catch (NoSuchElementException | IllegalStateException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }
}
