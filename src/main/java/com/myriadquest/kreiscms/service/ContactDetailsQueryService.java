package com.myriadquest.kreiscms.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.myriadquest.kreiscms.domain.ContactDetails;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.ContactDetailsRepository;
import com.myriadquest.kreiscms.service.dto.ContactDetailsCriteria;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;
import com.myriadquest.kreiscms.service.mapper.ContactDetailsMapper;

/**
 * Service for executing complex queries for {@link ContactDetails} entities in the database.
 * The main input is a {@link ContactDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactDetailsDTO} or a {@link Page} of {@link ContactDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactDetailsQueryService extends QueryService<ContactDetails> {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsQueryService.class);

    private final ContactDetailsRepository contactDetailsRepository;

    private final ContactDetailsMapper contactDetailsMapper;

    public ContactDetailsQueryService(ContactDetailsRepository contactDetailsRepository, ContactDetailsMapper contactDetailsMapper) {
        this.contactDetailsRepository = contactDetailsRepository;
        this.contactDetailsMapper = contactDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link ContactDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactDetailsDTO> findByCriteria(ContactDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactDetails> specification = createSpecification(criteria);
        return contactDetailsMapper.toDto(contactDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactDetailsDTO> findByCriteria(ContactDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactDetails> specification = createSpecification(criteria);
        return contactDetailsRepository.findAll(specification, page)
            .map(contactDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactDetails> specification = createSpecification(criteria);
        return contactDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactDetails> createSpecification(ContactDetailsCriteria criteria) {
        Specification<ContactDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactDetails_.id));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), ContactDetails_.address));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ContactDetails_.email));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), ContactDetails_.contact));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), ContactDetails_.tenantId));
            }
        }
        return specification;
    }
}
