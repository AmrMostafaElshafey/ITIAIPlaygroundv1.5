package com.iti.aiplayground.service;

import com.iti.aiplayground.model.PromptLibraryItem;
import com.iti.aiplayground.repository.PromptLibraryItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PromptLibraryService {
    private final PromptLibraryItemRepository repository;

    public PromptLibraryService(PromptLibraryItemRepository repository) {
        this.repository = repository;
    }

    public PromptLibraryItem save(PromptLibraryItem item) {
        return repository.save(item);
    }

    public List<PromptLibraryItem> findAll() {
        return repository.findAll();
    }

    public List<PromptLibraryItem> findFeatured() {
        return repository.findTop6ByOrderByUpdatedAtDesc();
    }
}
