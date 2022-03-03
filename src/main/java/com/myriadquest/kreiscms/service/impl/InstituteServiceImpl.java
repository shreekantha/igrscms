package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.InstituteService;
import com.myriadquest.kreiscms.domain.Institute;
import com.myriadquest.kreiscms.repository.InstituteRepository;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;
import com.myriadquest.kreiscms.service.mapper.InstituteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Institute}.
 */
@Service
@Transactional
public class InstituteServiceImpl implements InstituteService {

    private final Logger log = LoggerFactory.getLogger(InstituteServiceImpl.class);

    private final InstituteRepository instituteRepository;

    private final InstituteMapper instituteMapper;

    public InstituteServiceImpl(InstituteRepository instituteRepository, InstituteMapper instituteMapper) {
        this.instituteRepository = instituteRepository;
        this.instituteMapper = instituteMapper;
    }

    @Override
    public InstituteDTO save(InstituteDTO instituteDTO) {
        log.debug("Request to save Institute : {}", instituteDTO);
        Institute institute = instituteMapper.toEntity(instituteDTO);
        institute = instituteRepository.save(institute);
        return instituteMapper.toDto(institute);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InstituteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Institutes");
        return instituteRepository.findAll(pageable)
            .map(instituteMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InstituteDTO> findOne(Long id) {
        log.debug("Request to get Institute : {}", id);
        return instituteRepository.findById(id)
            .map(instituteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institute : {}", id);
        instituteRepository.deleteById(id);
    }
}
