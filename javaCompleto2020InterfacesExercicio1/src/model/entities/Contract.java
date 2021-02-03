package model.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Contract {

    private int number;
    private LocalDate date;
    private double contractValue;
    private List<Installment> installments = new ArrayList<>();

    public Contract() {
    }

    public Contract(int number, LocalDate date, double totalValue) {
        this.number = number;
        this.date = date;
        this.contractValue = totalValue;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getContractValue() {
        return contractValue;
    }

    public void setContractValue(double contractValue) {
        this.contractValue = contractValue;
    }

    public List<Installment> getInstallments() {
        return installments;
    }

    public void addInstallment(Installment installment) {
        installments.add(installment);
    }

    public void removeInstallment(Installment installment) {
        installments.remove(installment);
    }

    @Override
    public String toString() {
        return "Contract [number=" + number + ", date=" + date + ", contractValue=" + contractValue
                + ", installments=" + installments + "]";
    }

}
