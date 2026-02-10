package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.PromptLibraryItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptLibraryItemRepository extends JpaRepository<PromptLibraryItem, Long> {
    List<PromptLibraryItem> findTop6ByOrderByUpdatedAtDesc();
}
