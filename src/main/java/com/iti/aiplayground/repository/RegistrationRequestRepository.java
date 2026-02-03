package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    List<RegistrationRequest> findByStatus(ApprovalStatus status);
}
