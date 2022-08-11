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

import com.myriadquest.kreiscms.domain.SslcResult;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.SslcResultRepository;
import com.myriadquest.kreiscms.service.dto.SslcResultCriteria;
import com.myriadquest.kreiscms.service.dto.SslcResultDTO;
import com.myriadquest.kreiscms.service.mapper.SslcResultMapper;

/**
 * Service for executing complex queries for {@link SslcResult} entities in the database.
 * The main input is a {@link SslcResultCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SslcResultDTO} or a {@link Page} of {@link SslcResultDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SslcResultQueryService extends QueryService<SslcResult> {

    private final Logger log = LoggerFactory.getLogger(SslcResultQueryService.class);

    private final SslcResultRepository sslcResultRepository;

    private final SslcResultMapper sslcResultMapper;

    public SslcResultQueryService(SslcResultRepository sslcResultRepository, SslcResultMapper sslcResultMapper) {
        this.sslcResultRepository = sslcResultRepository;
        this.sslcResultMapper = sslcResultMapper;
    }

    /**
     * Return a {@link List} of {@link SslcResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SslcResultDTO> findByCriteria(SslcResultCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SslcResult> specification = createSpecification(criteria);
        return sslcResultMapper.toDto(sslcResultRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SslcResultDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SslcResultDTO> findByCriteria(SslcResultCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SslcResult> specification = createSpecification(criteria);
        return sslcResultRepository.findAll(specification, page)
            .map(sslcResultMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SslcResultCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SslcResult> specification = createSpecification(criteria);
        return sslcResultRepository.count(specification);
    }

    /**
     * Function to convert {@link SslcResultCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SslcResult> createSpecification(SslcResultCriteria criteria) {
        Specification<SslcResult> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SslcResult_.id));
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), SslcResult_.academicYear));
            }
            if (criteria.getQualityResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQualityResult(), SslcResult_.qualityResult));
            }
            if (criteria.getTopResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopResult(), SslcResult_.topResult));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), SslcResult_.tenantId));
            }
        }
        return specification;
    }
}
