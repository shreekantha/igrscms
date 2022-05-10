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

import com.myriadquest.kreiscms.domain.AlumniProfile;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.AlumniProfileRepository;
import com.myriadquest.kreiscms.service.dto.AlumniProfileCriteria;
import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;
import com.myriadquest.kreiscms.service.mapper.AlumniProfileMapper;

/**
 * Service for executing complex queries for {@link AlumniProfile} entities in the database.
 * The main input is a {@link AlumniProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AlumniProfileDTO} or a {@link Page} of {@link AlumniProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AlumniProfileQueryService extends QueryService<AlumniProfile> {

    private final Logger log = LoggerFactory.getLogger(AlumniProfileQueryService.class);

    private final AlumniProfileRepository alumniProfileRepository;

    private final AlumniProfileMapper alumniProfileMapper;

    public AlumniProfileQueryService(AlumniProfileRepository alumniProfileRepository, AlumniProfileMapper alumniProfileMapper) {
        this.alumniProfileRepository = alumniProfileRepository;
        this.alumniProfileMapper = alumniProfileMapper;
    }

    /**
     * Return a {@link List} of {@link AlumniProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AlumniProfileDTO> findByCriteria(AlumniProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AlumniProfile> specification = createSpecification(criteria);
        return alumniProfileMapper.toDto(alumniProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AlumniProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AlumniProfileDTO> findByCriteria(AlumniProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AlumniProfile> specification = createSpecification(criteria);
        return alumniProfileRepository.findAll(specification, page)
            .map(alumniProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AlumniProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AlumniProfile> specification = createSpecification(criteria);
        return alumniProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link AlumniProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AlumniProfile> createSpecification(AlumniProfileCriteria criteria) {
        Specification<AlumniProfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AlumniProfile_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), AlumniProfile_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), AlumniProfile_.lastName));
            }
            if (criteria.getFathersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFathersName(), AlumniProfile_.fathersName));
            }
            if (criteria.getMothersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMothersName(), AlumniProfile_.mothersName));
            }
            if (criteria.getCurrentTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentTerm(), AlumniProfile_.currentTerm));
            }
            if (criteria.getJoiningAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJoiningAcademicYear(), AlumniProfile_.joiningAcademicYear));
            }
            if (criteria.getExitAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExitAcademicYear(), AlumniProfile_.exitAcademicYear));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), AlumniProfile_.mobile));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), AlumniProfile_.email));
            }
            if (criteria.getAadhaar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadhaar(), AlumniProfile_.aadhaar));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), AlumniProfile_.dob));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), AlumniProfile_.imgLink));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), AlumniProfile_.tenantId));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), AlumniProfile_.active));
            }
        }
        return specification;
    }
}
