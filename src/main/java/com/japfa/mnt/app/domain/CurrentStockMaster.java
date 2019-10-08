package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CurrentStockMaster.
 */
@Entity
@Table(name = "current_stock_master")
public class CurrentStockMaster implements Serializable {

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

    @Column(name = "stock_in_hand")
    private Integer stockInHand;

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

    public CurrentStockMaster flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public CurrentStockMaster materialCode(String materialCode) {
        this.materialCode = materialCode;
        return this;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public CurrentStockMaster materialName(String materialName) {
        this.materialName = materialName;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Integer getStockInHand() {
        return stockInHand;
    }

    public CurrentStockMaster stockInHand(Integer stockInHand) {
        this.stockInHand = stockInHand;
        return this;
    }

    public void setStockInHand(Integer stockInHand) {
        this.stockInHand = stockInHand;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrentStockMaster)) {
            return false;
        }
        return id != null && id.equals(((CurrentStockMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurrentStockMaster{" +
            "id=" + getId() +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", materialCode='" + getMaterialCode() + "'" +
            ", materialName='" + getMaterialName() + "'" +
            ", stockInHand=" + getStockInHand() +
            "}";
    }
}
