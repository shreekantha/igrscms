package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.SslcResultService;
import com.myriadquest.kreiscms.domain.SslcResult;
import com.myriadquest.kreiscms.repository.SslcResultRepository;
import com.myriadquest.kreiscms.service.dto.SslcResultDTO;
import com.myriadquest.kreiscms.service.mapper.SslcResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SslcResult}.
 */
@Service
@Transactional
public class SslcResultServiceImpl implements SslcResultService {

    private final Logger log = LoggerFactory.getLogger(SslcResultServiceImpl.class);

    private final SslcResultRepository sslcResultRepository;

    private final SslcResultMapper sslcResultMapper;

    public SslcResultServiceImpl(SslcResultRepository sslcResultRepository, SslcResultMapper sslcResultMapper) {
        this.sslcResultRepository = sslcResultRepository;
        this.sslcResultMapper = sslcResultMapper;
    }

    @Override
    public SslcResultDTO save(SslcResultDTO sslcResultDTO) {
        log.debug("Request to save SslcResult : {}", sslcResultDTO);
        SslcResult sslcResult = sslcResultMapper.toEntity(sslcResultDTO);
        sslcResult = sslcResultRepository.save(sslcResult);
        return sslcResultMapper.toDto(sslcResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SslcResultDTO> findAll() {
        log.debug("Request to get all SslcResults");
        return sslcResultRepository.findAll().stream()
            .map(sslcResultMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SslcResultDTO> findOne(Long id) {
        log.debug("Request to get SslcResult : {}", id);
        return sslcResultRepository.findById(id)
            .map(sslcResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SslcResult : {}", id);
        sslcResultRepository.deleteById(id);
    }
}
