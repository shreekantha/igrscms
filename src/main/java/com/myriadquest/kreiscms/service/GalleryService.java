package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.GalleryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Gallery}.
 */
public interface GalleryService {

    /**
     * Save a gallery.
     *
     * @param galleryDTO the entity to save.
     * @return the persisted entity.
     */
    GalleryDTO save(GalleryDTO galleryDTO);

    /**
     * Get all the galleries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GalleryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" gallery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GalleryDTO> findOne(Long id);

    /**
     * Delete the "id" gallery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
