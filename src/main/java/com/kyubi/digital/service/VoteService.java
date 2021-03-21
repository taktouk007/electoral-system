package com.kyubi.digital.service;

import com.kyubi.digital.service.dto.VoteDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kyubi.digital.domain.Vote}.
 */
public interface VoteService {

    /**
     * Save a vote.
     *
     * @param voteDTO the entity to save.
     * @return the persisted entity.
     */
    VoteDTO save(VoteDTO voteDTO);

    /**
     * Get all the votes.
     *
     * @return the list of entities.
     */
    List<VoteDTO> findAll();


    /**
     * Get the "id" vote.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VoteDTO> findOne(Long id);

    /**
     * Delete the "id" vote.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
