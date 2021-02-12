package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.enums.WorkerLevel;

public class Worker {
    private String name;
    private WorkerLevel level;
    private double baseSalary;
    private Department department;
    private List<HourContract> contracts = new ArrayList<>();

    public Worker(String name, WorkerLevel level, Department department, double baseSalary) {
        this.name = name;
        this.level = level;
        this.department = department;
        this.baseSalary = baseSalary;
    }
    
    public Worker() {
    }

    public void addContract(HourContract contract) {
        contracts.add(contract);
    }

    public void removeContract(HourContract contract) {
        contracts.remove(contract);
    }

    public double income(int year, int month) {
        double total = baseSalary;
        for (HourContract contract : contracts) {
            LocalDate contractDate = contract.getDate();
            if (contractDate.getYear() == year && contractDate.getMonthValue() == month) {
                total += contract.totalValue();
            }
        }
        return total;
    }

    public String getName() {
        return name;
    }

    public WorkerLevel getLevel() {
        return level;
    }

    public Department getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public List<HourContract> getContracts() {
        return contracts;
    }

    @Override
    public String toString() {
        return "Worker [name=" + name + ", level=" + level + ", baseSalary=" + baseSalary + ", department=" + department
                + ", contracts=" + contracts + "]";
    }
    
    

}
