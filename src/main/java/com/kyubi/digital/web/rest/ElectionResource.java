package com.kyubi.digital.web.rest;

import com.kyubi.digital.service.ElectionService;
import com.kyubi.digital.web.rest.errors.BadRequestAlertException;
import com.kyubi.digital.service.dto.ElectionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.kyubi.digital.domain.Election}.
 */
@RestController
@RequestMapping("/api")
public class ElectionResource {

    private final Logger log = LoggerFactory.getLogger(ElectionResource.class);

    private static final String ENTITY_NAME = "election";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElectionService electionService;

    public ElectionResource(ElectionService electionService) {
        this.electionService = electionService;
    }

    /**
     * {@code POST  /elections} : Create a new election.
     *
     * @param electionDTO the electionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new electionDTO, or with status {@code 400 (Bad Request)} if the election has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elections")
    public ResponseEntity<ElectionDTO> createElection(@Valid @RequestBody ElectionDTO electionDTO) throws URISyntaxException {
        log.debug("REST request to save Election : {}", electionDTO);
        if (electionDTO.getId() != null) {
            throw new BadRequestAlertException("A new election cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElectionDTO result = electionService.save(electionDTO);
        return ResponseEntity.created(new URI("/api/elections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elections} : Updates an existing election.
     *
     * @param electionDTO the electionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated electionDTO,
     * or with status {@code 400 (Bad Request)} if the electionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the electionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elections")
    public ResponseEntity<ElectionDTO> updateElection(@Valid @RequestBody ElectionDTO electionDTO) throws URISyntaxException {
        log.debug("REST request to update Election : {}", electionDTO);
        if (electionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElectionDTO result = electionService.save(electionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, electionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elections} : get all the elections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elections in body.
     */
    @GetMapping("/elections")
    public List<ElectionDTO> getAllElections() {
        log.debug("REST request to get all Elections");
        return electionService.findAll();
    }

    /**
     * {@code GET  /elections/:id} : get the "id" election.
     *
     * @param id the id of the electionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the electionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elections/{id}")
    public ResponseEntity<ElectionDTO> getElection(@PathVariable Long id) {
        log.debug("REST request to get Election : {}", id);
        Optional<ElectionDTO> electionDTO = electionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(electionDTO);
    }

    /**
     * {@code DELETE  /elections/:id} : delete the "id" election.
     *
     * @param id the id of the electionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elections/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        log.debug("REST request to delete Election : {}", id);
        electionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
