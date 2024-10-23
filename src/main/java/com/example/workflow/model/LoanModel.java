package com.example.workflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_model")
public class LoanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_type")
    private String loanType;

    private double customerIncome;
    private double maxLoanAmount;
    private int loanTerm;

    @Column(name = "collateral")
    private String collateral;

    private String bankName;

    @Column(name = "loan_release")
    private String loanRelease; // Approval status

    // Calculated results
    @Column(name = "monthly_payment")
    private double monthlyPayment;

    @Column(name = "total_payment")
    private double totalPayment;

    @Column(name = "total_interest")
    private double totalInterest;

    // New field to store the date and time when the loan was created
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public LoanModel() {}

    // Constructor with parameters
    public LoanModel(String loanType, double customerIncome, double maxLoanAmount, int loanTerm, String collateral, String bankName, String loanRelease, double monthlyPayment, double totalPayment, double totalInterest) {
        this.loanType = loanType;
        this.customerIncome = customerIncome;
        this.maxLoanAmount = maxLoanAmount;
        this.loanTerm = loanTerm;
        this.collateral = collateral;
        this.bankName = bankName;
        this.loanRelease = loanRelease;
        this.monthlyPayment = monthlyPayment;
        this.totalPayment = totalPayment;
        this.totalInterest = totalInterest;
    }

    // Automatically set the createdAt field before persisting the entity
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getCollateral() {
        return collateral;
    }

    public void setCollateral(String collateral) {
        this.collateral = collateral;
    }

    public double getCustomerIncome() {
        return customerIncome;
    }

    public void setCustomerIncome(double customerIncome) {
        this.customerIncome = customerIncome;
    }

    public double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLoanRelease() {
        return loanRelease;
    }

    public void setLoanRelease(String loanRelease) {
        this.loanRelease = loanRelease;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
