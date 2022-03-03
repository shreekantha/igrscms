package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.SpeakerDesk}.
 */
public interface SpeakerDeskService {

    /**
     * Save a speakerDesk.
     *
     * @param speakerDeskDTO the entity to save.
     * @return the persisted entity.
     */
    SpeakerDeskDTO save(SpeakerDeskDTO speakerDeskDTO);

    /**
     * Get all the speakerDesks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpeakerDeskDTO> findAll(Pageable pageable);


    /**
     * Get the "id" speakerDesk.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpeakerDeskDTO> findOne(Long id);

    /**
     * Delete the "id" speakerDesk.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
