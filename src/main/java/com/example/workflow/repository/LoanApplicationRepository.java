package com.example.workflow.repository;

import com.example.workflow.model.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanApplicationRepository extends JpaRepository<LoanModel, Long> {
}
