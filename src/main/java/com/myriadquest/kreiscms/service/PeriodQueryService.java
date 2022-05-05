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

import com.myriadquest.kreiscms.domain.Period;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.PeriodRepository;
import com.myriadquest.kreiscms.service.dto.PeriodCriteria;
import com.myriadquest.kreiscms.service.dto.PeriodDTO;
import com.myriadquest.kreiscms.service.mapper.PeriodMapper;

/**
 * Service for executing complex queries for {@link Period} entities in the database.
 * The main input is a {@link PeriodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PeriodDTO} or a {@link Page} of {@link PeriodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PeriodQueryService extends QueryService<Period> {

    private final Logger log = LoggerFactory.getLogger(PeriodQueryService.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    public PeriodQueryService(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    /**
     * Return a {@link List} of {@link PeriodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PeriodDTO> findByCriteria(PeriodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Period> specification = createSpecification(criteria);
        return periodMapper.toDto(periodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PeriodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PeriodDTO> findByCriteria(PeriodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Period> specification = createSpecification(criteria);
        return periodRepository.findAll(specification, page)
            .map(periodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PeriodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Period> specification = createSpecification(criteria);
        return periodRepository.count(specification);
    }

    /**
     * Function to convert {@link PeriodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Period> createSpecification(PeriodCriteria criteria) {
        Specification<Period> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Period_.id));
            }
            if (criteria.getNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumber(), Period_.number));
            }
            if (criteria.getLabel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabel(), Period_.label));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), Period_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), Period_.endTime));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Period_.tenantId));
            }
            if (criteria.getClassTimeTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassTimeTableId(),
                    root -> root.join(Period_.classTimeTables, JoinType.LEFT).get(ClassTimeTable_.id)));
            }
        }
        return specification;
    }
}
