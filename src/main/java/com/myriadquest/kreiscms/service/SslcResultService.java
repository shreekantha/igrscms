package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.SslcResultDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.SslcResult}.
 */
public interface SslcResultService {

    /**
     * Save a sslcResult.
     *
     * @param sslcResultDTO the entity to save.
     * @return the persisted entity.
     */
    SslcResultDTO save(SslcResultDTO sslcResultDTO);

    /**
     * Get all the sslcResults.
     *
     * @return the list of entities.
     */
    List<SslcResultDTO> findAll();


    /**
     * Get the "id" sslcResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SslcResultDTO> findOne(Long id);

    /**
     * Delete the "id" sslcResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
