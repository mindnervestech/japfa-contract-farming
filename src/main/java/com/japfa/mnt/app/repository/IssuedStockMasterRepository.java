package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.IssuedStockMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the IssuedStockMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IssuedStockMasterRepository extends JpaRepository<IssuedStockMaster, Long> {

}
