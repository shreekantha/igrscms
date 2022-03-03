package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.SpeakerDeskService;
import com.myriadquest.kreiscms.domain.SpeakerDesk;
import com.myriadquest.kreiscms.repository.SpeakerDeskRepository;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;
import com.myriadquest.kreiscms.service.mapper.SpeakerDeskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SpeakerDesk}.
 */
@Service
@Transactional
public class SpeakerDeskServiceImpl implements SpeakerDeskService {

    private final Logger log = LoggerFactory.getLogger(SpeakerDeskServiceImpl.class);

    private final SpeakerDeskRepository speakerDeskRepository;

    private final SpeakerDeskMapper speakerDeskMapper;

    public SpeakerDeskServiceImpl(SpeakerDeskRepository speakerDeskRepository, SpeakerDeskMapper speakerDeskMapper) {
        this.speakerDeskRepository = speakerDeskRepository;
        this.speakerDeskMapper = speakerDeskMapper;
    }

    @Override
    public SpeakerDeskDTO save(SpeakerDeskDTO speakerDeskDTO) {
        log.debug("Request to save SpeakerDesk : {}", speakerDeskDTO);
        SpeakerDesk speakerDesk = speakerDeskMapper.toEntity(speakerDeskDTO);
        speakerDesk = speakerDeskRepository.save(speakerDesk);
        return speakerDeskMapper.toDto(speakerDesk);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpeakerDeskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpeakerDesks");
        return speakerDeskRepository.findAll(pageable)
            .map(speakerDeskMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SpeakerDeskDTO> findOne(Long id) {
        log.debug("Request to get SpeakerDesk : {}", id);
        return speakerDeskRepository.findById(id)
            .map(speakerDeskMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpeakerDesk : {}", id);
        speakerDeskRepository.deleteById(id);
    }
}
