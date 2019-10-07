package com.japfa.mnt.app.domain;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "line_supervisor")
public class LineSupervisor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "branch_code")
    private String branchCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LineSupervisor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public LineSupervisor branchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineSupervisor)) {
            return false;
        }
        return id != null && id.equals(((LineSupervisor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LineSupervisor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            "}";
    }
}
