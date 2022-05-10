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

import com.myriadquest.kreiscms.domain.Gallery;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.GalleryRepository;
import com.myriadquest.kreiscms.service.dto.GalleryCriteria;
import com.myriadquest.kreiscms.service.dto.GalleryDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryMapper;

/**
 * Service for executing complex queries for {@link Gallery} entities in the database.
 * The main input is a {@link GalleryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GalleryDTO} or a {@link Page} of {@link GalleryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GalleryQueryService extends QueryService<Gallery> {

    private final Logger log = LoggerFactory.getLogger(GalleryQueryService.class);

    private final GalleryRepository galleryRepository;

    private final GalleryMapper galleryMapper;

    public GalleryQueryService(GalleryRepository galleryRepository, GalleryMapper galleryMapper) {
        this.galleryRepository = galleryRepository;
        this.galleryMapper = galleryMapper;
    }

    /**
     * Return a {@link List} of {@link GalleryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GalleryDTO> findByCriteria(GalleryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Gallery> specification = createSpecification(criteria);
        return galleryMapper.toDto(galleryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GalleryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GalleryDTO> findByCriteria(GalleryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Gallery> specification = createSpecification(criteria);
        return galleryRepository.findAll(specification, page)
            .map(galleryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GalleryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Gallery> specification = createSpecification(criteria);
        return galleryRepository.count(specification);
    }

    /**
     * Function to convert {@link GalleryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Gallery> createSpecification(GalleryCriteria criteria) {
        Specification<Gallery> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Gallery_.id));
            }
            if (criteria.getImgUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgUrl(), Gallery_.imgUrl));
            }
            if (criteria.getDescritpion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescritpion(), Gallery_.descritpion));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), Gallery_.tenantId));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Gallery_.category, JoinType.LEFT).get(GalleryCat_.id)));
            }
        }
        return specification;
    }
}
