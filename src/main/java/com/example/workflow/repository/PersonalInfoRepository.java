package com.example.workflow.repository;

import com.example.workflow.model.PersonalInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfoModel, Long> {
}
