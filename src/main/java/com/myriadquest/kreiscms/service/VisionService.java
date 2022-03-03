package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.VisionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Vision}.
 */
public interface VisionService {

    /**
     * Save a vision.
     *
     * @param visionDTO the entity to save.
     * @return the persisted entity.
     */
    VisionDTO save(VisionDTO visionDTO);

    /**
     * Get all the visions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vision.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisionDTO> findOne(Long id);

    /**
     * Delete the "id" vision.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
