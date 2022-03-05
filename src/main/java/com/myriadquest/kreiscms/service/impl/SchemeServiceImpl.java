package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.SchemeService;
import com.myriadquest.kreiscms.domain.Scheme;
import com.myriadquest.kreiscms.repository.SchemeRepository;
import com.myriadquest.kreiscms.service.dto.SchemeDTO;
import com.myriadquest.kreiscms.service.mapper.SchemeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Scheme}.
 */
@Service
@Transactional
public class SchemeServiceImpl implements SchemeService {

    private final Logger log = LoggerFactory.getLogger(SchemeServiceImpl.class);

    private final SchemeRepository schemeRepository;

    private final SchemeMapper schemeMapper;

    public SchemeServiceImpl(SchemeRepository schemeRepository, SchemeMapper schemeMapper) {
        this.schemeRepository = schemeRepository;
        this.schemeMapper = schemeMapper;
    }

    @Override
    public SchemeDTO save(SchemeDTO schemeDTO) {
        log.debug("Request to save Scheme : {}", schemeDTO);
        Scheme scheme = schemeMapper.toEntity(schemeDTO);
        scheme = schemeRepository.save(scheme);
        return schemeMapper.toDto(scheme);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchemeDTO> findAll() {
        log.debug("Request to get all Schemes");
        return schemeRepository.findAll().stream()
            .map(schemeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SchemeDTO> findOne(Long id) {
        log.debug("Request to get Scheme : {}", id);
        return schemeRepository.findById(id)
            .map(schemeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scheme : {}", id);
        schemeRepository.deleteById(id);
    }
}
