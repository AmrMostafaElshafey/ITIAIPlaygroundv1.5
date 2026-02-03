package com.iti.aiplayground.service;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.ServiceRequest;
import com.iti.aiplayground.repository.ServiceRequestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestService {
    private final ServiceRequestRepository repository;

    public ServiceRequestService(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    public ServiceRequest save(ServiceRequest request) {
        return repository.save(request);
    }

    public List<ServiceRequest> findAll() {
        return repository.findAll();
    }

    public Optional<ServiceRequest> findById(Long id) {
        return repository.findById(id);
    }

    public List<ServiceRequest> findPending() {
        return repository.findByStatus(ApprovalStatus.PENDING);
    }
}
