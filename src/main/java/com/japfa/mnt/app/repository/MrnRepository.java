package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.Mrn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mrn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MrnRepository extends JpaRepository<Mrn, Long> {

}
