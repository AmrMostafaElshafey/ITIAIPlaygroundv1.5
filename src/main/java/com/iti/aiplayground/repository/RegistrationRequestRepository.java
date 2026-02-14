package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.RegistrationRequest;
import com.iti.aiplayground.model.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {
    List<RegistrationRequest> findByStatus(ApprovalStatus status);

    Optional<RegistrationRequest> findByEmailAndStatusAndRole(String email, ApprovalStatus status, UserRole role);
}
