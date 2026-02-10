package com.iti.aiplayground.service;

import com.iti.aiplayground.model.PromptLibraryOverview;
import com.iti.aiplayground.repository.PromptLibraryOverviewRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PromptLibraryOverviewService {
    private final PromptLibraryOverviewRepository repository;

    public PromptLibraryOverviewService(PromptLibraryOverviewRepository repository) {
        this.repository = repository;
    }

    public PromptLibraryOverview save(PromptLibraryOverview overview) {
        return repository.save(overview);
    }

    public Optional<PromptLibraryOverview> getLatest() {
        return repository.findTopByOrderByUpdatedAtDesc();
    }
}
