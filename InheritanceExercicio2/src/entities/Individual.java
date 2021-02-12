package entities;

public class Individual extends TaxPayer {
    private double healthExpense;

    public Individual(String name, double annualIncome, double healthExpense) {
        super(name, annualIncome);
        this.healthExpense = healthExpense;
    }

    public Individual() {
        super();
    }

    public double tax() {
        if (getAnnualIncome() < 20_000) {
            return Math.max((getAnnualIncome() * 0.15) - (healthExpense * 0.5), 0);
        } else {
            return Math.max((getAnnualIncome() * 0.25) - (healthExpense * 0.5), 0);
        }
    }

    public double getHealthExpense() {
        return healthExpense;
    }

    public void setHealthExpense(double healthExpense) {
        this.healthExpense = healthExpense;
    }
    
}
