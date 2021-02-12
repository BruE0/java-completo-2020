package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ProcessingService {

    private PaymentService paymentService;

    public ProcessingService() {
    }

    public ProcessingService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void processMonthlyInstallments(Contract contract, int numberOfInstallments) {
        LocalDate startDate = contract.getDate();
        double monthlyValue = contract.getContractValue() / numberOfInstallments;

        for (int i = 1; i <= numberOfInstallments; ++i) {
            LocalDate dueDate = startDate.plusMonths(i);
            double amount = monthlyValue + paymentService.interestRate(monthlyValue, i);
            amount += paymentService.paymentFee(amount);
            contract.addInstallment(new Installment(dueDate, amount));
        }
    }

}
