package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.AboutUsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.AboutUs}.
 */
public interface AboutUsService {

    /**
     * Save a aboutUs.
     *
     * @param aboutUsDTO the entity to save.
     * @return the persisted entity.
     */
    AboutUsDTO save(AboutUsDTO aboutUsDTO);

    /**
     * Get all the aboutuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AboutUsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" aboutUs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AboutUsDTO> findOne(Long id);

    /**
     * Delete the "id" aboutUs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
