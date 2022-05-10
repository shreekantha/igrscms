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

import com.myriadquest.kreiscms.domain.HomeImg;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.HomeImgRepository;
import com.myriadquest.kreiscms.service.dto.HomeImgCriteria;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;
import com.myriadquest.kreiscms.service.mapper.HomeImgMapper;

/**
 * Service for executing complex queries for {@link HomeImg} entities in the database.
 * The main input is a {@link HomeImgCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HomeImgDTO} or a {@link Page} of {@link HomeImgDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HomeImgQueryService extends QueryService<HomeImg> {

    private final Logger log = LoggerFactory.getLogger(HomeImgQueryService.class);

    private final HomeImgRepository homeImgRepository;

    private final HomeImgMapper homeImgMapper;

    public HomeImgQueryService(HomeImgRepository homeImgRepository, HomeImgMapper homeImgMapper) {
        this.homeImgRepository = homeImgRepository;
        this.homeImgMapper = homeImgMapper;
    }

    /**
     * Return a {@link List} of {@link HomeImgDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HomeImgDTO> findByCriteria(HomeImgCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HomeImg> specification = createSpecification(criteria);
        return homeImgMapper.toDto(homeImgRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HomeImgDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HomeImgDTO> findByCriteria(HomeImgCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HomeImg> specification = createSpecification(criteria);
        return homeImgRepository.findAll(specification, page)
            .map(homeImgMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HomeImgCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HomeImg> specification = createSpecification(criteria);
        return homeImgRepository.count(specification);
    }

    /**
     * Function to convert {@link HomeImgCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HomeImg> createSpecification(HomeImgCriteria criteria) {
        Specification<HomeImg> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HomeImg_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), HomeImg_.title));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), HomeImg_.tenantId));
            }
        }
        return specification;
    }
}
