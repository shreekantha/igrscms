package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.MissionService;
import com.myriadquest.kreiscms.domain.Mission;
import com.myriadquest.kreiscms.repository.MissionRepository;
import com.myriadquest.kreiscms.service.dto.MissionDTO;
import com.myriadquest.kreiscms.service.mapper.MissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Mission}.
 */
@Service
@Transactional
public class MissionServiceImpl implements MissionService {

    private final Logger log = LoggerFactory.getLogger(MissionServiceImpl.class);

    private final MissionRepository missionRepository;

    private final MissionMapper missionMapper;

    public MissionServiceImpl(MissionRepository missionRepository, MissionMapper missionMapper) {
        this.missionRepository = missionRepository;
        this.missionMapper = missionMapper;
    }

    @Override
    public MissionDTO save(MissionDTO missionDTO) {
        log.debug("Request to save Mission : {}", missionDTO);
        Mission mission = missionMapper.toEntity(missionDTO);
        mission = missionRepository.save(mission);
        return missionMapper.toDto(mission);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Missions");
        return missionRepository.findAll(pageable)
            .map(missionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MissionDTO> findOne(Long id) {
        log.debug("Request to get Mission : {}", id);
        return missionRepository.findById(id)
            .map(missionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mission : {}", id);
        missionRepository.deleteById(id);
    }
}
