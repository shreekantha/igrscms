package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.SchemeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Scheme}.
 */
public interface SchemeService {

    /**
     * Save a scheme.
     *
     * @param schemeDTO the entity to save.
     * @return the persisted entity.
     */
    SchemeDTO save(SchemeDTO schemeDTO);

    /**
     * Get all the schemes.
     *
     * @return the list of entities.
     */
    List<SchemeDTO> findAll();


    /**
     * Get the "id" scheme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchemeDTO> findOne(Long id);

    /**
     * Delete the "id" scheme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
