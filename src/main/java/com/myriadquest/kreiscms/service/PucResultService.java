package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.PucResultDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.PucResult}.
 */
public interface PucResultService {

    /**
     * Save a pucResult.
     *
     * @param pucResultDTO the entity to save.
     * @return the persisted entity.
     */
    PucResultDTO save(PucResultDTO pucResultDTO);

    /**
     * Get all the pucResults.
     *
     * @return the list of entities.
     */
    List<PucResultDTO> findAll();


    /**
     * Get the "id" pucResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PucResultDTO> findOne(Long id);

    /**
     * Delete the "id" pucResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
