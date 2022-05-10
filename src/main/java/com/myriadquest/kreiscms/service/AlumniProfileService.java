package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.AlumniProfile}.
 */
public interface AlumniProfileService {

    /**
     * Save a alumniProfile.
     *
     * @param alumniProfileDTO the entity to save.
     * @return the persisted entity.
     */
    AlumniProfileDTO save(AlumniProfileDTO alumniProfileDTO);

    /**
     * Get all the alumniProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AlumniProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" alumniProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlumniProfileDTO> findOne(Long id);

    /**
     * Delete the "id" alumniProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
