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

import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.ExamTimeTableRepository;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableCriteria;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ExamTimeTableMapper;

/**
 * Service for executing complex queries for {@link ExamTimeTable} entities in the database.
 * The main input is a {@link ExamTimeTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamTimeTableDTO} or a {@link Page} of {@link ExamTimeTableDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamTimeTableQueryService extends QueryService<ExamTimeTable> {

    private final Logger log = LoggerFactory.getLogger(ExamTimeTableQueryService.class);

    private final ExamTimeTableRepository examTimeTableRepository;

    private final ExamTimeTableMapper examTimeTableMapper;

    public ExamTimeTableQueryService(ExamTimeTableRepository examTimeTableRepository, ExamTimeTableMapper examTimeTableMapper) {
        this.examTimeTableRepository = examTimeTableRepository;
        this.examTimeTableMapper = examTimeTableMapper;
    }

    /**
     * Return a {@link List} of {@link ExamTimeTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamTimeTableDTO> findByCriteria(ExamTimeTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExamTimeTable> specification = createSpecification(criteria);
        return examTimeTableMapper.toDto(examTimeTableRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExamTimeTableDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamTimeTableDTO> findByCriteria(ExamTimeTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExamTimeTable> specification = createSpecification(criteria);
        return examTimeTableRepository.findAll(specification, page)
            .map(examTimeTableMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamTimeTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExamTimeTable> specification = createSpecification(criteria);
        return examTimeTableRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamTimeTableCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExamTimeTable> createSpecification(ExamTimeTableCriteria criteria) {
        Specification<ExamTimeTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExamTimeTable_.id));
            }
            if (criteria.getExamType() != null) {
                specification = specification.and(buildSpecification(criteria.getExamType(), ExamTimeTable_.examType));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), ExamTimeTable_.date));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), ExamTimeTable_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), ExamTimeTable_.endTime));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), ExamTimeTable_.tenantId));
            }
            if (criteria.getAcademicCalendarId() != null) {
                specification = specification.and(buildSpecification(criteria.getAcademicCalendarId(),
                    root -> root.join(ExamTimeTable_.academicCalendar, JoinType.LEFT).get(AcademicCalendar_.id)));
            }
            if (criteria.getDegreeId() != null) {
                specification = specification.and(buildSpecification(criteria.getDegreeId(),
                    root -> root.join(ExamTimeTable_.degree, JoinType.LEFT).get(Degree_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(ExamTimeTable_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getTermId() != null) {
                specification = specification.and(buildSpecification(criteria.getTermId(),
                    root -> root.join(ExamTimeTable_.term, JoinType.LEFT).get(Term_.id)));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildSpecification(criteria.getCourseId(),
                    root -> root.join(ExamTimeTable_.course, JoinType.LEFT).get(Course_.id)));
            }
        }
        return specification;
    }
}
