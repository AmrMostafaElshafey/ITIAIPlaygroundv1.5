package com.iti.aiplayground.model;

import jakarta.persistence.Column;
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
@Table(name = "ai_services")
@SQLDelete(sql = "UPDATE ai_services SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AiService extends BaseEntity {
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private AdminUser owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @Column(length = 1000)
    private String description;

    private String status;

    @Enumerated(EnumType.STRING)
    private ServiceEligibility eligibility = ServiceEligibility.BOTH;

    private boolean publishOnHome = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AdminUser getOwner() {
        return owner;
    }

    public void setOwner(AdminUser owner) {
        this.owner = owner;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceEligibility getEligibility() {
        return eligibility;
    }

    public void setEligibility(ServiceEligibility eligibility) {
        this.eligibility = eligibility;
    }

    public boolean isPublishOnHome() {
        return publishOnHome;
    }

    public void setPublishOnHome(boolean publishOnHome) {
        this.publishOnHome = publishOnHome;
    }
}
