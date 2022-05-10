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

import com.myriadquest.kreiscms.domain.Institute;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.InstituteRepository;
import com.myriadquest.kreiscms.service.dto.InstituteCriteria;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;
import com.myriadquest.kreiscms.service.mapper.InstituteMapper;

/**
 * Service for executing complex queries for {@link Institute} entities in the database.
 * The main input is a {@link InstituteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InstituteDTO} or a {@link Page} of {@link InstituteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstituteQueryService extends QueryService<Institute> {

    private final Logger log = LoggerFactory.getLogger(InstituteQueryService.class);

    private final InstituteRepository instituteRepository;

    private final InstituteMapper instituteMapper;

    public InstituteQueryService(InstituteRepository instituteRepository, InstituteMapper instituteMapper) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
    }

    /**
     * Return a {@link List} of {@link InstituteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InstituteDTO> findByCriteria(InstituteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Institute> specification = createSpecification(criteria);
        return instituteMapper.toDto(instituteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InstituteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InstituteDTO> findByCriteria(InstituteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Institute> specification = createSpecification(criteria);
        return instituteRepository.findAll(specification, page)
            .map(instituteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstituteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Institute> specification = createSpecification(criteria);
        return instituteRepository.count(specification);
    }

    /**
     * Function to convert {@link InstituteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Institute> createSpecification(InstituteCriteria criteria) {
        Specification<Institute> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Institute_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Institute_.name));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), Institute_.shortName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Institute_.address));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Institute_.email));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Institute_.contact));
            }
            if (criteria.getLogoLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogoLink(), Institute_.logoLink));
            }
            if (criteria.getTagLine() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTagLine(), Institute_.tagLine));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Institute_.tenantId));
            }
        }
        return specification;
    }
}
