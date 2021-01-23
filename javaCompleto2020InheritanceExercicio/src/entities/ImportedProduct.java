package entities;

public class ImportedProduct extends Product {
    private double customsFee;

    public ImportedProduct() {
    }

    public ImportedProduct(String name, double price, double customsFee) {
        super(name, price);
        this.customsFee = customsFee;
    }

    @Override
    public final String priceTag() {
        return String.format("%s $ %.2f (Customs fee: $ %.2f)", getName(), totalPrice(), customsFee);
    }

    public double totalPrice() {
        return getPrice() + customsFee;
    }

    public double getCustomsFee() {
        return customsFee;
    }

    public void setCustomsFee(double customsFee) {
        this.customsFee = customsFee;
    }

}
