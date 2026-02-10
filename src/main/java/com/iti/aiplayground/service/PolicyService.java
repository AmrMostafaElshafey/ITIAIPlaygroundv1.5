package com.iti.aiplayground.service;

import com.iti.aiplayground.model.Policy;
import com.iti.aiplayground.repository.PolicyRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    private final PolicyRepository repository;

    public PolicyService(PolicyRepository repository) {
        this.repository = repository;
    }

    public Policy save(Policy policy) {
        return repository.save(policy);
    }

    public Optional<Policy> getLatest() {
        return repository.findTopByOrderByUpdatedAtDesc();
    }
}
