package application;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import entities.Product;

public class Program {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        String stringCsvPath = (args.length == 1) ? args[0] : "source.csv";

        Path sourceCsvPath = Paths.get(stringCsvPath);
        Path targetCsvPath = Paths.get("out", "target.csv");
        final List<Product> products = new ArrayList<>();

        try (final Stream<String> csvStream = Files.lines(sourceCsvPath)) {
            csvStream.map(line -> line.split(",")).forEach(line -> {
                String name = line[0];
                double price = Double.parseDouble(line[1].trim());
                int qty = Integer.parseInt(line[2].trim());

                products.add(new Product(name, price, qty));
            });
        }

        Files.createDirectories(targetCsvPath.getParent());

        try (final BufferedWriter writer = Files.newBufferedWriter(targetCsvPath, StandardOpenOption.CREATE)) {
            for (Product product : products) {
                writer.write(product.getName() + "," + product.getPrice() * product.getQty());
                writer.newLine();
            }
        }

        sc.close();
    }
}
