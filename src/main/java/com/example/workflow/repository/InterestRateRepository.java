package com.example.workflow.repository;

import com.example.workflow.model.InterestRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRateModel, Integer> {

    // Find by bank name and loan type
    List<InterestRateModel> findByBankNameAndLoanType(String bankName, String loanType);

    // Find by loan type
    List<InterestRateModel> findByLoanType(String loanType);

    // Find by bank name
    List<InterestRateModel> findByBankName(String bankName);
}

