package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.ClassTimeTable}.
 */
public interface ClassTimeTableService {

    /**
     * Save a classTimeTable.
     *
     * @param classTimeTableDTO the entity to save.
     * @return the persisted entity.
     */
    ClassTimeTableDTO save(ClassTimeTableDTO classTimeTableDTO);

    /**
     * Get all the classTimeTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClassTimeTableDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classTimeTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassTimeTableDTO> findOne(Long id);

    /**
     * Delete the "id" classTimeTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
