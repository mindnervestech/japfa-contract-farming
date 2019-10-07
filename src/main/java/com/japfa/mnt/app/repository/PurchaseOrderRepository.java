package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
