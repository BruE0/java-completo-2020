package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Client;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import entities.enums.OrderStatus;

public class Program {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        Client client = clientFromInput(sc);
        Order order = orderFromInput(client, sc);

        System.out.println("\nORDER SUMMARY:");
        DateTimeFormatter fmtDMYHMS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Order moment: " + order.getMoment().format(fmtDMYHMS));
        String status = order.getStatus().toString();
        status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        System.out.println("Order status: " + status);
        System.out.println("Client: " + order.getClient().getName() + " (" + order.getClient().getBirthDate() + ") "
                + "- " + order.getClient().getEmail());
        System.out.println("Order items:");
        for (OrderItem item : order.getOrderItems()) {
            System.out.println(item);
        }
        System.out.printf("Total price: $%.2f\n", order.total());
        sc.close();
    }

    public static Client clientFromInput(Scanner sc) {
        System.out.println("Enter client data:");
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        System.out.print("Birth date (DD/MM/YYYY): ");
        DateTimeFormatter fmtDDMMYYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate birthDate = LocalDate.parse(sc.nextLine().trim(), fmtDDMMYYYY);

        return new Client(name, email, birthDate);
    }

    public static Order orderFromInput(Client client, Scanner sc) {
        System.out.println("Enter order data:");
        System.out.print("Status ");
        System.out.print(Arrays.toString(OrderStatus.values()));
        System.out.print(": ");
        OrderStatus status = OrderStatus.valueOf(sc.nextLine().trim().toUpperCase());

        System.out.print("How many items to this order? ");
        int numberOfItems = sc.nextInt();
        sc.nextLine();

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 1; i <= numberOfItems; ++i) {
            System.out.println("Enter #" + i + " item data: ");
            System.out.print("Product name: ");
            String name = sc.nextLine().trim();

            System.out.print("Product price: ");
            double price = sc.nextDouble();
            sc.nextLine();

            System.out.print("Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            orderItems.add(new OrderItem(qty, price, new Product(name, price)));
        }
        Order order = new Order(LocalDateTime.now(), status, client);
        for (OrderItem item : orderItems) {
            order.addItem(item);
        }
        return order;
    }
}
