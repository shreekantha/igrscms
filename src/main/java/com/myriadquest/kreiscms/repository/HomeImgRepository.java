package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.HomeImg;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HomeImg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HomeImgRepository extends JpaRepository<HomeImg, Long> {
}
