package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.Vision;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisionRepository extends JpaRepository<Vision, Long>, JpaSpecificationExecutor<Vision> {
}
