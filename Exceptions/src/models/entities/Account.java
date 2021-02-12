package models.entities;

import models.exceptions.IllegalWithdrawalException;

public class Account {
    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;
    
    public Account () {
    }

    public Account(int number, String holder, double balance, double withdrawLimit) {
        super();
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
    
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalWithdrawalException("Not enough balance.");
        }
        if (amount > withdrawLimit) {
            throw new IllegalWithdrawalException("The amount exceeds withdraw limit.");
        }
        this.balance -= amount;
    }
    
    public int getNumber() {
        return number;
    }

    public String getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }
    
    
}
