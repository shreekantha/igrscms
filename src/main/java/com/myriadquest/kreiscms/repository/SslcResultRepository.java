package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.SslcResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SslcResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SslcResultRepository extends JpaRepository<SslcResult, Long>, JpaSpecificationExecutor<SslcResult> {
}
