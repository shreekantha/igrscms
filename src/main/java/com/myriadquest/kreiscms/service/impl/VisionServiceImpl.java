package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.VisionService;
import com.myriadquest.kreiscms.domain.Vision;
import com.myriadquest.kreiscms.repository.VisionRepository;
import com.myriadquest.kreiscms.service.dto.VisionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Vision}.
 */
@Service
@Transactional
public class VisionServiceImpl implements VisionService {

    private final Logger log = LoggerFactory.getLogger(VisionServiceImpl.class);

    private final VisionRepository visionRepository;

    private final VisionMapper visionMapper;

    public VisionServiceImpl(VisionRepository visionRepository, VisionMapper visionMapper) {
        this.visionRepository = visionRepository;
        this.visionMapper = visionMapper;
    }

    @Override
    public VisionDTO save(VisionDTO visionDTO) {
        log.debug("Request to save Vision : {}", visionDTO);
        Vision vision = visionMapper.toEntity(visionDTO);
        vision = visionRepository.save(vision);
        return visionMapper.toDto(vision);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Visions");
        return visionRepository.findAll(pageable)
            .map(visionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VisionDTO> findOne(Long id) {
        log.debug("Request to get Vision : {}", id);
        return visionRepository.findById(id)
            .map(visionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Vision : {}", id);
        visionRepository.deleteById(id);
    }
}
