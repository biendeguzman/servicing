package com.example.workflow.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Interest_Rates")
public class InterestRateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "loan_type", nullable = false)
    private String loanType;

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;

    // Constructors
    public InterestRateModel() {
    }

    public InterestRateModel(String bankName, String loanType, double interestRate) {
        this.bankName = bankName;
        this.loanType = loanType;
        this.interestRate = interestRate;
    }
}
