package com.iti.aiplayground.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "prompt_library_items")
@SQLDelete(sql = "UPDATE prompt_library_items SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PromptLibraryItem extends BaseEntity {
    private String title;

    @Column(length = 2000)
    private String promptText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private LearningDepartment department;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public LearningDepartment getDepartment() {
        return department;
    }

    public void setDepartment(LearningDepartment department) {
        this.department = department;
    }
}
