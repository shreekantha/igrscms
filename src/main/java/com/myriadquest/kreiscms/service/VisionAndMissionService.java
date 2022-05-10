package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.VisionAndMission}.
 */
public interface VisionAndMissionService {

    /**
     * Save a visionAndMission.
     *
     * @param visionAndMissionDTO the entity to save.
     * @return the persisted entity.
     */
    VisionAndMissionDTO save(VisionAndMissionDTO visionAndMissionDTO);

    /**
     * Get all the visionAndMissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisionAndMissionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" visionAndMission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisionAndMissionDTO> findOne(Long id);

    /**
     * Delete the "id" visionAndMission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
