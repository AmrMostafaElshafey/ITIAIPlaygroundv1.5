package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.HomePageConfig;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomePageConfigRepository extends JpaRepository<HomePageConfig, Long> {
    Optional<HomePageConfig> findTopByOrderByUpdatedAtDesc();
}
