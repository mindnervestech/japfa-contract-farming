package com.japfa.mnt.app.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PurchaseOrder.
 */
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_name")
    private String farmerName;

    @Column(name = "flock_number")
    private String flockNumber;

    @Column(name = "p_o_number")
    private String pONumber;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_id")
    private String itemID;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "transpoter_name")
    private String transpoterName;

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

    public PurchaseOrder farmerName(String farmerName) {
        this.farmerName = farmerName;
        return this;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFlockNumber() {
        return flockNumber;
    }

    public PurchaseOrder flockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
        return this;
    }

    public void setFlockNumber(String flockNumber) {
        this.flockNumber = flockNumber;
    }

    public String getpONumber() {
        return pONumber;
    }

    public PurchaseOrder pONumber(String pONumber) {
        this.pONumber = pONumber;
        return this;
    }

    public void setpONumber(String pONumber) {
        this.pONumber = pONumber;
    }

    public String getItemName() {
        return itemName;
    }

    public PurchaseOrder itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public PurchaseOrder itemID(String itemID) {
        this.itemID = itemID;
        return this;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public Double getQuantity() {
        return quantity;
    }

    public PurchaseOrder quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public PurchaseOrder supplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getTranspoterName() {
        return transpoterName;
    }

    public PurchaseOrder transpoterName(String transpoterName) {
        this.transpoterName = transpoterName;
        return this;
    }

    public void setTranspoterName(String transpoterName) {
        this.transpoterName = transpoterName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseOrder)) {
            return false;
        }
        return id != null && id.equals(((PurchaseOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
            "id=" + getId() +
            ", farmerName='" + getFarmerName() + "'" +
            ", flockNumber='" + getFlockNumber() + "'" +
            ", pONumber='" + getpONumber() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", itemID='" + getItemID() + "'" +
            ", quantity=" + getQuantity() +
            ", supplierName='" + getSupplierName() + "'" +
            ", transpoterName='" + getTranspoterName() + "'" +
            "}";
    }
}
