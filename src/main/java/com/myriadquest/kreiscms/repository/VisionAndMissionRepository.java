package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.VisionAndMission;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VisionAndMission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisionAndMissionRepository extends JpaRepository<VisionAndMission, Long>, JpaSpecificationExecutor<VisionAndMission> {
}
