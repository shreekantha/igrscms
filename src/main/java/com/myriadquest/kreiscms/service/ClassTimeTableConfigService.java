package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.ClassTimeTableConfig}.
 */
public interface ClassTimeTableConfigService {

    /**
     * Save a classTimeTableConfig.
     *
     * @param classTimeTableConfigDTO the entity to save.
     * @return the persisted entity.
     */
    ClassTimeTableConfigDTO save(ClassTimeTableConfigDTO classTimeTableConfigDTO);

    /**
     * Get all the classTimeTableConfigs.
     *
     * @return the list of entities.
     */
    List<ClassTimeTableConfigDTO> findAll();


    /**
     * Get the "id" classTimeTableConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClassTimeTableConfigDTO> findOne(Long id);

    /**
     * Delete the "id" classTimeTableConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
