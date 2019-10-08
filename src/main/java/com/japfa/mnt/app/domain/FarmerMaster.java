package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FarmerMaster.
 */
@Entity
@Table(name = "farmer_master")
public class FarmerMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "farmer_id")
    private String farmerID;

    @Column(name = "flock_number")
    private String flockNumber;

    @Column(name = "address_of_farmer")
    private String addressOfFarmer;

    @Column(name = "line_supervisor_name")
    private String lineSupervisorName;

    @Column(name = "line_supervisor_id")
    private String lineSupervisorID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public FarmerMaster farmerName(String farmerName) {
        this.farmerName = farmerName;
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public FarmerMaster farmerID(String farmerID) {
        this.farmerID = farmerID;
        return this;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getFlockNumber() {
        return flockNumber;
    }

    public FarmerMaster flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getAddressOfFarmer() {
        return addressOfFarmer;
    }

    public FarmerMaster addressOfFarmer(String addressOfFarmer) {
        this.addressOfFarmer = addressOfFarmer;
        return this;
    }

    public void setAddressOfFarmer(String addressOfFarmer) {
        this.addressOfFarmer = addressOfFarmer;
    }

    public String getLineSupervisorName() {
        return lineSupervisorName;
    }

    public FarmerMaster lineSupervisorName(String lineSupervisorName) {
        this.lineSupervisorName = lineSupervisorName;
        return this;
    }

    public void setLineSupervisorName(String lineSupervisorName) {
        this.lineSupervisorName = lineSupervisorName;
    }

    public String getLineSupervisorID() {
        return lineSupervisorID;
    }

    public FarmerMaster lineSupervisorID(String lineSupervisorID) {
        this.lineSupervisorID = lineSupervisorID;
        return this;
    }

    public void setLineSupervisorID(String lineSupervisorID) {
        this.lineSupervisorID = lineSupervisorID;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmerMaster)) {
            return false;
        }
        return id != null && id.equals(((FarmerMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FarmerMaster{" +
            "id=" + getId() +
            ", farmerName='" + getFarmerName() + "'" +
            ", farmerID='" + getFarmerID() + "'" +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", addressOfFarmer='" + getAddressOfFarmer() + "'" +
            ", lineSupervisorName='" + getLineSupervisorName() + "'" +
            ", lineSupervisorID='" + getLineSupervisorID() + "'" +
            "}";
    }
}
