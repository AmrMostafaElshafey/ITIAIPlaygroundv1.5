package com.iti.aiplayground.service;

import com.iti.aiplayground.model.LearningDepartment;
import com.iti.aiplayground.repository.LearningDepartmentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LearningDepartmentService {
    private final LearningDepartmentRepository repository;

    public LearningDepartmentService(LearningDepartmentRepository repository) {
        this.repository = repository;
    }

    public LearningDepartment save(LearningDepartment department) {
        return repository.save(department);
    }

    public List<LearningDepartment> findAll() {
        return repository.findAll();
    }

    public Optional<LearningDepartment> findById(Long id) {
        return repository.findById(id);
    }
}
