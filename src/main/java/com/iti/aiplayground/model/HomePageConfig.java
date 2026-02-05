package com.iti.aiplayground.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "home_page_configs")
@SQLDelete(sql = "UPDATE home_page_configs SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class HomePageConfig extends BaseEntity {
    private String heroTitle;
    private String heroSubtitle;

    @Column(length = 1200)
    private String aboutText;

    private String contactEmail;
    private String heroCtaLabel;
    private String heroCtaUrl;

    public String getHeroTitle() {
        return heroTitle;
    }

    public void setHeroTitle(String heroTitle) {
        this.heroTitle = heroTitle;
    }

    public String getHeroSubtitle() {
        return heroSubtitle;
    }

    public void setHeroSubtitle(String heroSubtitle) {
        this.heroSubtitle = heroSubtitle;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getHeroCtaLabel() {
        return heroCtaLabel;
    }

    public void setHeroCtaLabel(String heroCtaLabel) {
        this.heroCtaLabel = heroCtaLabel;
    }

    public String getHeroCtaUrl() {
        return heroCtaUrl;
    }

    public void setHeroCtaUrl(String heroCtaUrl) {
        this.heroCtaUrl = heroCtaUrl;
    }
}
