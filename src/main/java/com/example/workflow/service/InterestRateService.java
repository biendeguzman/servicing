package com.example.workflow.service;

import com.example.workflow.model.InterestRateModel;
import com.example.workflow.repository.InterestRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class InterestRateService {

    @Autowired
    private InterestRateRepository interestRateRepository;

    // Method to create a new interest rate
    public InterestRateModel createInterestRate(InterestRateModel interestRate) {
        return interestRateRepository.save(interestRate);
    }

    // Method to retrieve all interest rates
    public List<InterestRateModel> getAllInterestRates() {
        return interestRateRepository.findAll();
    }

    // Method to retrieve interest rates by loan type and bank name
    public List<InterestRateModel> getInterestRatesByLoanTypeAndBank(String loanType, String bankName) {
        return interestRateRepository.findByBankNameAndLoanType(bankName, loanType);
    }

    // Method to retrieve interest rates by loan type
    public List<InterestRateModel> getInterestRatesByLoanType(String loanType) {
        return interestRateRepository.findByLoanType(loanType);
    }

    // Method to retrieve interest rates by bank name
    public List<InterestRateModel> getInterestRatesByBankName(String bankName) {
        return interestRateRepository.findByBankName(bankName);
    }
}

