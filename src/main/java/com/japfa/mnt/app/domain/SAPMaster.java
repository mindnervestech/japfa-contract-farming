package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SAPMaster.
 */
@Entity
@Table(name = "sap_master")
public class SAPMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "farmer_id")
    private String farmerID;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "flock_number")
    private String flockNumber;

    @Column(name = "address_of_farmer")
    private String addressOfFarmer;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "p_o_number")
    private String pONumber;

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

    public SAPMaster farmerName(String farmerName) {
        this.farmerName = farmerName;
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public SAPMaster farmerID(String farmerID) {
        this.farmerID = farmerID;
        return this;
    }

    public void setFarmerID(String farmerID) {
        this.farmerID = farmerID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public SAPMaster branchCode(String branchCode) {
        this.branchCode = branchCode;
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFlockNumber() {
        return flockNumber;
    }

    public SAPMaster flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getAddressOfFarmer() {
        return addressOfFarmer;
    }

    public SAPMaster addressOfFarmer(String addressOfFarmer) {
        this.addressOfFarmer = addressOfFarmer;
        return this;
    }

    public void setAddressOfFarmer(String addressOfFarmer) {
        this.addressOfFarmer = addressOfFarmer;
    }

    public String getItemCode() {
        return itemCode;
    }

    public SAPMaster itemCode(String itemCode) {
        this.itemCode = itemCode;
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public SAPMaster quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getpONumber() {
        return pONumber;
    }

    public SAPMaster pONumber(String pONumber) {
        this.pONumber = pONumber;
        return this;
    }

    public void setpONumber(String pONumber) {
        this.pONumber = pONumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SAPMaster)) {
            return false;
        }
        return id != null && id.equals(((SAPMaster) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SAPMaster{" +
            "id=" + getId() +
            ", farmerName='" + getFarmerName() + "'" +
            ", farmerID='" + getFarmerID() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", addressOfFarmer='" + getAddressOfFarmer() + "'" +
            ", itemCode='" + getItemCode() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", pONumber='" + getpONumber() + "'" +
            "}";
    }
}
