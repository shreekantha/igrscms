package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.ContactDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContactDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long>, JpaSpecificationExecutor<ContactDetails> {
}
