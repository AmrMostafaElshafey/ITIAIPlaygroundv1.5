package com.iti.aiplayground.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "service_requests")
@SQLDelete(sql = "UPDATE service_requests SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ServiceRequest extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registration_request_id")
    private RegistrationRequest registrationRequest;

    private String serviceName;
    private String requestNotes;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    private String reviewerNotes;

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(RegistrationRequest registrationRequest) {
        this.registrationRequest = registrationRequest;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequestNotes() {
        return requestNotes;
    }

    public void setRequestNotes(String requestNotes) {
        this.requestNotes = requestNotes;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getReviewerNotes() {
        return reviewerNotes;
    }

    public void setReviewerNotes(String reviewerNotes) {
        this.reviewerNotes = reviewerNotes;
    }
}
