package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A IssuedStockMaster.
 */
@Entity
@Table(name = "issued_stock_master")
public class IssuedStockMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flock_number")
    private String flockNumber;

    @Column(name = "material_code")
    private String materialCode;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "stock_issued")
    private Integer stockIssued;

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

    public IssuedStockMaster flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public IssuedStockMaster materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public IssuedStockMaster materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getStockIssued() {
        return stockIssued;
    }

    public IssuedStockMaster stockIssued(Integer stockIssued) {
        this.stockIssued = stockIssued;
        return this;
    }

    public void setStockIssued(Integer stockIssued) {
        this.stockIssued = stockIssued;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssuedStockMaster)) {
            return false;
        }
        return id != null && id.equals(((IssuedStockMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IssuedStockMaster{" +
            "id=" + getId() +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", materialCode='" + getMaterialCode() + "'" +
            ", materialName='" + getMaterialName() + "'" +
            ", stockIssued=" + getStockIssued() +
            "}";
    }
}
