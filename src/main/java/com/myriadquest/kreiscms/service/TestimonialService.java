package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.TestimonialDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.Testimonial}.
 */
public interface TestimonialService {

    /**
     * Save a testimonial.
     *
     * @param testimonialDTO the entity to save.
     * @return the persisted entity.
     */
    TestimonialDTO save(TestimonialDTO testimonialDTO);

    /**
     * Get all the testimonials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestimonialDTO> findAll(Pageable pageable);


    /**
     * Get the "id" testimonial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestimonialDTO> findOne(Long id);

    /**
     * Delete the "id" testimonial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
