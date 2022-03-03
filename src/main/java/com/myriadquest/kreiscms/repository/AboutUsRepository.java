package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.AboutUs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AboutUs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Long> {
}
