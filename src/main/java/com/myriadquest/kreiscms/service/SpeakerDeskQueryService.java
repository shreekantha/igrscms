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

import com.myriadquest.kreiscms.domain.SpeakerDesk;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.SpeakerDeskRepository;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskCriteria;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;
import com.myriadquest.kreiscms.service.mapper.SpeakerDeskMapper;

/**
 * Service for executing complex queries for {@link SpeakerDesk} entities in the database.
 * The main input is a {@link SpeakerDeskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpeakerDeskDTO} or a {@link Page} of {@link SpeakerDeskDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpeakerDeskQueryService extends QueryService<SpeakerDesk> {

    private final Logger log = LoggerFactory.getLogger(SpeakerDeskQueryService.class);

    private final SpeakerDeskRepository speakerDeskRepository;

    private final SpeakerDeskMapper speakerDeskMapper;

    public SpeakerDeskQueryService(SpeakerDeskRepository speakerDeskRepository, SpeakerDeskMapper speakerDeskMapper) {
        this.speakerDeskRepository = speakerDeskRepository;
        this.speakerDeskMapper = speakerDeskMapper;
    }

    /**
     * Return a {@link List} of {@link SpeakerDeskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpeakerDeskDTO> findByCriteria(SpeakerDeskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SpeakerDesk> specification = createSpecification(criteria);
        return speakerDeskMapper.toDto(speakerDeskRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpeakerDeskDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpeakerDeskDTO> findByCriteria(SpeakerDeskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SpeakerDesk> specification = createSpecification(criteria);
        return speakerDeskRepository.findAll(specification, page)
            .map(speakerDeskMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpeakerDeskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SpeakerDesk> specification = createSpecification(criteria);
        return speakerDeskRepository.count(specification);
    }

    /**
     * Function to convert {@link SpeakerDeskCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SpeakerDesk> createSpecification(SpeakerDeskCriteria criteria) {
        Specification<SpeakerDesk> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SpeakerDesk_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SpeakerDesk_.name));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), SpeakerDesk_.note));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), SpeakerDesk_.imgLink));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), SpeakerDesk_.tenantId));
            }
        }
        return specification;
    }
}
