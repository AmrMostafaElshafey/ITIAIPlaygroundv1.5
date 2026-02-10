package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.Policy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findTopByOrderByUpdatedAtDesc();
}
