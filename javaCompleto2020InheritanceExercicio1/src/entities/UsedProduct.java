package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsedProduct extends Product {
    private LocalDate manufactureDate;

    public UsedProduct() {
    }

    public UsedProduct(String name, double price, LocalDate manufactureDate) {
        super(name, price);
        this.manufactureDate = manufactureDate;
    }

    @Override
    public final String priceTag() {
        DateTimeFormatter fmtDDMMYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("%s (used) $ %.2f (Manufacture Date: %s)", getName(), getPrice(),
                manufactureDate.format(fmtDDMMYYY));
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

}
