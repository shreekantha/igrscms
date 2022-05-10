package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.HomeImgDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.HomeImg}.
 */
public interface HomeImgService {

    /**
     * Save a homeImg.
     *
     * @param homeImgDTO the entity to save.
     * @return the persisted entity.
     */
    HomeImgDTO save(HomeImgDTO homeImgDTO);

    /**
     * Get all the homeImgs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HomeImgDTO> findAll(Pageable pageable);


    /**
     * Get the "id" homeImg.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HomeImgDTO> findOne(Long id);

    /**
     * Delete the "id" homeImg.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
