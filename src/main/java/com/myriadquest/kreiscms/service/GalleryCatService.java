package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.GalleryCat}.
 */
public interface GalleryCatService {

    /**
     * Save a galleryCat.
     *
     * @param galleryCatDTO the entity to save.
     * @return the persisted entity.
     */
    GalleryCatDTO save(GalleryCatDTO galleryCatDTO);

    /**
     * Get all the galleryCats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GalleryCatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" galleryCat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GalleryCatDTO> findOne(Long id);

    /**
     * Delete the "id" galleryCat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
