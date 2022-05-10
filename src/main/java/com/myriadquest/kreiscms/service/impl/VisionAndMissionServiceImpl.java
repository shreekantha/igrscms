package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.VisionAndMissionService;
import com.myriadquest.kreiscms.domain.VisionAndMission;
import com.myriadquest.kreiscms.repository.VisionAndMissionRepository;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionAndMissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link VisionAndMission}.
 */
@Service
@Transactional
public class VisionAndMissionServiceImpl implements VisionAndMissionService {

    private final Logger log = LoggerFactory.getLogger(VisionAndMissionServiceImpl.class);

    private final VisionAndMissionRepository visionAndMissionRepository;

    private final VisionAndMissionMapper visionAndMissionMapper;

    public VisionAndMissionServiceImpl(VisionAndMissionRepository visionAndMissionRepository, VisionAndMissionMapper visionAndMissionMapper) {
        this.visionAndMissionRepository = visionAndMissionRepository;
        this.visionAndMissionMapper = visionAndMissionMapper;
    }

    @Override
    public VisionAndMissionDTO save(VisionAndMissionDTO visionAndMissionDTO) {
        log.debug("Request to save VisionAndMission : {}", visionAndMissionDTO);
        VisionAndMission visionAndMission = visionAndMissionMapper.toEntity(visionAndMissionDTO);
        visionAndMission = visionAndMissionRepository.save(visionAndMission);
        return visionAndMissionMapper.toDto(visionAndMission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VisionAndMissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VisionAndMissions");
        return visionAndMissionRepository.findAll(pageable)
            .map(visionAndMissionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VisionAndMissionDTO> findOne(Long id) {
        log.debug("Request to get VisionAndMission : {}", id);
        return visionAndMissionRepository.findById(id)
            .map(visionAndMissionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VisionAndMission : {}", id);
        visionAndMissionRepository.deleteById(id);
    }
}
