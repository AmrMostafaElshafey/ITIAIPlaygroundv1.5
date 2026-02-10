package com.iti.aiplayground.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "prompt_library_overview")
@SQLDelete(sql = "UPDATE prompt_library_overview SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PromptLibraryOverview extends BaseEntity {
    private String title;

    @Column(length = 2000)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
