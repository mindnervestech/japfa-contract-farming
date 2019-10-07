package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.SAPMaster;
import com.japfa.mnt.app.repository.SAPMasterRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.SAPMaster}.
 */
@RestController
@RequestMapping("/api")
public class SAPMasterResource {

    private final Logger log = LoggerFactory.getLogger(SAPMasterResource.class);

    private static final String ENTITY_NAME = "sAPMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SAPMasterRepository sAPMasterRepository;

    public SAPMasterResource(SAPMasterRepository sAPMasterRepository) {
        this.sAPMasterRepository = sAPMasterRepository;
    }

    /**
     * {@code POST  /sap-masters} : Create a new sAPMaster.
     *
     * @param sAPMaster the sAPMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sAPMaster, or with status {@code 400 (Bad Request)} if the sAPMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sap-masters")
    public ResponseEntity<SAPMaster> createSAPMaster(@RequestBody SAPMaster sAPMaster) throws URISyntaxException {
        log.debug("REST request to save SAPMaster : {}", sAPMaster);
        if (sAPMaster.getId() != null) {
            throw new BadRequestAlertException("A new sAPMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SAPMaster result = sAPMasterRepository.save(sAPMaster);
        return ResponseEntity.created(new URI("/api/sap-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sap-masters} : Updates an existing sAPMaster.
     *
     * @param sAPMaster the sAPMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sAPMaster,
     * or with status {@code 400 (Bad Request)} if the sAPMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sAPMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sap-masters")
    public ResponseEntity<SAPMaster> updateSAPMaster(@RequestBody SAPMaster sAPMaster) throws URISyntaxException {
        log.debug("REST request to update SAPMaster : {}", sAPMaster);
        if (sAPMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SAPMaster result = sAPMasterRepository.save(sAPMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sAPMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sap-masters} : get all the sAPMasters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sAPMasters in body.
     */
    @GetMapping("/sap-masters")
    public List<SAPMaster> getAllSAPMasters() {
        log.debug("REST request to get all SAPMasters");
        return sAPMasterRepository.findAll();
    }

    /**
     * {@code GET  /sap-masters/:id} : get the "id" sAPMaster.
     *
     * @param id the id of the sAPMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sAPMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sap-masters/{id}")
    public ResponseEntity<SAPMaster> getSAPMaster(@PathVariable Long id) {
        log.debug("REST request to get SAPMaster : {}", id);
        Optional<SAPMaster> sAPMaster = sAPMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sAPMaster);
    }

    /**
     * {@code DELETE  /sap-masters/:id} : delete the "id" sAPMaster.
     *
     * @param id the id of the sAPMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sap-masters/{id}")
    public ResponseEntity<Void> deleteSAPMaster(@PathVariable Long id) {
        log.debug("REST request to delete SAPMaster : {}", id);
        sAPMasterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
