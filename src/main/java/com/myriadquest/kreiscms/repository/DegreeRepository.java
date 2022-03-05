package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.Degree;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Degree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
}
