package model.services;

public interface PaymentService {

    double paymentFee(double amount);
    double interestRate(double amount, int numberOfInstallments);
}
