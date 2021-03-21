package com.kyubi.digital.service;

import com.kyubi.digital.service.dto.CandidateDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kyubi.digital.domain.Candidate}.
 */
public interface CandidateService {

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateDTO save(CandidateDTO candidateDTO);

    /**
     * Get all the candidates.
     *
     * @return the list of entities.
     */
    List<CandidateDTO> findAll();


    /**
     * Get the "id" candidate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateDTO> findOne(Long id);

    /**
     * Delete the "id" candidate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
