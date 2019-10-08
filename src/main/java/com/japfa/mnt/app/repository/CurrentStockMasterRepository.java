package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.CurrentStockMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CurrentStockMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentStockMasterRepository extends JpaRepository<CurrentStockMaster, Long> {

}
