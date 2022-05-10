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

import com.myriadquest.kreiscms.domain.NoticeBoard;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.NoticeBoardRepository;
import com.myriadquest.kreiscms.service.dto.NoticeBoardCriteria;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;
import com.myriadquest.kreiscms.service.mapper.NoticeBoardMapper;

/**
 * Service for executing complex queries for {@link NoticeBoard} entities in the database.
 * The main input is a {@link NoticeBoardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoticeBoardDTO} or a {@link Page} of {@link NoticeBoardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoticeBoardQueryService extends QueryService<NoticeBoard> {

    private final Logger log = LoggerFactory.getLogger(NoticeBoardQueryService.class);

    private final NoticeBoardRepository noticeBoardRepository;

    private final NoticeBoardMapper noticeBoardMapper;

    public NoticeBoardQueryService(NoticeBoardRepository noticeBoardRepository, NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardMapper = noticeBoardMapper;
    }

    /**
     * Return a {@link List} of {@link NoticeBoardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoticeBoardDTO> findByCriteria(NoticeBoardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NoticeBoard> specification = createSpecification(criteria);
        return noticeBoardMapper.toDto(noticeBoardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoticeBoardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeBoardDTO> findByCriteria(NoticeBoardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NoticeBoard> specification = createSpecification(criteria);
        return noticeBoardRepository.findAll(specification, page)
            .map(noticeBoardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoticeBoardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NoticeBoard> specification = createSpecification(criteria);
        return noticeBoardRepository.count(specification);
    }

    /**
     * Function to convert {@link NoticeBoardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NoticeBoard> createSpecification(NoticeBoardCriteria criteria) {
        Specification<NoticeBoard> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NoticeBoard_.id));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), NoticeBoard_.active));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), NoticeBoard_.tenantId));
            }
        }
        return specification;
    }
}
