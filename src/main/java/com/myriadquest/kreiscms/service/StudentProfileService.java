package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.StudentProfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.StudentProfile}.
 */
public interface StudentProfileService {

    /**
     * Save a studentProfile.
     *
     * @param studentProfileDTO the entity to save.
     * @return the persisted entity.
     */
    StudentProfileDTO save(StudentProfileDTO studentProfileDTO);

    /**
     * Get all the studentProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentProfileDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentProfileDTO> findOne(Long id);

    /**
     * Delete the "id" studentProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
