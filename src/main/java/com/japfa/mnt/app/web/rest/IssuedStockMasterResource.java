package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.IssuedStockMaster;
import com.japfa.mnt.app.repository.IssuedStockMasterRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.IssuedStockMaster}.
 */
@RestController
@RequestMapping("/api")
public class IssuedStockMasterResource {

    private final Logger log = LoggerFactory.getLogger(IssuedStockMasterResource.class);

    private static final String ENTITY_NAME = "issuedStockMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IssuedStockMasterRepository issuedStockMasterRepository;

    public IssuedStockMasterResource(IssuedStockMasterRepository issuedStockMasterRepository) {
        this.issuedStockMasterRepository = issuedStockMasterRepository;
    }

    /**
     * {@code POST  /issued-stock-masters} : Create a new issuedStockMaster.
     *
     * @param issuedStockMaster the issuedStockMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new issuedStockMaster, or with status {@code 400 (Bad Request)} if the issuedStockMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/issued-stock-masters")
    public ResponseEntity<IssuedStockMaster> createIssuedStockMaster(@RequestBody IssuedStockMaster issuedStockMaster) throws URISyntaxException {
        log.debug("REST request to save IssuedStockMaster : {}", issuedStockMaster);
        if (issuedStockMaster.getId() != null) {
            throw new BadRequestAlertException("A new issuedStockMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IssuedStockMaster result = issuedStockMasterRepository.save(issuedStockMaster);
        return ResponseEntity.created(new URI("/api/issued-stock-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /issued-stock-masters} : Updates an existing issuedStockMaster.
     *
     * @param issuedStockMaster the issuedStockMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated issuedStockMaster,
     * or with status {@code 400 (Bad Request)} if the issuedStockMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the issuedStockMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/issued-stock-masters")
    public ResponseEntity<IssuedStockMaster> updateIssuedStockMaster(@RequestBody IssuedStockMaster issuedStockMaster) throws URISyntaxException {
        log.debug("REST request to update IssuedStockMaster : {}", issuedStockMaster);
        if (issuedStockMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IssuedStockMaster result = issuedStockMasterRepository.save(issuedStockMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, issuedStockMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /issued-stock-masters} : get all the issuedStockMasters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of issuedStockMasters in body.
     */
    @GetMapping("/issued-stock-masters")
    public List<IssuedStockMaster> getAllIssuedStockMasters() {
        log.debug("REST request to get all IssuedStockMasters");
        return issuedStockMasterRepository.findAll();
    }

    /**
     * {@code GET  /issued-stock-masters/:id} : get the "id" issuedStockMaster.
     *
     * @param id the id of the issuedStockMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the issuedStockMaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/issued-stock-masters/{id}")
    public ResponseEntity<IssuedStockMaster> getIssuedStockMaster(@PathVariable Long id) {
        log.debug("REST request to get IssuedStockMaster : {}", id);
        Optional<IssuedStockMaster> issuedStockMaster = issuedStockMasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(issuedStockMaster);
    }

    /**
     * {@code DELETE  /issued-stock-masters/:id} : delete the "id" issuedStockMaster.
     *
     * @param id the id of the issuedStockMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/issued-stock-masters/{id}")
    public ResponseEntity<Void> deleteIssuedStockMaster(@PathVariable Long id) {
        log.debug("REST request to delete IssuedStockMaster : {}", id);
        issuedStockMasterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
