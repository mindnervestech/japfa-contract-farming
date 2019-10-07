package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.DailyRecording;
import com.japfa.mnt.app.repository.DailyRecordingRepository;
import com.japfa.mnt.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.japfa.mnt.app.domain.DailyRecording}.
 */
@RestController
@RequestMapping("/api")
public class DailyRecordingResource {

    private final Logger log = LoggerFactory.getLogger(DailyRecordingResource.class);

    private static final String ENTITY_NAME = "dailyRecording";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyRecordingRepository dailyRecordingRepository;

    public DailyRecordingResource(DailyRecordingRepository dailyRecordingRepository) {
        this.dailyRecordingRepository = dailyRecordingRepository;
    }

    /**
     * {@code POST  /daily-recordings} : Create a new dailyRecording.
     *
     * @param dailyRecording the dailyRecording to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyRecording, or with status {@code 400 (Bad Request)} if the dailyRecording has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-recordings")
    public ResponseEntity<DailyRecording> createDailyRecording(@RequestBody DailyRecording dailyRecording) throws URISyntaxException {
        log.debug("REST request to save DailyRecording : {}", dailyRecording);
        if (dailyRecording.getId() != null) {
            throw new BadRequestAlertException("A new dailyRecording cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyRecording result = dailyRecordingRepository.save(dailyRecording);
        return ResponseEntity.created(new URI("/api/daily-recordings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-recordings} : Updates an existing dailyRecording.
     *
     * @param dailyRecording the dailyRecording to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyRecording,
     * or with status {@code 400 (Bad Request)} if the dailyRecording is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyRecording couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-recordings")
    public ResponseEntity<DailyRecording> updateDailyRecording(@RequestBody DailyRecording dailyRecording) throws URISyntaxException {
        log.debug("REST request to update DailyRecording : {}", dailyRecording);
        if (dailyRecording.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DailyRecording result = dailyRecordingRepository.save(dailyRecording);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dailyRecording.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /daily-recordings} : get all the dailyRecordings.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyRecordings in body.
     */
    @GetMapping("/daily-recordings")
    public List<DailyRecording> getAllDailyRecordings() {
        log.debug("REST request to get all DailyRecordings");
        return dailyRecordingRepository.findAll();
    }

    /**
     * {@code GET  /daily-recordings/:id} : get the "id" dailyRecording.
     *
     * @param id the id of the dailyRecording to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyRecording, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-recordings/{id}")
    public ResponseEntity<DailyRecording> getDailyRecording(@PathVariable Long id) {
        log.debug("REST request to get DailyRecording : {}", id);
        Optional<DailyRecording> dailyRecording = dailyRecordingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dailyRecording);
    }

    /**
     * {@code DELETE  /daily-recordings/:id} : delete the "id" dailyRecording.
     *
     * @param id the id of the dailyRecording to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-recordings/{id}")
    public ResponseEntity<Void> deleteDailyRecording(@PathVariable Long id) {
        log.debug("REST request to delete DailyRecording : {}", id);
        dailyRecordingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
