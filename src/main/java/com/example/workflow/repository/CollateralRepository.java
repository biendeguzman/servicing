package com.example.workflow.repository;

import com.example.workflow.model.CollateralModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollateralRepository extends JpaRepository<CollateralModel, Long> {
}
