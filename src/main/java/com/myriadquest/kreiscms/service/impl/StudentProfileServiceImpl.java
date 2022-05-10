package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.StudentProfileService;
import com.myriadquest.kreiscms.domain.StudentProfile;
import com.myriadquest.kreiscms.repository.StudentProfileRepository;
import com.myriadquest.kreiscms.service.dto.StudentProfileDTO;
import com.myriadquest.kreiscms.service.mapper.StudentProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentProfile}.
 */
@Service
@Transactional
public class StudentProfileServiceImpl implements StudentProfileService {

    private final Logger log = LoggerFactory.getLogger(StudentProfileServiceImpl.class);

    private final StudentProfileRepository studentProfileRepository;

    private final StudentProfileMapper studentProfileMapper;

    public StudentProfileServiceImpl(StudentProfileRepository studentProfileRepository, StudentProfileMapper studentProfileMapper) {
        this.studentProfileRepository = studentProfileRepository;
        this.studentProfileMapper = studentProfileMapper;
    }

    @Override
    public StudentProfileDTO save(StudentProfileDTO studentProfileDTO) {
        log.debug("Request to save StudentProfile : {}", studentProfileDTO);
        StudentProfile studentProfile = studentProfileMapper.toEntity(studentProfileDTO);
        studentProfile = studentProfileRepository.save(studentProfile);
        return studentProfileMapper.toDto(studentProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentProfiles");
        return studentProfileRepository.findAll(pageable)
            .map(studentProfileMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StudentProfileDTO> findOne(Long id) {
        log.debug("Request to get StudentProfile : {}", id);
        return studentProfileRepository.findById(id)
            .map(studentProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentProfile : {}", id);
        studentProfileRepository.deleteById(id);
    }
}
