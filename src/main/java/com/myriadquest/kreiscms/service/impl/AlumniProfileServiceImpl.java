package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.AlumniProfileService;
import com.myriadquest.kreiscms.domain.AlumniProfile;
import com.myriadquest.kreiscms.repository.AlumniProfileRepository;
import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;
import com.myriadquest.kreiscms.service.mapper.AlumniProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AlumniProfile}.
 */
@Service
@Transactional
public class AlumniProfileServiceImpl implements AlumniProfileService {

    private final Logger log = LoggerFactory.getLogger(AlumniProfileServiceImpl.class);

    private final AlumniProfileRepository alumniProfileRepository;

    private final AlumniProfileMapper alumniProfileMapper;

    public AlumniProfileServiceImpl(AlumniProfileRepository alumniProfileRepository, AlumniProfileMapper alumniProfileMapper) {
        this.alumniProfileRepository = alumniProfileRepository;
        this.alumniProfileMapper = alumniProfileMapper;
    }

    @Override
    public AlumniProfileDTO save(AlumniProfileDTO alumniProfileDTO) {
        log.debug("Request to save AlumniProfile : {}", alumniProfileDTO);
        AlumniProfile alumniProfile = alumniProfileMapper.toEntity(alumniProfileDTO);
        alumniProfile = alumniProfileRepository.save(alumniProfile);
        return alumniProfileMapper.toDto(alumniProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlumniProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AlumniProfiles");
        return alumniProfileRepository.findAll(pageable)
            .map(alumniProfileMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AlumniProfileDTO> findOne(Long id) {
        log.debug("Request to get AlumniProfile : {}", id);
        return alumniProfileRepository.findById(id)
            .map(alumniProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AlumniProfile : {}", id);
        alumniProfileRepository.deleteById(id);
    }
}
