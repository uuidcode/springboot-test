package com.github.uuidcode.springboot.test.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.assertj.core.api.Assertions;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Project extends CoreEntity<Project> {
    public enum ProjectType {
        NORMAL,
        SPECIAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    private String name;

    private LocalDateTime addedDateTime;

    private LocalDateTime updatedDateTime;

    @Enumerated(EnumType.STRING)
    private ProjectType projectType;

    @Transient
    private Partner partner;

    public ProjectType getProjectType() {
        return this.projectType;
    }

    public Optional<ProjectType> getProjectTypeOptional() {
        return Optional.ofNullable(this.projectType);
    }

    public Project setProjectType(ProjectType projectType) {
        this.projectType = projectType;
        return this;
    }

    public Project projectTypeIsNull() {
        Assertions.assertThat(this.projectType).isNull();
        return this;
    }

    public Project projectTypeIsNotNull() {
        Assertions.assertThat(this.projectType).isNotNull();
        return this;
    }

    public Project projectTypeIsEqualTo(ProjectType projectType) {
        Assertions.assertThat(this.projectType).isEqualTo(projectType);
        return this;
    }

    public Partner getPartner() {
        return this.partner;
    }

    public Optional<Partner> getPartnerOptional() {
        return Optional.ofNullable(this.partner);
    }

    public Project setPartner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public Project partnerIsNull() {
        Assertions.assertThat(this.partner).isNull();
        return this;
    }

    public Project partnerIsNotNull() {
        Assertions.assertThat(this.partner).isNotNull();
        return this;
    }

    public Project partnerIsEqualTo(Partner partner) {
        Assertions.assertThat(this.partner).isEqualTo(partner);
        return this;
    }

    public LocalDateTime getUpdatedDateTime() {
        return this.updatedDateTime;
    }

    public Project setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
        return this;
    }
    public LocalDateTime getAddedDateTime() {
        return this.addedDateTime;
    }
    
    public Project setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
        return this;
    }
    public String getName() {
        return this.name;
    }

    public Project setName(String name) {
        this.name = name;
        return this;
    }

    public Project nameIsEqualsTo(String name) {
        assertThat(this.name).isEqualTo(name);
        return this;
    }

    public Project projectTypeIsEqualsTo(ProjectType projectType) {
        assertThat(this.projectType).isEqualTo(projectType);
        return this;
    }

    public Long getProjectId() {
        return this.projectId;
    }

    public Optional<Long> getProjectIdOptional() {
        return Optional.ofNullable(this.projectId);
    }

    public Project setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public Project projectIdIsNull() {
        Assertions.assertThat(this.projectId).isNull();
        return this;
    }

    public Project projectIdIsNotNull() {
        Assertions.assertThat(this.projectId).isNotNull();
        return this;
    }

    public Project projectIdIsEqualTo(Long projectId) {
        Assertions.assertThat(this.projectId).isEqualTo(projectId);
        return this;
    }
}
