package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.DegreeService;
import com.myriadquest.kreiscms.domain.Degree;
import com.myriadquest.kreiscms.repository.DegreeRepository;
import com.myriadquest.kreiscms.service.dto.DegreeDTO;
import com.myriadquest.kreiscms.service.mapper.DegreeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Degree}.
 */
@Service
@Transactional
public class DegreeServiceImpl implements DegreeService {

    private final Logger log = LoggerFactory.getLogger(DegreeServiceImpl.class);

    private final DegreeRepository degreeRepository;

    private final DegreeMapper degreeMapper;

    public DegreeServiceImpl(DegreeRepository degreeRepository, DegreeMapper degreeMapper) {
        this.degreeRepository = degreeRepository;
        this.degreeMapper = degreeMapper;
    }

    @Override
    public DegreeDTO save(DegreeDTO degreeDTO) {
        log.debug("Request to save Degree : {}", degreeDTO);
        Degree degree = degreeMapper.toEntity(degreeDTO);
        degree = degreeRepository.save(degree);
        return degreeMapper.toDto(degree);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DegreeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Degrees");
        return degreeRepository.findAll(pageable)
            .map(degreeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DegreeDTO> findOne(Long id) {
        log.debug("Request to get Degree : {}", id);
        return degreeRepository.findById(id)
            .map(degreeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Degree : {}", id);
        degreeRepository.deleteById(id);
    }
}
