package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.PucResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PucResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PucResultRepository extends JpaRepository<PucResult, Long>, JpaSpecificationExecutor<PucResult> {
}
