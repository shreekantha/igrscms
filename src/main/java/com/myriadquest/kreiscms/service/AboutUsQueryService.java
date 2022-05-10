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

import com.myriadquest.kreiscms.domain.AboutUs;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.AboutUsRepository;
import com.myriadquest.kreiscms.service.dto.AboutUsCriteria;
import com.myriadquest.kreiscms.service.dto.AboutUsDTO;
import com.myriadquest.kreiscms.service.mapper.AboutUsMapper;

/**
 * Service for executing complex queries for {@link AboutUs} entities in the database.
 * The main input is a {@link AboutUsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AboutUsDTO} or a {@link Page} of {@link AboutUsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AboutUsQueryService extends QueryService<AboutUs> {

    private final Logger log = LoggerFactory.getLogger(AboutUsQueryService.class);

    private final AboutUsRepository aboutUsRepository;

    private final AboutUsMapper aboutUsMapper;

    public AboutUsQueryService(AboutUsRepository aboutUsRepository, AboutUsMapper aboutUsMapper) {
        this.aboutUsRepository = aboutUsRepository;
        this.aboutUsMapper = aboutUsMapper;
    }

    /**
     * Return a {@link List} of {@link AboutUsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AboutUsDTO> findByCriteria(AboutUsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AboutUs> specification = createSpecification(criteria);
        return aboutUsMapper.toDto(aboutUsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AboutUsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AboutUsDTO> findByCriteria(AboutUsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AboutUs> specification = createSpecification(criteria);
        return aboutUsRepository.findAll(specification, page)
            .map(aboutUsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AboutUsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AboutUs> specification = createSpecification(criteria);
        return aboutUsRepository.count(specification);
    }

    /**
     * Function to convert {@link AboutUsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AboutUs> createSpecification(AboutUsCriteria criteria) {
        Specification<AboutUs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AboutUs_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), AboutUs_.title));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), AboutUs_.imgLink));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), AboutUs_.tenantId));
            }
        }
        return specification;
    }
}
