package com.kyubi.digital.web.rest;

import com.kyubi.digital.service.UserAppService;
import com.kyubi.digital.web.rest.errors.BadRequestAlertException;
import com.kyubi.digital.service.dto.UserAppDTO;

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
 * REST controller for managing {@link com.kyubi.digital.domain.UserApp}.
 */
@RestController
@RequestMapping("/api")
public class UserAppResource {

    private final Logger log = LoggerFactory.getLogger(UserAppResource.class);

    private static final String ENTITY_NAME = "userApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAppService userAppService;

    public UserAppResource(UserAppService userAppService) {
        this.userAppService = userAppService;
    }

    /**
     * {@code POST  /user-apps} : Create a new userApp.
     *
     * @param userAppDTO the userAppDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAppDTO, or with status {@code 400 (Bad Request)} if the userApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-apps")
    public ResponseEntity<UserAppDTO> createUserApp(@RequestBody UserAppDTO userAppDTO) throws URISyntaxException {
        log.debug("REST request to save UserApp : {}", userAppDTO);
        if (userAppDTO.getId() != null) {
            throw new BadRequestAlertException("A new userApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAppDTO result = userAppService.save(userAppDTO);
        return ResponseEntity.created(new URI("/api/user-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-apps} : Updates an existing userApp.
     *
     * @param userAppDTO the userAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAppDTO,
     * or with status {@code 400 (Bad Request)} if the userAppDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-apps")
    public ResponseEntity<UserAppDTO> updateUserApp(@RequestBody UserAppDTO userAppDTO) throws URISyntaxException {
        log.debug("REST request to update UserApp : {}", userAppDTO);
        if (userAppDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAppDTO result = userAppService.save(userAppDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAppDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-apps} : get all the userApps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApps in body.
     */
    @GetMapping("/user-apps")
    public List<UserAppDTO> getAllUserApps() {
        log.debug("REST request to get all UserApps");
        return userAppService.findAll();
    }

    /**
     * {@code GET  /user-apps/:id} : get the "id" userApp.
     *
     * @param id the id of the userAppDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAppDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-apps/{id}")
    public ResponseEntity<UserAppDTO> getUserApp(@PathVariable Long id) {
        log.debug("REST request to get UserApp : {}", id);
        Optional<UserAppDTO> userAppDTO = userAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAppDTO);
    }

    /**
     * {@code DELETE  /user-apps/:id} : delete the "id" userApp.
     *
     * @param id the id of the userAppDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-apps/{id}")
    public ResponseEntity<Void> deleteUserApp(@PathVariable Long id) {
        log.debug("REST request to delete UserApp : {}", id);
        userAppService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
