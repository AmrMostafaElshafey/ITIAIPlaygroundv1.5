package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.LearningDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningDepartmentRepository extends JpaRepository<LearningDepartment, Long> {
}
