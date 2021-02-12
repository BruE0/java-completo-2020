package model.services;

public class PaypalPaymentService implements PaymentService {

    @Override
    public double paymentFee(double amount) {
        return amount * 0.02;
    }

    @Override
    public double interestRate(double amount, int numberOfInstallments) {
        return amount * 0.01 * numberOfInstallments;
    }

}
