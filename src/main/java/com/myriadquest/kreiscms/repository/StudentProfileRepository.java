package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.StudentProfile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StudentProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long>, JpaSpecificationExecutor<StudentProfile> {
}
