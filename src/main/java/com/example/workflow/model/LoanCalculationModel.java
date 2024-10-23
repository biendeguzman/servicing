package com.example.workflow.model;

public class LoanCalculationModel {
    private double monthlyPayment;
    private double totalPayment;
    private double totalInterest;

    public LoanCalculationModel(double monthlyPayment, double totalPayment, double totalInterest) {
        this.monthlyPayment = monthlyPayment;
        this.totalPayment = totalPayment;
        this.totalInterest = totalInterest;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public double getTotalInterest() {
        return totalInterest;
    }
}
