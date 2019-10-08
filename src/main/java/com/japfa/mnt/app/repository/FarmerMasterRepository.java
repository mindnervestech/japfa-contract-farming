package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.FarmerMaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FarmerMaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmerMasterRepository extends JpaRepository<FarmerMaster, Long> {

}
