package com.kyubi.digital.web.rest;

import com.kyubi.digital.service.FigureService;
import com.kyubi.digital.web.rest.errors.BadRequestAlertException;
import com.kyubi.digital.service.dto.FigureDTO;

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
 * REST controller for managing {@link com.kyubi.digital.domain.Figure}.
 */
@RestController
@RequestMapping("/api")
public class FigureResource {

    private final Logger log = LoggerFactory.getLogger(FigureResource.class);

    private static final String ENTITY_NAME = "figure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FigureService figureService;

    public FigureResource(FigureService figureService) {
        this.figureService = figureService;
    }

    /**
     * {@code POST  /figures} : Create a new figure.
     *
     * @param figureDTO the figureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new figureDTO, or with status {@code 400 (Bad Request)} if the figure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/figures")
    public ResponseEntity<FigureDTO> createFigure(@Valid @RequestBody FigureDTO figureDTO) throws URISyntaxException {
        log.debug("REST request to save Figure : {}", figureDTO);
        if (figureDTO.getId() != null) {
            throw new BadRequestAlertException("A new figure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FigureDTO result = figureService.save(figureDTO);
        return ResponseEntity.created(new URI("/api/figures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /figures} : Updates an existing figure.
     *
     * @param figureDTO the figureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated figureDTO,
     * or with status {@code 400 (Bad Request)} if the figureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the figureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/figures")
    public ResponseEntity<FigureDTO> updateFigure(@Valid @RequestBody FigureDTO figureDTO) throws URISyntaxException {
        log.debug("REST request to update Figure : {}", figureDTO);
        if (figureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FigureDTO result = figureService.save(figureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, figureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /figures} : get all the figures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of figures in body.
     */
    @GetMapping("/figures")
    public List<FigureDTO> getAllFigures() {
        log.debug("REST request to get all Figures");
        return figureService.findAll();
    }

    /**
     * {@code GET  /figures/:id} : get the "id" figure.
     *
     * @param id the id of the figureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the figureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/figures/{id}")
    public ResponseEntity<FigureDTO> getFigure(@PathVariable Long id) {
        log.debug("REST request to get Figure : {}", id);
        Optional<FigureDTO> figureDTO = figureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(figureDTO);
    }

    /**
     * {@code DELETE  /figures/:id} : delete the "id" figure.
     *
     * @param id the id of the figureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/figures/{id}")
    public ResponseEntity<Void> deleteFigure(@PathVariable Long id) {
        log.debug("REST request to delete Figure : {}", id);
        figureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
