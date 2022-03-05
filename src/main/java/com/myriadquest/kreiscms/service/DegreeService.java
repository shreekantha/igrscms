package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.DegreeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Degree}.
 */
public interface DegreeService {

    /**
     * Save a degree.
     *
     * @param degreeDTO the entity to save.
     * @return the persisted entity.
     */
    DegreeDTO save(DegreeDTO degreeDTO);

    /**
     * Get all the degrees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DegreeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" degree.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DegreeDTO> findOne(Long id);

    /**
     * Delete the "id" degree.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
