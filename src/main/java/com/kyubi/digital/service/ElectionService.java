package com.kyubi.digital.service;

import com.kyubi.digital.service.dto.ElectionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kyubi.digital.domain.Election}.
 */
public interface ElectionService {

    /**
     * Save a election.
     *
     * @param electionDTO the entity to save.
     * @return the persisted entity.
     */
    ElectionDTO save(ElectionDTO electionDTO);

    /**
     * Get all the elections.
     *
     * @return the list of entities.
     */
    List<ElectionDTO> findAll();


    /**
     * Get the "id" election.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ElectionDTO> findOne(Long id);

    /**
     * Delete the "id" election.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
