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

import com.myriadquest.kreiscms.domain.PucResult;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.PucResultRepository;
import com.myriadquest.kreiscms.service.dto.PucResultCriteria;
import com.myriadquest.kreiscms.service.dto.PucResultDTO;
import com.myriadquest.kreiscms.service.mapper.PucResultMapper;

/**
 * Service for executing complex queries for {@link PucResult} entities in the database.
 * The main input is a {@link PucResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PucResultDTO} or a {@link Page} of {@link PucResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PucResultQueryService extends QueryService<PucResult> {

    private final Logger log = LoggerFactory.getLogger(PucResultQueryService.class);

    private final PucResultRepository pucResultRepository;

    private final PucResultMapper pucResultMapper;

    public PucResultQueryService(PucResultRepository pucResultRepository, PucResultMapper pucResultMapper) {
        this.pucResultRepository = pucResultRepository;
        this.pucResultMapper = pucResultMapper;
    }

    /**
     * Return a {@link List} of {@link PucResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PucResultDTO> findByCriteria(PucResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PucResult> specification = createSpecification(criteria);
        return pucResultMapper.toDto(pucResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PucResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PucResultDTO> findByCriteria(PucResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PucResult> specification = createSpecification(criteria);
        return pucResultRepository.findAll(specification, page)
            .map(pucResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PucResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PucResult> specification = createSpecification(criteria);
        return pucResultRepository.count(specification);
    }

    /**
     * Function to convert {@link PucResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PucResult> createSpecification(PucResultCriteria criteria) {
        Specification<PucResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PucResult_.id));
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), PucResult_.academicYear));
            }
            if (criteria.getQualityResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQualityResult(), PucResult_.qualityResult));
            }
            if (criteria.getTopResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopResult(), PucResult_.topResult));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), PucResult_.tenantId));
            }
        }
        return specification;
    }
}
