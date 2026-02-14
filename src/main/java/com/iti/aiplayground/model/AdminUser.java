package com.iti.aiplayground.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "admin_users")
@SQLDelete(sql = "UPDATE admin_users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class AdminUser extends BaseEntity {
    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private SystemRole role = SystemRole.ADMIN;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SystemRole getRole() {
        return role;
    }

    public void setRole(SystemRole role) {
        this.role = role;
    }
}
