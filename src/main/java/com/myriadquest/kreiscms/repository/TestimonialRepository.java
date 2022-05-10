package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.Testimonial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Testimonial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long>, JpaSpecificationExecutor<Testimonial> {
}
