package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.AlumniProfile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AlumniProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlumniProfileRepository extends JpaRepository<AlumniProfile, Long>, JpaSpecificationExecutor<AlumniProfile> {
}
