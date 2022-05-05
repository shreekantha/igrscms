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

import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.ClassTimeTableRepository;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableCriteria;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableMapper;

/**
 * Service for executing complex queries for {@link ClassTimeTable} entities in the database.
 * The main input is a {@link ClassTimeTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassTimeTableDTO} or a {@link Page} of {@link ClassTimeTableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassTimeTableQueryService extends QueryService<ClassTimeTable> {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableQueryService.class);

    private final ClassTimeTableRepository classTimeTableRepository;

    private final ClassTimeTableMapper classTimeTableMapper;

    public ClassTimeTableQueryService(ClassTimeTableRepository classTimeTableRepository, ClassTimeTableMapper classTimeTableMapper) {
        this.classTimeTableRepository = classTimeTableRepository;
        this.classTimeTableMapper = classTimeTableMapper;
    }

    /**
     * Return a {@link List} of {@link ClassTimeTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassTimeTableDTO> findByCriteria(ClassTimeTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClassTimeTable> specification = createSpecification(criteria);
        return classTimeTableMapper.toDto(classTimeTableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassTimeTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassTimeTableDTO> findByCriteria(ClassTimeTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassTimeTable> specification = createSpecification(criteria);
        return classTimeTableRepository.findAll(specification, page)
            .map(classTimeTableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassTimeTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassTimeTable> specification = createSpecification(criteria);
        return classTimeTableRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassTimeTableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassTimeTable> createSpecification(ClassTimeTableCriteria criteria) {
        Specification<ClassTimeTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassTimeTable_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildSpecification(criteria.getDay(), ClassTimeTable_.day));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), ClassTimeTable_.tenantId));
            }
            if (criteria.getFacultyId() != null) {
                specification = specification.and(buildSpecification(criteria.getFacultyId(),
                    root -> root.join(ClassTimeTable_.faculty, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getAcademicCalendarId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcademicCalendarId(),
                    root -> root.join(ClassTimeTable_.academicCalendar, JoinType.LEFT).get(AcademicCalendar_.id)));
            }
            if (criteria.getDegreeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDegreeId(),
                    root -> root.join(ClassTimeTable_.degree, JoinType.LEFT).get(Degree_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(ClassTimeTable_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getTermId() != null) {
                specification = specification.and(buildSpecification(criteria.getTermId(),
                    root -> root.join(ClassTimeTable_.term, JoinType.LEFT).get(Term_.id)));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseId(),
                    root -> root.join(ClassTimeTable_.course, JoinType.LEFT).get(Course_.id)));
            }
            if (criteria.getPeriodId() != null) {
                specification = specification.and(buildSpecification(criteria.getPeriodId(),
                    root -> root.join(ClassTimeTable_.period, JoinType.LEFT).get(Period_.id)));
            }
        }
        return specification;
    }
}
