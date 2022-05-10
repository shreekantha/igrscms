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

import com.myriadquest.kreiscms.domain.Mission;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.MissionRepository;
import com.myriadquest.kreiscms.service.dto.MissionCriteria;
import com.myriadquest.kreiscms.service.dto.MissionDTO;
import com.myriadquest.kreiscms.service.mapper.MissionMapper;

/**
 * Service for executing complex queries for {@link Mission} entities in the database.
 * The main input is a {@link MissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MissionDTO} or a {@link Page} of {@link MissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MissionQueryService extends QueryService<Mission> {

    private final Logger log = LoggerFactory.getLogger(MissionQueryService.class);

    private final MissionRepository missionRepository;

    private final MissionMapper missionMapper;

    public MissionQueryService(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    /**
     * Return a {@link List} of {@link MissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MissionDTO> findByCriteria(MissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Mission> specification = createSpecification(criteria);
        return missionMapper.toDto(missionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MissionDTO> findByCriteria(MissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Mission> specification = createSpecification(criteria);
        return missionRepository.findAll(specification, page)
            .map(missionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Mission> specification = createSpecification(criteria);
        return missionRepository.count(specification);
    }

    /**
     * Function to convert {@link MissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Mission> createSpecification(MissionCriteria criteria) {
        Specification<Mission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Mission_.id));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), Mission_.details));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Mission_.tenantId));
            }
        }
        return specification;
    }
}
