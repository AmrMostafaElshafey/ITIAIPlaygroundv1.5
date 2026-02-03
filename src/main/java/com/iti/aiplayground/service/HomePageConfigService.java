package com.iti.aiplayground.service;

import com.iti.aiplayground.model.HomePageConfig;
import com.iti.aiplayground.repository.HomePageConfigRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class HomePageConfigService {
    private final HomePageConfigRepository repository;

    public HomePageConfigService(HomePageConfigRepository repository) {
        this.repository = repository;
    }

    public Optional<HomePageConfig> getLatestConfig() {
        return repository.findTopByOrderByUpdatedAtDesc();
    }

    public HomePageConfig save(HomePageConfig config) {
        return repository.save(config);
    }
}
