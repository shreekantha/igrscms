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

import com.myriadquest.kreiscms.domain.Testimonial;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.TestimonialRepository;
import com.myriadquest.kreiscms.service.dto.TestimonialCriteria;
import com.myriadquest.kreiscms.service.dto.TestimonialDTO;
import com.myriadquest.kreiscms.service.mapper.TestimonialMapper;

/**
 * Service for executing complex queries for {@link Testimonial} entities in the database.
 * The main input is a {@link TestimonialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TestimonialDTO} or a {@link Page} of {@link TestimonialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TestimonialQueryService extends QueryService<Testimonial> {

    private final Logger log = LoggerFactory.getLogger(TestimonialQueryService.class);

    private final TestimonialRepository testimonialRepository;

    private final TestimonialMapper testimonialMapper;

    public TestimonialQueryService(TestimonialRepository testimonialRepository, TestimonialMapper testimonialMapper) {
        this.testimonialRepository = testimonialRepository;
        this.testimonialMapper = testimonialMapper;
    }

    /**
     * Return a {@link List} of {@link TestimonialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TestimonialDTO> findByCriteria(TestimonialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Testimonial> specification = createSpecification(criteria);
        return testimonialMapper.toDto(testimonialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TestimonialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TestimonialDTO> findByCriteria(TestimonialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Testimonial> specification = createSpecification(criteria);
        return testimonialRepository.findAll(specification, page)
            .map(testimonialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TestimonialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Testimonial> specification = createSpecification(criteria);
        return testimonialRepository.count(specification);
    }

    /**
     * Function to convert {@link TestimonialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Testimonial> createSpecification(TestimonialCriteria criteria) {
        Specification<Testimonial> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Testimonial_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Testimonial_.name));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), Testimonial_.imgLink));
            }
            if (criteria.getBatchYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBatchYear(), Testimonial_.batchYear));
            }
        }
        return specification;
    }
}
