package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;

import java.util.List;
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
     * @return the list of entities.
     */
    List<ClassTimeTableDTO> findAll();


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
