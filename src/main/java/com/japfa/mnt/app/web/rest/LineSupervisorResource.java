package com.japfa.mnt.app.web.rest;

import com.japfa.mnt.app.domain.LineSupervisor;
import com.japfa.mnt.app.repository.LineSupervisorRepository;
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
 * REST controller for managing {@link com.japfa.mnt.app.domain.LineSupervisor}.
 */
@RestController
@RequestMapping("/api")
public class LineSupervisorResource {

    private final Logger log = LoggerFactory.getLogger(LineSupervisorResource.class);

    private static final String ENTITY_NAME = "lineSupervisor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineSupervisorRepository lineSupervisorRepository;

    public LineSupervisorResource(LineSupervisorRepository lineSupervisorRepository) {
        this.lineSupervisorRepository = lineSupervisorRepository;
    }

    /**
     * {@code POST  /line-supervisors} : Create a new lineSupervisor.
     *
     * @param lineSupervisor the lineSupervisor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineSupervisor, or with status {@code 400 (Bad Request)} if the lineSupervisor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/line-supervisors")
    public ResponseEntity<LineSupervisor> createLineSupervisor(@RequestBody LineSupervisor lineSupervisor) throws URISyntaxException {
        log.debug("REST request to save LineSupervisor : {}", lineSupervisor);
        if (lineSupervisor.getId() != null) {
            throw new BadRequestAlertException("A new lineSupervisor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineSupervisor result = lineSupervisorRepository.save(lineSupervisor);
        return ResponseEntity.created(new URI("/api/line-supervisors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /line-supervisors} : Updates an existing lineSupervisor.
     *
     * @param lineSupervisor the lineSupervisor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineSupervisor,
     * or with status {@code 400 (Bad Request)} if the lineSupervisor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineSupervisor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/line-supervisors")
    public ResponseEntity<LineSupervisor> updateLineSupervisor(@RequestBody LineSupervisor lineSupervisor) throws URISyntaxException {
        log.debug("REST request to update LineSupervisor : {}", lineSupervisor);
        if (lineSupervisor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineSupervisor result = lineSupervisorRepository.save(lineSupervisor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineSupervisor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /line-supervisors} : get all the lineSupervisors.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineSupervisors in body.
     */
    @GetMapping("/line-supervisors")
    public List<LineSupervisor> getAllLineSupervisors() {
        log.debug("REST request to get all LineSupervisors");
        return lineSupervisorRepository.findAll();
    }

    /**
     * {@code GET  /line-supervisors/:id} : get the "id" lineSupervisor.
     *
     * @param id the id of the lineSupervisor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineSupervisor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/line-supervisors/{id}")
    public ResponseEntity<LineSupervisor> getLineSupervisor(@PathVariable Long id) {
        log.debug("REST request to get LineSupervisor : {}", id);
        Optional<LineSupervisor> lineSupervisor = lineSupervisorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lineSupervisor);
    }

    /**
     * {@code DELETE  /line-supervisors/:id} : delete the "id" lineSupervisor.
     *
     * @param id the id of the lineSupervisor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/line-supervisors/{id}")
    public ResponseEntity<Void> deleteLineSupervisor(@PathVariable Long id) {
        log.debug("REST request to delete LineSupervisor : {}", id);
        lineSupervisorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
