package com.example.workflow.service;

import com.example.workflow.model.*;
import com.example.workflow.repository.*;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;  // Added for date and time
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DmnService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private CollateralRepository collateralRepository;

    @Autowired
    private InterestRateRepository interestRateRepository;

    @Autowired
    private DecisionService decisionService;

    // Method to calculate monthly payment, total payment, and total interest
    private LoanCalculationModel calculateLoan(double loanAmount, double annualInterestRate, int loanTermInMonths) {
        // Convert annual interest rate to monthly
        double monthlyInterestRate = annualInterestRate / 100 / 12;

        // Calculate monthly payment using amortization formula
        double monthlyPayment = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermInMonths)) /
                (Math.pow(1 + monthlyInterestRate, loanTermInMonths) - 1);

        // Total payment over the life of the loan
        double totalPayment = monthlyPayment * loanTermInMonths;

        // Total interest paid
        double totalInterest = totalPayment - loanAmount;

        return new LoanCalculationModel(monthlyPayment, totalPayment, totalInterest);
    }

    public LoanModel createLoanApplication(LoanModel loanModel) {
        return loanApplicationRepository.save(loanModel);
    }

    public PersonalInfoModel savePersonalInfo(PersonalInfoModel personalInfoModel) {
        return personalInfoRepository.save(personalInfoModel);
    }

    public CollateralModel saveCollateral(CollateralModel collateralModel) {
        return collateralRepository.save(collateralModel);
    }

    public List<LoanModel> getAllLoanApplications() {
        return loanApplicationRepository.findAll();
    }

    public String evaluateLoanDecision(String decisionId, LoanModel loanModel, PersonalInfoModel personalInfoModel) {
        try {
            // Fetch interest rate based on bankName and loanType
            List<InterestRateModel> interestRates = interestRateRepository.findByBankNameAndLoanType(loanModel.getBankName(), loanModel.getLoanType());

            // Handle if no interest rate is found
            if (interestRates.isEmpty()) {
                throw new RuntimeException("No interest rate found for the specified bank and loan type: " + loanModel.getBankName() + ", " + loanModel.getLoanType());
            }

            // Get the first interest rate result
            InterestRateModel interestRateModel = interestRates.get(0);
            double interestRate = interestRateModel.getInterestRate();

            // Prepare collateral before evaluating the decision
            CollateralModel collateralModel = new CollateralModel(loanModel.getLoanType());
            String collateral = collateralModel.getCollateral();
            loanModel.setCollateral(collateral);

            // Prepare DMN input variables
            Map<String, Object> variables = new HashMap<>();
            variables.put("loanType", loanModel.getLoanType());
            variables.put("customerIncome", loanModel.getCustomerIncome());
            variables.put("maxLoanAmount", loanModel.getMaxLoanAmount());
            variables.put("loanTerm", loanModel.getLoanTerm());
            variables.put("interestRate", interestRate);
            variables.put("bankName", loanModel.getBankName());
            variables.put("fullName", personalInfoModel.getFullName());
            variables.put("phoneNumber", personalInfoModel.getPhoneNumber());
            variables.put("emailAddress", personalInfoModel.getEmailAddress());
            variables.put("telephoneNumber", personalInfoModel.getTelephoneNumber());

            // Evaluate DMN decision
            DmnDecisionResult decisionResult = decisionService
                    .evaluateDecisionByKey(decisionId)
                    .variables(variables)
                    .evaluate();

            // Check if the decision result is valid
            if (decisionResult == null || decisionResult.getSingleResult() == null) {
                throw new RuntimeException("No result returned from the decision evaluation");
            }

            // Access the single result
            Map<String, Object> singleResult = decisionResult.getSingleResult();

            // Log only the approval status
            String approvalStatus = (String) singleResult.get("approvalStatus");

            // Handle case where approval status might be null
            if (approvalStatus == null) {
                approvalStatus = "No approval status available";
            }

            // Calculate loan details
            LoanCalculationModel calculationResult = calculateLoan(
                    loanModel.getMaxLoanAmount(), // Loan amount
                    interestRate, // Interest rate
                    loanModel.getLoanTerm() // Loan term
            );

            // Set results into the loan model
            loanModel.setLoanRelease(approvalStatus);
            loanModel.setMonthlyPayment(calculationResult.getMonthlyPayment());
            loanModel.setTotalPayment(calculationResult.getTotalPayment());
            loanModel.setTotalInterest(calculationResult.getTotalInterest());

            // Set the created at date and time
            loanModel.setCreatedAt(LocalDateTime.now());

            // Save the loan model to the repository with all inputs and results
            loanApplicationRepository.save(loanModel);

            // Return the formatted result with loan calculation details and creation date
            return "Approval Status: " + approvalStatus + "\n" +
                    "Interest Rate: " + interestRate + "%" + "\n" +
                    "Collateral: " + collateral + "\n" +
                    "Monthly Payment: ₱" + String.format("%.2f", calculationResult.getMonthlyPayment()) + "\n" +
                    "Total Payment: ₱" + String.format("%.2f", calculationResult.getTotalPayment()) + "\n" +
                    "Total Interest: ₱" + String.format("%.2f", calculationResult.getTotalInterest()) + "\n" +
                    "Created At: " + loanModel.getCreatedAt();

        } catch (Exception e) {
            e.printStackTrace();  // Log the full stack trace for debugging
            return "Error evaluating loan decision: " + e.getMessage();
        }
    }
}
