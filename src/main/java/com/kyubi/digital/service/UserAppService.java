package com.kyubi.digital.service;

import com.kyubi.digital.service.dto.UserAppDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kyubi.digital.domain.UserApp}.
 */
public interface UserAppService {

    /**
     * Save a userApp.
     *
     * @param userAppDTO the entity to save.
     * @return the persisted entity.
     */
    UserAppDTO save(UserAppDTO userAppDTO);

    /**
     * Get all the userApps.
     *
     * @return the list of entities.
     */
    List<UserAppDTO> findAll();


    /**
     * Get the "id" userApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserAppDTO> findOne(Long id);

    /**
     * Delete the "id" userApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
