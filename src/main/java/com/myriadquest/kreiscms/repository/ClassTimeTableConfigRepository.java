package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.ClassTimeTableConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClassTimeTableConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassTimeTableConfigRepository extends JpaRepository<ClassTimeTableConfig, Long> {
}
