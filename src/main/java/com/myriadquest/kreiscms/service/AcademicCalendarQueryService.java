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

import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.AcademicCalendarRepository;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarCriteria;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;
import com.myriadquest.kreiscms.service.mapper.AcademicCalendarMapper;

/**
 * Service for executing complex queries for {@link AcademicCalendar} entities in the database.
 * The main input is a {@link AcademicCalendarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AcademicCalendarDTO} or a {@link Page} of {@link AcademicCalendarDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AcademicCalendarQueryService extends QueryService<AcademicCalendar> {

    private final Logger log = LoggerFactory.getLogger(AcademicCalendarQueryService.class);

    private final AcademicCalendarRepository academicCalendarRepository;

    private final AcademicCalendarMapper academicCalendarMapper;

    public AcademicCalendarQueryService(AcademicCalendarRepository academicCalendarRepository, AcademicCalendarMapper academicCalendarMapper) {
        this.academicCalendarRepository = academicCalendarRepository;
        this.academicCalendarMapper = academicCalendarMapper;
    }

    /**
     * Return a {@link List} of {@link AcademicCalendarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AcademicCalendarDTO> findByCriteria(AcademicCalendarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AcademicCalendar> specification = createSpecification(criteria);
        return academicCalendarMapper.toDto(academicCalendarRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AcademicCalendarDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AcademicCalendarDTO> findByCriteria(AcademicCalendarCriteria criteria, Pageable page) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AcademicCalendar> specification = createSpecification(criteria);
        return academicCalendarRepository.findAll(specification, page)
            .map(academicCalendarMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AcademicCalendarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AcademicCalendar> specification = createSpecification(criteria);
        return academicCalendarRepository.count(specification);
    }

    /**
     * Function to convert {@link AcademicCalendarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AcademicCalendar> createSpecification(AcademicCalendarCriteria criteria) {
        Specification<AcademicCalendar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AcademicCalendar_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), AcademicCalendar_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), AcademicCalendar_.endDate));
            }
            if (criteria.getAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAcademicYear(), AcademicCalendar_.academicYear));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AcademicCalendar_.active));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), AcademicCalendar_.tenantId));
            }
            if (criteria.getClassTimeTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassTimeTableId(),
                    root -> root.join(AcademicCalendar_.classTimeTables, JoinType.LEFT).get(ClassTimeTable_.id)));
            }
            if (criteria.getExamTimeTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getExamTimeTableId(),
                    root -> root.join(AcademicCalendar_.examTimeTables, JoinType.LEFT).get(ExamTimeTable_.id)));
            }
            if (criteria.getDegreeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDegreeId(),
                    root -> root.join(AcademicCalendar_.degree, JoinType.LEFT).get(Degree_.id)));
            }
        }
        return specification;
    }
}
