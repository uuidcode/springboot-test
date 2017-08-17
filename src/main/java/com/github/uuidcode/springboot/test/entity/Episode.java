package com.github.uuidcode.springboot.test.entity;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.assertj.core.api.Assertions;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Episode {
    public enum EpisodeType {
        OPEN, CLOSED
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long episodeId;

    private String name;
    private LocalDateTime addedDateTime;
    private LocalDateTime updatedDateTime;
    private Long projectId;
    private EpisodeType episodeType;

    public EpisodeType getEpisodeType() {
        return this.episodeType;
    }

    public Optional<EpisodeType> getEpisodeTypeOptional() {
        return Optional.ofNullable(this.episodeType);
    }

    public Episode setEpisodeType(EpisodeType episodeType) {
        this.episodeType = episodeType;
        return this;
    }

    public Episode episodeTypeIsNull() {
        Assertions.assertThat(this.episodeType).isNull();
        return this;
    }

    public Episode episodeTypeIsNotNull() {
        Assertions.assertThat(this.episodeType).isNotNull();
        return this;
    }

    public Episode episodeTypeIsEqualTo(EpisodeType episodeType) {
        Assertions.assertThat(this.episodeType).isEqualTo(episodeType);
        return this;
    }
    public Long getProjectId() {
        return this.projectId;
    }

    public Episode setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public LocalDateTime getUpdatedDateTime() {
        return this.updatedDateTime;
    }

    public Episode setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
        return this;
    }
    public LocalDateTime getAddedDateTime() {
        return this.addedDateTime;
    }

    public Episode setAddedDateTime(LocalDateTime addedDateTime) {
        this.addedDateTime = addedDateTime;
        return this;
    }
    public String getName() {
        return this.name;
    }

    public Episode setName(String name) {
        this.name = name;
        return this;
    }

    public Long getEpisodeId() {
        return this.episodeId;
    }

    public Episode setEpisodeId(Long episodeId) {
        this.episodeId = episodeId;
        return this;
    }
}
