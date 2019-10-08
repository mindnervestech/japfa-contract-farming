package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.FarmerMaster;
import com.japfa.mnt.app.repository.FarmerMasterRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.FarmerMaster}.
 */
@RestController
@RequestMapping("/api")
public class FarmerMasterResource {

    private final Logger log = LoggerFactory.getLogger(FarmerMasterResource.class);

    private static final String ENTITY_NAME = "farmerMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmerMasterRepository farmerMasterRepository;

    public FarmerMasterResource(FarmerMasterRepository farmerMasterRepository) {
        this.farmerMasterRepository = farmerMasterRepository;
    }

    /**
     * {@code POST  /farmer-masters} : Create a new farmerMaster.
     *
     * @param farmerMaster the farmerMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farmerMaster, or with status {@code 400 (Bad Request)} if the farmerMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmer-masters")
    public ResponseEntity<FarmerMaster> createFarmerMaster(@RequestBody FarmerMaster farmerMaster) throws URISyntaxException {
        log.debug("REST request to save FarmerMaster : {}", farmerMaster);
        if (farmerMaster.getId() != null) {
            throw new BadRequestAlertException("A new farmerMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FarmerMaster result = farmerMasterRepository.save(farmerMaster);
        return ResponseEntity.created(new URI("/api/farmer-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmer-masters} : Updates an existing farmerMaster.
     *
     * @param farmerMaster the farmerMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmerMaster,
     * or with status {@code 400 (Bad Request)} if the farmerMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farmerMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmer-masters")
    public ResponseEntity<FarmerMaster> updateFarmerMaster(@RequestBody FarmerMaster farmerMaster) throws URISyntaxException {
        log.debug("REST request to update FarmerMaster : {}", farmerMaster);
        if (farmerMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FarmerMaster result = farmerMasterRepository.save(farmerMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmerMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /farmer-masters} : get all the farmerMasters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farmerMasters in body.
     */
    @GetMapping("/farmer-masters")
    public List<FarmerMaster> getAllFarmerMasters() {
        log.debug("REST request to get all FarmerMasters");
        return farmerMasterRepository.findAll();
    }

    /**
     * {@code GET  /farmer-masters/:id} : get the "id" farmerMaster.
     *
     * @param id the id of the farmerMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farmerMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmer-masters/{id}")
    public ResponseEntity<FarmerMaster> getFarmerMaster(@PathVariable Long id) {
        log.debug("REST request to get FarmerMaster : {}", id);
        Optional<FarmerMaster> farmerMaster = farmerMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(farmerMaster);
    }

    /**
     * {@code DELETE  /farmer-masters/:id} : delete the "id" farmerMaster.
     *
     * @param id the id of the farmerMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmer-masters/{id}")
    public ResponseEntity<Void> deleteFarmerMaster(@PathVariable Long id) {
        log.debug("REST request to delete FarmerMaster : {}", id);
        farmerMasterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
