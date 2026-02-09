package com.iti.aiplayground.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "registration_requests")
@SQLDelete(sql = "UPDATE registration_requests SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class RegistrationRequest extends BaseEntity {
    private String fullName;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(length = 500)
    private String photoPath;

    @Column(length = 500)
    private String workIdPath;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    private String reviewerNotes;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getWorkIdPath() {
        return workIdPath;
    }

    public void setWorkIdPath(String workIdPath) {
        this.workIdPath = workIdPath;
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
