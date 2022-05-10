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

import com.myriadquest.kreiscms.domain.ClassTimeTableConfig;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.ClassTimeTableConfigRepository;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigCriteria;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableConfigMapper;

/**
 * Service for executing complex queries for {@link ClassTimeTableConfig} entities in the database.
 * The main input is a {@link ClassTimeTableConfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClassTimeTableConfigDTO} or a {@link Page} of {@link ClassTimeTableConfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClassTimeTableConfigQueryService extends QueryService<ClassTimeTableConfig> {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableConfigQueryService.class);

    private final ClassTimeTableConfigRepository classTimeTableConfigRepository;

    private final ClassTimeTableConfigMapper classTimeTableConfigMapper;

    public ClassTimeTableConfigQueryService(ClassTimeTableConfigRepository classTimeTableConfigRepository, ClassTimeTableConfigMapper classTimeTableConfigMapper) {
        this.classTimeTableConfigRepository = classTimeTableConfigRepository;
        this.classTimeTableConfigMapper = classTimeTableConfigMapper;
    }

    /**
     * Return a {@link List} of {@link ClassTimeTableConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClassTimeTableConfigDTO> findByCriteria(ClassTimeTableConfigCriteria criteria) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("find by criteria : {}", criteria);
        final Specification<ClassTimeTableConfig> specification = createSpecification(criteria);
        return classTimeTableConfigMapper.toDto(classTimeTableConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClassTimeTableConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClassTimeTableConfigDTO> findByCriteria(ClassTimeTableConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClassTimeTableConfig> specification = createSpecification(criteria);
        return classTimeTableConfigRepository.findAll(specification, page)
            .map(classTimeTableConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClassTimeTableConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClassTimeTableConfig> specification = createSpecification(criteria);
        return classTimeTableConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link ClassTimeTableConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClassTimeTableConfig> createSpecification(ClassTimeTableConfigCriteria criteria) {
        Specification<ClassTimeTableConfig> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClassTimeTableConfig_.id));
            }
            if (criteria.getTimeTableGenType() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeTableGenType(), ClassTimeTableConfig_.timeTableGenType));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), ClassTimeTableConfig_.tenantId));
            }
        }
        return specification;
    }
}
