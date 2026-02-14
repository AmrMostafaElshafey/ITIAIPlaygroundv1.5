package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.PromptLibraryOverview;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptLibraryOverviewRepository extends JpaRepository<PromptLibraryOverview, Long> {
    Optional<PromptLibraryOverview> findTopByOrderByUpdatedAtDesc();
}
