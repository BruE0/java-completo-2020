package entities;

public class Product {
    private final String name;
    private final double price;
    private final int qty;

    public Product(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public String toString() {
        return "Product [name=" + name + ", price=" + price + ", qty=" + qty + "]";
    }

}
