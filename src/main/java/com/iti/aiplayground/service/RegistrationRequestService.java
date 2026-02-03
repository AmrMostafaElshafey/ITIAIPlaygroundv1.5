package com.iti.aiplayground.service;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.repository.RegistrationRequestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RegistrationRequestService {
    private final RegistrationRequestRepository repository;

    public RegistrationRequestService(RegistrationRequestRepository repository) {
        this.repository = repository;
    }

    public RegistrationRequest save(RegistrationRequest request) {
        return repository.save(request);
    }

    public List<RegistrationRequest> findAll() {
        return repository.findAll();
    }

    public List<RegistrationRequest> findApproved() {
        return repository.findByStatus(ApprovalStatus.APPROVED);
    }

    public Optional<RegistrationRequest> findById(Long id) {
        return repository.findById(id);
    }
}
