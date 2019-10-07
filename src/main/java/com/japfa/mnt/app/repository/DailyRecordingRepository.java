package com.japfa.mnt.app.repository;
import com.japfa.mnt.app.domain.DailyRecording;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DailyRecording entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyRecordingRepository extends JpaRepository<DailyRecording, Long> {

}
