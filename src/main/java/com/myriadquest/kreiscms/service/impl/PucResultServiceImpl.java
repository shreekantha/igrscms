package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.PucResultService;
import com.myriadquest.kreiscms.domain.PucResult;
import com.myriadquest.kreiscms.repository.PucResultRepository;
import com.myriadquest.kreiscms.service.dto.PucResultDTO;
import com.myriadquest.kreiscms.service.mapper.PucResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PucResult}.
 */
@Service
@Transactional
public class PucResultServiceImpl implements PucResultService {

    private final Logger log = LoggerFactory.getLogger(PucResultServiceImpl.class);

    private final PucResultRepository pucResultRepository;

    private final PucResultMapper pucResultMapper;

    public PucResultServiceImpl(PucResultRepository pucResultRepository, PucResultMapper pucResultMapper) {
        this.pucResultRepository = pucResultRepository;
        this.pucResultMapper = pucResultMapper;
    }

    @Override
    public PucResultDTO save(PucResultDTO pucResultDTO) {
        log.debug("Request to save PucResult : {}", pucResultDTO);
        PucResult pucResult = pucResultMapper.toEntity(pucResultDTO);
        pucResult = pucResultRepository.save(pucResult);
        return pucResultMapper.toDto(pucResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PucResultDTO> findAll() {
        log.debug("Request to get all PucResults");
        return pucResultRepository.findAll().stream()
            .map(pucResultMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PucResultDTO> findOne(Long id) {
        log.debug("Request to get PucResult : {}", id);
        return pucResultRepository.findById(id)
            .map(pucResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PucResult : {}", id);
        pucResultRepository.deleteById(id);
    }
}
