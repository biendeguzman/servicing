package com.example.workflow.controller;

import com.example.workflow.model.*;
import com.example.workflow.repository.CollateralRepository;
import com.example.workflow.repository.PersonalInfoRepository;
import com.example.workflow.service.DmnService;
import com.example.workflow.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.workflow.model.LoanEvaluationRequest;


import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class DmnController {

    @Autowired
    private DmnService dmnService;

    @Autowired
    private CollateralRepository collateralRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private InterestRateService interestRateService;

    // Create a loan application
    @PostMapping
    public ResponseEntity<LoanModel> createLoanApplication(@RequestBody LoanModel loanModel) {
        LoanModel createdLoanModel = dmnService.createLoanApplication(loanModel);
        return ResponseEntity.ok(createdLoanModel);
    }

    // Evaluate a loan decision using DMN
    @PostMapping("/evaluate")
    public ResponseEntity<String> evaluateLoanDecision(
            @RequestParam String decisionId,
            @RequestBody LoanEvaluationRequest loanEvaluationRequest) {
        try {
            LoanModel loanModel = loanEvaluationRequest.getLoanModel();
            PersonalInfoModel personalInfoModel = loanEvaluationRequest.getPersonalInfoModel();
            CollateralModel collateralModel = loanEvaluationRequest.getCollateralModel();

            // Save personal info, collateral, and approval to respective repositories
            collateralRepository.save(collateralModel);
            personalInfoRepository.save(personalInfoModel);

            // Add debug logging
            System.out.println("Evaluating loan decision with parameters:");
            System.out.println("Loan Model: " + loanModel);
            System.out.println("Personal Info: " + personalInfoModel);
            System.out.println("Collateral: " + collateralModel);

            // Evaluate loan decision using DMN service
            String decisionResult = dmnService.evaluateLoanDecision(decisionId, loanModel, personalInfoModel);

            return ResponseEntity.ok(decisionResult);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error evaluating loan decision: " + e.getMessage());
        }
    }

    // Save personal info
    @PostMapping("/personal-info")
    public ResponseEntity<PersonalInfoModel> createPersonalInfo(@RequestBody PersonalInfoModel personalInfoModel) {
        PersonalInfoModel createdPersonalInfo = dmnService.savePersonalInfo(personalInfoModel);
        return ResponseEntity.ok(createdPersonalInfo);
    }

    // Save collateral info
    @PostMapping("/collateral")
    public ResponseEntity<CollateralModel> createCollateral(@RequestBody CollateralModel collateralModel) {
        CollateralModel createdCollateral = dmnService.saveCollateral(collateralModel);
        return ResponseEntity.ok(createdCollateral);
    }

    // Create a new interest rate
    @PostMapping("/interest-rate")
    public ResponseEntity<InterestRateModel> createInterestRate(@RequestBody InterestRateModel interestRate) {
        InterestRateModel createdInterestRate = interestRateService.createInterestRate(interestRate);
        return ResponseEntity.ok(createdInterestRate);
    }

    // Get interest rates by loan type
    @GetMapping("/by-loan-type")
    public ResponseEntity<List<InterestRateModel>> getInterestRatesByLoanType(@RequestParam String loanType) {
        List<InterestRateModel> interestRates = interestRateService.getInterestRatesByLoanType(loanType);
        return ResponseEntity.ok(interestRates);
    }

    // Get interest rates by bank name
    @GetMapping("/by-bank-name")
    public ResponseEntity<List<InterestRateModel>> getInterestRatesByBankName(@RequestParam String bankName) {
        List<InterestRateModel> interestRates = interestRateService.getInterestRatesByBankName(bankName);
        return ResponseEntity.ok(interestRates);
    }

    // Get interest rates by loan type and bank name
    @GetMapping("/by-loan-type-and-bank")
    public ResponseEntity<List<InterestRateModel>> getInterestRatesByLoanTypeAndBank(
            @RequestParam String loanType,
            @RequestParam String bankName) {
        List<InterestRateModel> interestRates = interestRateService.getInterestRatesByLoanTypeAndBank(loanType, bankName);
        return ResponseEntity.ok(interestRates);
    }
}
