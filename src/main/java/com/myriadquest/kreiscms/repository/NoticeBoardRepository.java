package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.NoticeBoard;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NoticeBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Long> {
}
