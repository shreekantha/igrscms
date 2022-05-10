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

import com.myriadquest.kreiscms.domain.StudentProfile;
import com.myriadquest.kreiscms.domain.*; // for static metamodels
import com.myriadquest.kreiscms.repository.StudentProfileRepository;
import com.myriadquest.kreiscms.service.dto.StudentProfileCriteria;
import com.myriadquest.kreiscms.service.dto.StudentProfileDTO;
import com.myriadquest.kreiscms.service.mapper.StudentProfileMapper;

/**
 * Service for executing complex queries for {@link StudentProfile} entities in the database.
 * The main input is a {@link StudentProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentProfileDTO} or a {@link Page} of {@link StudentProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentProfileQueryService extends QueryService<StudentProfile> {

    private final Logger log = LoggerFactory.getLogger(StudentProfileQueryService.class);

    private final StudentProfileRepository studentProfileRepository;

    private final StudentProfileMapper studentProfileMapper;

    public StudentProfileQueryService(StudentProfileRepository studentProfileRepository, StudentProfileMapper studentProfileMapper) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentProfileMapper = studentProfileMapper;
    }

    /**
     * Return a {@link List} of {@link StudentProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentProfileDTO> findByCriteria(StudentProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileMapper.toDto(studentProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudentProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentProfileDTO> findByCriteria(StudentProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileRepository.findAll(specification, page)
            .map(studentProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StudentProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StudentProfile> specification = createSpecification(criteria);
        return studentProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link StudentProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StudentProfile> createSpecification(StudentProfileCriteria criteria) {
        Specification<StudentProfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StudentProfile_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), StudentProfile_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), StudentProfile_.lastName));
            }
            if (criteria.getFathersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFathersName(), StudentProfile_.fathersName));
            }
            if (criteria.getMothersName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMothersName(), StudentProfile_.mothersName));
            }
            if (criteria.getCurrentTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCurrentTerm(), StudentProfile_.currentTerm));
            }
            if (criteria.getJoiningAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJoiningAcademicYear(), StudentProfile_.joiningAcademicYear));
            }
            if (criteria.getExitAcademicYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExitAcademicYear(), StudentProfile_.exitAcademicYear));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), StudentProfile_.mobile));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), StudentProfile_.email));
            }
            if (criteria.getAadhaar() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadhaar(), StudentProfile_.aadhaar));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), StudentProfile_.dob));
            }
            if (criteria.getImgLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImgLink(), StudentProfile_.imgLink));
            }
            if (criteria.getTenantId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenantId(), StudentProfile_.tenantId));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), StudentProfile_.active));
            }
        }
        return specification;
    }
}
