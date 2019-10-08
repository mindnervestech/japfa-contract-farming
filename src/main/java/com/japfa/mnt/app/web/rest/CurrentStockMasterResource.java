package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.CurrentStockMaster;
import com.japfa.mnt.app.repository.CurrentStockMasterRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.CurrentStockMaster}.
 */
@RestController
@RequestMapping("/api")
public class CurrentStockMasterResource {

    private final Logger log = LoggerFactory.getLogger(CurrentStockMasterResource.class);

    private static final String ENTITY_NAME = "currentStockMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurrentStockMasterRepository currentStockMasterRepository;

    public CurrentStockMasterResource(CurrentStockMasterRepository currentStockMasterRepository) {
        this.currentStockMasterRepository = currentStockMasterRepository;
    }

    /**
     * {@code POST  /current-stock-masters} : Create a new currentStockMaster.
     *
     * @param currentStockMaster the currentStockMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new currentStockMaster, or with status {@code 400 (Bad Request)} if the currentStockMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/current-stock-masters")
    public ResponseEntity<CurrentStockMaster> createCurrentStockMaster(@RequestBody CurrentStockMaster currentStockMaster) throws URISyntaxException {
        log.debug("REST request to save CurrentStockMaster : {}", currentStockMaster);
        if (currentStockMaster.getId() != null) {
            throw new BadRequestAlertException("A new currentStockMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurrentStockMaster result = currentStockMasterRepository.save(currentStockMaster);
        return ResponseEntity.created(new URI("/api/current-stock-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /current-stock-masters} : Updates an existing currentStockMaster.
     *
     * @param currentStockMaster the currentStockMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated currentStockMaster,
     * or with status {@code 400 (Bad Request)} if the currentStockMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the currentStockMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/current-stock-masters")
    public ResponseEntity<CurrentStockMaster> updateCurrentStockMaster(@RequestBody CurrentStockMaster currentStockMaster) throws URISyntaxException {
        log.debug("REST request to update CurrentStockMaster : {}", currentStockMaster);
        if (currentStockMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurrentStockMaster result = currentStockMasterRepository.save(currentStockMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, currentStockMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /current-stock-masters} : get all the currentStockMasters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of currentStockMasters in body.
     */
    @GetMapping("/current-stock-masters")
    public List<CurrentStockMaster> getAllCurrentStockMasters() {
        log.debug("REST request to get all CurrentStockMasters");
        return currentStockMasterRepository.findAll();
    }

    /**
     * {@code GET  /current-stock-masters/:id} : get the "id" currentStockMaster.
     *
     * @param id the id of the currentStockMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the currentStockMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/current-stock-masters/{id}")
    public ResponseEntity<CurrentStockMaster> getCurrentStockMaster(@PathVariable Long id) {
        log.debug("REST request to get CurrentStockMaster : {}", id);
        Optional<CurrentStockMaster> currentStockMaster = currentStockMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(currentStockMaster);
    }

    /**
     * {@code DELETE  /current-stock-masters/:id} : delete the "id" currentStockMaster.
     *
     * @param id the id of the currentStockMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/current-stock-masters/{id}")
    public ResponseEntity<Void> deleteCurrentStockMaster(@PathVariable Long id) {
        log.debug("REST request to delete CurrentStockMaster : {}", id);
        currentStockMasterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
