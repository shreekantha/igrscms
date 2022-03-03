package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.InstituteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Institute}.
 */
public interface InstituteService {

    /**
     * Save a institute.
     *
     * @param instituteDTO the entity to save.
     * @return the persisted entity.
     */
    InstituteDTO save(InstituteDTO instituteDTO);

    /**
     * Get all the institutes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstituteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" institute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstituteDTO> findOne(Long id);

    /**
     * Delete the "id" institute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
