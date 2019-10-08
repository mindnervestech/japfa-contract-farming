package com.japfa.mnt.app.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "daily_recording")
public class DailyRecording implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flock_number")
    private String flockNumber;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "chiks_sampling_weight")
    private Integer chiksSamplingWeight;

    @Column(name = "chiks_condition")
    private String chiksCondition;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_on")
    private LocalDate createdOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlockNumber() {
        return flockNumber;
    }

    public DailyRecording flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public DailyRecording materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public Integer getChiksSamplingWeight() {
        return chiksSamplingWeight;
    }

    public DailyRecording chiksSamplingWeight(Integer chiksSamplingWeight) {
        this.chiksSamplingWeight = chiksSamplingWeight;
        return this;
    }

    public void setChiksSamplingWeight(Integer chiksSamplingWeight) {
        this.chiksSamplingWeight = chiksSamplingWeight;
    }

    public String getChiksCondition() {
        return chiksCondition;
    }

    public DailyRecording chiksCondition(String chiksCondition) {
        this.chiksCondition = chiksCondition;
        return this;
    }

    public void setChiksCondition(String chiksCondition) {
        this.chiksCondition = chiksCondition;
    }

    public String getQuantity() {
        return quantity;
    }

    public DailyRecording quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public DailyRecording comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public DailyRecording createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public DailyRecording createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DailyRecording)) {
            return false;
        }
        return id != null && id.equals(((DailyRecording) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DailyRecording{" +
            "id=" + getId() +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", materialCode='" + getMaterialCode() + "'" +
            ", chiksSamplingWeight=" + getChiksSamplingWeight() +
            ", chiksCondition='" + getChiksCondition() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", comment='" + getComment() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdOn='" + getCreatedOn() + "'" +
            "}";
    }
}
