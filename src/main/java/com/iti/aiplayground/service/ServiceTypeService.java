package com.iti.aiplayground.service;

import com.iti.aiplayground.model.ServiceType;
import com.iti.aiplayground.repository.ServiceTypeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {
    private final ServiceTypeRepository repository;

    public ServiceTypeService(ServiceTypeRepository repository) {
        this.repository = repository;
    }

    public ServiceType save(ServiceType type) {
        return repository.save(type);
    }

    public List<ServiceType> findAll() {
        return repository.findAll();
    }

    public Optional<ServiceType> findById(Long id) {
        return repository.findById(id);
    }
}
