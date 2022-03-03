package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.SpeakerDesk;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SpeakerDesk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpeakerDeskRepository extends JpaRepository<SpeakerDesk, Long> {
}
