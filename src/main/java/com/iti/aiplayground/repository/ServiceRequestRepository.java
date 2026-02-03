package com.iti.aiplayground.repository;

import com.iti.aiplayground.model.ApprovalStatus;
import com.iti.aiplayground.model.ServiceRequest;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByStatus(ApprovalStatus status);
}
