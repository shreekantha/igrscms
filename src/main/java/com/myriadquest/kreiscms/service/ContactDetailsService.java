package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.ContactDetails}.
 */
public interface ContactDetailsService {

    /**
     * Save a contactDetails.
     *
     * @param contactDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ContactDetailsDTO save(ContactDetailsDTO contactDetailsDTO);

    /**
     * Get all the contactDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contactDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" contactDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
