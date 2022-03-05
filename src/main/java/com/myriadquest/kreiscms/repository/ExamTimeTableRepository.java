package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.ExamTimeTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExamTimeTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamTimeTableRepository extends JpaRepository<ExamTimeTable, Long> {
}
