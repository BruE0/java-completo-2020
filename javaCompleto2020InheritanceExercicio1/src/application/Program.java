package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.ImportedProduct;
import entities.Product;
import entities.UsedProduct;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> products = getProducts(sc);

        System.out.println("\nPRICE TAGS:");
        for (Product p : products) {
            System.out.println(p.priceTag());
        }

    }

    public static List<Product> getProducts(Scanner sc) {
        List<Product> products = new ArrayList<>();
        System.out.print("Enter number of products: ");
        int numberOfProducts = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfProducts; ++i) {
            System.out.println("Product #" + i + " data:");
            System.out.print("Common, used or imported (c/u/i)? ");
            char type = sc.nextLine().trim().charAt(0);

            System.out.print("Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            if (type == 'i') {
                System.out.print("Customs Fee: ");
                double fee = sc.nextDouble();
                sc.nextLine();
                products.add(new ImportedProduct(name, price, fee));

            } else if (type == 'u') {
                DateTimeFormatter fmtDDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                System.out.print("Manufacture Date (DD/MM/YYYY): ");
                LocalDate date = LocalDate.parse(sc.nextLine().trim(), fmtDDMMYYYY);
                products.add(new UsedProduct(name, price, date));

            } else {
                products.add(new Product(name, price));
            }
        }
        return products;
    }
}
