package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.MissionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Mission}.
 */
public interface MissionService {

    /**
     * Save a mission.
     *
     * @param missionDTO the entity to save.
     * @return the persisted entity.
     */
    MissionDTO save(MissionDTO missionDTO);

    /**
     * Get all the missions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MissionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MissionDTO> findOne(Long id);

    /**
     * Delete the "id" mission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
