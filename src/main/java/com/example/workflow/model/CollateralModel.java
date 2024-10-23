package com.example.workflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CollateralModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanType;
    private String collateral;

    public CollateralModel() {}

    public CollateralModel(String loanType) {
        this.loanType = loanType;
        this.collateral = determineCollateral(loanType);
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
        this.collateral = determineCollateral(loanType);
    }

    public String getCollateral() {
        return collateral;
    }

    // Method to determine collateral based on loan type
    private String determineCollateral(String loanType) {
        switch (loanType) {
            case "Housing Loan":
                return "Property";
            case "Business Loan":
                return "Business Assets";
            case "Auto Loan":
                return "Vehicle";
            default:
                return "No collateral required";
        }
    }
}
