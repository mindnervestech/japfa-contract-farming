package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.Mrn;
import com.japfa.mnt.app.repository.MrnRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.Mrn}.
 */
@RestController
@RequestMapping("/api")
public class MrnResource {

    private final Logger log = LoggerFactory.getLogger(MrnResource.class);

    private static final String ENTITY_NAME = "mrn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MrnRepository mrnRepository;

    public MrnResource(MrnRepository mrnRepository) {
        this.mrnRepository = mrnRepository;
    }

    /**
     * {@code POST  /mrns} : Create a new mrn.
     *
     * @param mrn the mrn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mrn, or with status {@code 400 (Bad Request)} if the mrn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mrns")
    public ResponseEntity<Mrn> createMrn(@RequestBody Mrn mrn) throws URISyntaxException {
        log.debug("REST request to save Mrn : {}", mrn);
        if (mrn.getId() != null) {
            throw new BadRequestAlertException("A new mrn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mrn result = mrnRepository.save(mrn);
        return ResponseEntity.created(new URI("/api/mrns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mrns} : Updates an existing mrn.
     *
     * @param mrn the mrn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mrn,
     * or with status {@code 400 (Bad Request)} if the mrn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mrn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mrns")
    public ResponseEntity<Mrn> updateMrn(@RequestBody Mrn mrn) throws URISyntaxException {
        log.debug("REST request to update Mrn : {}", mrn);
        if (mrn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Mrn result = mrnRepository.save(mrn);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mrn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mrns} : get all the mrns.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mrns in body.
     */
    @GetMapping("/mrns")
    public List<Mrn> getAllMrns() {
        log.debug("REST request to get all Mrns");
        return mrnRepository.findAll();
    }

    /**
     * {@code GET  /mrns/:id} : get the "id" mrn.
     *
     * @param id the id of the mrn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mrn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mrns/{id}")
    public ResponseEntity<Mrn> getMrn(@PathVariable Long id) {
        log.debug("REST request to get Mrn : {}", id);
        Optional<Mrn> mrn = mrnRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mrn);
    }

    /**
     * {@code DELETE  /mrns/:id} : delete the "id" mrn.
     *
     * @param id the id of the mrn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mrns/{id}")
    public ResponseEntity<Void> deleteMrn(@PathVariable Long id) {
        log.debug("REST request to delete Mrn : {}", id);
        mrnRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
