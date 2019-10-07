package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Mrn.
 */
@Entity
@Table(name = "mrn")
public class Mrn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "d_c_date")
    private LocalDate dCDate;

    @Column(name = "d_c_number")
    private String dCNumber;

    @Column(name = "posting_date")
    private LocalDate postingDate;

    @Column(name = "p_o_number")
    private String pONumber;

    @Column(name = "item_number")
    private String itemNumber;

    @Column(name = "avg_weight")
    private String avgWeight;

    @Column(name = "jhi_condition")
    private String condition;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "item_recieved")
    private Integer itemRecieved;

    @Column(name = "flock_number")
    private String flockNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public Mrn vehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
        return this;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDate getdCDate() {
        return dCDate;
    }

    public Mrn dCDate(LocalDate dCDate) {
        this.dCDate = dCDate;
        return this;
    }

    public void setdCDate(LocalDate dCDate) {
        this.dCDate = dCDate;
    }

    public String getdCNumber() {
        return dCNumber;
    }

    public Mrn dCNumber(String dCNumber) {
        this.dCNumber = dCNumber;
        return this;
    }

    public void setdCNumber(String dCNumber) {
        this.dCNumber = dCNumber;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public Mrn postingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
        return this;
    }

    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public String getpONumber() {
        return pONumber;
    }

    public Mrn pONumber(String pONumber) {
        this.pONumber = pONumber;
        return this;
    }

    public void setpONumber(String pONumber) {
        this.pONumber = pONumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public Mrn itemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
        return this;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getAvgWeight() {
        return avgWeight;
    }

    public Mrn avgWeight(String avgWeight) {
        this.avgWeight = avgWeight;
        return this;
    }

    public void setAvgWeight(String avgWeight) {
        this.avgWeight = avgWeight;
    }

    public String getCondition() {
        return condition;
    }

    public Mrn condition(String condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Mrn createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getItemRecieved() {
        return itemRecieved;
    }

    public Mrn itemRecieved(Integer itemRecieved) {
        this.itemRecieved = itemRecieved;
        return this;
    }

    public void setItemRecieved(Integer itemRecieved) {
        this.itemRecieved = itemRecieved;
    }

    public String getFlockNumber() {
        return flockNumber;
    }

    public Mrn flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mrn)) {
            return false;
        }
        return id != null && id.equals(((Mrn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mrn{" +
            "id=" + getId() +
            ", vehicleNumber='" + getVehicleNumber() + "'" +
            ", dCDate='" + getdCDate() + "'" +
            ", dCNumber='" + getdCNumber() + "'" +
            ", postingDate='" + getPostingDate() + "'" +
            ", pONumber='" + getpONumber() + "'" +
            ", itemNumber='" + getItemNumber() + "'" +
            ", avgWeight='" + getAvgWeight() + "'" +
            ", condition='" + getCondition() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", itemRecieved=" + getItemRecieved() +
            ", flockNumber='" + getFlockNumber() + "'" +
            "}";
    }
}
