package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Program {

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter file full path: ");
        String pathString = sc.nextLine().trim();
        Path path;
        if (!pathString.isEmpty()) {
            path = Paths.get(pathString);
        } else {
            path = Paths.get("in.txt");
        }

        Map<String, Integer> candidateVote = new TreeMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            String[] parts;

            while (line != null) {
                parts = line.split(",");
                String name = parts[0].trim();
                int votes = Integer.parseInt(parts[1].trim());

                candidateVote.put(name, votes + candidateVote.getOrDefault(name, 0));

                line = reader.readLine();
            }
        }
        for (Map.Entry<String, Integer> entry : candidateVote.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());

        }

        sc.close();
    }
}
