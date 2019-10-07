package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.LineSupervisor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LineSupervisor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LineSupervisorRepository extends JpaRepository<LineSupervisor, Long> {

}
