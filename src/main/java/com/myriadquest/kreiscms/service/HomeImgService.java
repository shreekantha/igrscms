package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.HomeImgDTO;

import java.util.List;
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
     * @return the list of entities.
     */
    List<HomeImgDTO> findAll();


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
