package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.GalleryCat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GalleryCat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GalleryCatRepository extends JpaRepository<GalleryCat, Long> {
}
