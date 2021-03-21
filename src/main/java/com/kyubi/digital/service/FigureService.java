package com.kyubi.digital.service;

import com.kyubi.digital.service.dto.FigureDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kyubi.digital.domain.Figure}.
 */
public interface FigureService {

    /**
     * Save a figure.
     *
     * @param figureDTO the entity to save.
     * @return the persisted entity.
     */
    FigureDTO save(FigureDTO figureDTO);

    /**
     * Get all the figures.
     *
     * @return the list of entities.
     */
    List<FigureDTO> findAll();


    /**
     * Get the "id" figure.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FigureDTO> findOne(Long id);

    /**
     * Delete the "id" figure.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
