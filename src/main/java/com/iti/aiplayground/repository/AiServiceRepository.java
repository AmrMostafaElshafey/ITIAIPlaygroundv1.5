package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.AiService;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiServiceRepository extends JpaRepository<AiService, Long> {
    List<AiService> findByPublishOnHomeTrue();
}
