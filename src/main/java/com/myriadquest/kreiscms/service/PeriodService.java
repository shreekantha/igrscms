package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.PeriodDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Period}.
 */
public interface PeriodService {

    /**
     * Save a period.
     *
     * @param periodDTO the entity to save.
     * @return the persisted entity.
     */
    PeriodDTO save(PeriodDTO periodDTO);

    /**
     * Get all the periods.
     *
     * @return the list of entities.
     */
    List<PeriodDTO> findAll();


    /**
     * Get the "id" period.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PeriodDTO> findOne(Long id);

    /**
     * Delete the "id" period.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
