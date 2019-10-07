package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.SAPMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SAPMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SAPMasterRepository extends JpaRepository<SAPMaster, Long> {

}
