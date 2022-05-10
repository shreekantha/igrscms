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

import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.TermRepository;
import com.myriadquest.kreiscms.service.dto.TermCriteria;
import com.myriadquest.kreiscms.service.dto.TermDTO;
import com.myriadquest.kreiscms.service.mapper.TermMapper;

/**
 * Service for executing complex queries for {@link Term} entities in the database.
 * The main input is a {@link TermCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TermDTO} or a {@link Page} of {@link TermDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TermQueryService extends QueryService<Term> {

    private final Logger log = LoggerFactory.getLogger(TermQueryService.class);

    private final TermRepository termRepository;

    private final TermMapper termMapper;

    public TermQueryService(TermRepository termRepository, TermMapper termMapper) {
        this.termRepository = termRepository;
        this.termMapper = termMapper;
    }

    /**
     * Return a {@link List} of {@link TermDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TermDTO> findByCriteria(TermCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Term> specification = createSpecification(criteria);
        return termMapper.toDto(termRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TermDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TermDTO> findByCriteria(TermCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Term> specification = createSpecification(criteria);
        return termRepository.findAll(specification, page)
            .map(termMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TermCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Term> specification = createSpecification(criteria);
        return termRepository.count(specification);
    }

    /**
     * Function to convert {@link TermCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Term> createSpecification(TermCriteria criteria) {
        Specification<Term> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Term_.id));
            }
            if (criteria.getTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTerm(), Term_.term));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Term_.title));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Term_.description));
            }
            if (criteria.getImgUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgUrl(), Term_.imgUrl));
            }
            if (criteria.getNoOfStudents() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfStudents(), Term_.noOfStudents));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Term_.tenantId));
            }
            if (criteria.getClassTimeTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassTimeTableId(),
                    root -> root.join(Term_.classTimeTables, JoinType.LEFT).get(ClassTimeTable_.id)));
            }
            if (criteria.getExamTimeTableId() != null) {
                specification = specification.and(buildSpecification(criteria.getExamTimeTableId(),
                    root -> root.join(Term_.examTimeTables, JoinType.LEFT).get(ExamTimeTable_.id)));
            }
            if (criteria.getClassTeacherId() != null) {
                specification = specification.and(buildSpecification(criteria.getClassTeacherId(),
                    root -> root.join(Term_.classTeacher, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
