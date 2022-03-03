package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.TermService;
import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.repository.TermRepository;
import com.myriadquest.kreiscms.service.dto.TermDTO;
import com.myriadquest.kreiscms.service.mapper.TermMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Term}.
 */
@Service
@Transactional
public class TermServiceImpl implements TermService {

    private final Logger log = LoggerFactory.getLogger(TermServiceImpl.class);

    private final TermRepository termRepository;

    private final TermMapper termMapper;

    public TermServiceImpl(TermRepository termRepository, TermMapper termMapper) {
        this.termRepository = termRepository;
        this.termMapper = termMapper;
    }

    @Override
    public TermDTO save(TermDTO termDTO) {
        log.debug("Request to save Term : {}", termDTO);
        Term term = termMapper.toEntity(termDTO);
        term = termRepository.save(term);
        return termMapper.toDto(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TermDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Terms");
        return termRepository.findAll(pageable)
            .map(termMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TermDTO> findOne(Long id) {
        log.debug("Request to get Term : {}", id);
        return termRepository.findById(id)
            .map(termMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Term : {}", id);
        termRepository.deleteById(id);
    }
}
