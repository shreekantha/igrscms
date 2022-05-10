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

import com.myriadquest.kreiscms.domain.GalleryCat;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.GalleryCatRepository;
import com.myriadquest.kreiscms.service.dto.GalleryCatCriteria;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryCatMapper;

/**
 * Service for executing complex queries for {@link GalleryCat} entities in the database.
 * The main input is a {@link GalleryCatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GalleryCatDTO} or a {@link Page} of {@link GalleryCatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GalleryCatQueryService extends QueryService<GalleryCat> {

    private final Logger log = LoggerFactory.getLogger(GalleryCatQueryService.class);

    private final GalleryCatRepository galleryCatRepository;

    private final GalleryCatMapper galleryCatMapper;

    public GalleryCatQueryService(GalleryCatRepository galleryCatRepository, GalleryCatMapper galleryCatMapper) {
        this.galleryCatRepository = galleryCatRepository;
        this.galleryCatMapper = galleryCatMapper;
    }

    /**
     * Return a {@link List} of {@link GalleryCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GalleryCatDTO> findByCriteria(GalleryCatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GalleryCat> specification = createSpecification(criteria);
        return galleryCatMapper.toDto(galleryCatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GalleryCatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GalleryCatDTO> findByCriteria(GalleryCatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GalleryCat> specification = createSpecification(criteria);
        return galleryCatRepository.findAll(specification, page)
            .map(galleryCatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GalleryCatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GalleryCat> specification = createSpecification(criteria);
        return galleryCatRepository.count(specification);
    }

    /**
     * Function to convert {@link GalleryCatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GalleryCat> createSpecification(GalleryCatCriteria criteria) {
        Specification<GalleryCat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GalleryCat_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GalleryCat_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), GalleryCat_.description));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), GalleryCat_.imgLink));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), GalleryCat_.tenantId));
            }
            if (criteria.getGalleryId() != null) {
                specification = specification.and(buildSpecification(criteria.getGalleryId(),
                    root -> root.join(GalleryCat_.galleries, JoinType.LEFT).get(Gallery_.id)));
            }
        }
        return specification;
    }
}
