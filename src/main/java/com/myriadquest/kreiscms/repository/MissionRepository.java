package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.Mission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Mission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
}
