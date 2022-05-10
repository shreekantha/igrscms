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

import com.myriadquest.kreiscms.domain.Vision;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.VisionRepository;
import com.myriadquest.kreiscms.service.dto.VisionCriteria;
import com.myriadquest.kreiscms.service.dto.VisionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionMapper;

/**
 * Service for executing complex queries for {@link Vision} entities in the database.
 * The main input is a {@link VisionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VisionDTO} or a {@link Page} of {@link VisionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VisionQueryService extends QueryService<Vision> {

    private final Logger log = LoggerFactory.getLogger(VisionQueryService.class);

    private final VisionRepository visionRepository;

    private final VisionMapper visionMapper;

    public VisionQueryService(VisionRepository visionRepository, VisionMapper visionMapper) {
        this.visionRepository = visionRepository;
        this.visionMapper = visionMapper;
    }

    /**
     * Return a {@link List} of {@link VisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VisionDTO> findByCriteria(VisionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vision> specification = createSpecification(criteria);
        return visionMapper.toDto(visionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VisionDTO> findByCriteria(VisionCriteria criteria, Pageable page) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vision> specification = createSpecification(criteria);
        return visionRepository.findAll(specification, page)
            .map(visionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VisionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vision> specification = createSpecification(criteria);
        return visionRepository.count(specification);
    }

    /**
     * Function to convert {@link VisionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vision> createSpecification(VisionCriteria criteria) {
        Specification<Vision> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vision_.id));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), Vision_.detail));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Vision_.tenantId));
            }
        }
        return specification;
    }
}
