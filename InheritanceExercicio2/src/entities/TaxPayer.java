package entities;

public abstract class TaxPayer {
    private String name;
    private double annualIncome;
    
    protected TaxPayer() {
    }
    
    protected TaxPayer(String name, double annualIncome) {
        this.name = name;
        this.annualIncome = annualIncome;
    }

    public abstract double tax();
    
    public String getName() {
        return name;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }
    
    
}
