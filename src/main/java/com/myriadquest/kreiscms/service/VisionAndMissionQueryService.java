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

import com.myriadquest.kreiscms.domain.VisionAndMission;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.VisionAndMissionRepository;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionCriteria;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionAndMissionMapper;

/**
 * Service for executing complex queries for {@link VisionAndMission} entities in the database.
 * The main input is a {@link VisionAndMissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VisionAndMissionDTO} or a {@link Page} of {@link VisionAndMissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VisionAndMissionQueryService extends QueryService<VisionAndMission> {

    private final Logger log = LoggerFactory.getLogger(VisionAndMissionQueryService.class);

    private final VisionAndMissionRepository visionAndMissionRepository;

    private final VisionAndMissionMapper visionAndMissionMapper;

    public VisionAndMissionQueryService(VisionAndMissionRepository visionAndMissionRepository, VisionAndMissionMapper visionAndMissionMapper) {
        this.visionAndMissionRepository = visionAndMissionRepository;
        this.visionAndMissionMapper = visionAndMissionMapper;
    }

    /**
     * Return a {@link List} of {@link VisionAndMissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VisionAndMissionDTO> findByCriteria(VisionAndMissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VisionAndMission> specification = createSpecification(criteria);
        return visionAndMissionMapper.toDto(visionAndMissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VisionAndMissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VisionAndMissionDTO> findByCriteria(VisionAndMissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VisionAndMission> specification = createSpecification(criteria);
        return visionAndMissionRepository.findAll(specification, page)
            .map(visionAndMissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VisionAndMissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VisionAndMission> specification = createSpecification(criteria);
        return visionAndMissionRepository.count(specification);
    }

    /**
     * Function to convert {@link VisionAndMissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VisionAndMission> createSpecification(VisionAndMissionCriteria criteria) {
        Specification<VisionAndMission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VisionAndMission_.id));
            }
            if (criteria.getVision() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVision(), VisionAndMission_.vision));
            }
            if (criteria.getMission() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMission(), VisionAndMission_.mission));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), VisionAndMission_.tenantId));
            }
        }
        return specification;
    }
}
