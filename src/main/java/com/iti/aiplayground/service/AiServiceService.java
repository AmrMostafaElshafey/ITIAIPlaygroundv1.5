package com.iti.aiplayground.service;

import com.iti.aiplayground.model.AiService;
import com.iti.aiplayground.repository.AiServiceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AiServiceService {
    private final AiServiceRepository repository;

    public AiServiceService(AiServiceRepository repository) {
        this.repository = repository;
    }

    public List<AiService> findAll() {
        return repository.findAll();
    }

    public Optional<AiService> findById(Long id) {
        return repository.findById(id);
    }

    public AiService save(AiService service) {
        return repository.save(service);
    }

    public void softDelete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    public long count() {
        return repository.count();
    }
}
