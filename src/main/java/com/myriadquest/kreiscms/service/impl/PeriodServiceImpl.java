package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.PeriodService;
import com.myriadquest.kreiscms.domain.Period;
import com.myriadquest.kreiscms.repository.PeriodRepository;
import com.myriadquest.kreiscms.service.dto.PeriodDTO;
import com.myriadquest.kreiscms.service.mapper.PeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Period}.
 */
@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    public PeriodServiceImpl(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    @Override
    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        return periodMapper.toDto(period);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PeriodDTO> findAll() {
        log.debug("Request to get all Periods");
        return periodRepository.findAll().stream()
            .map(periodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodDTO> findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        return periodRepository.findById(id)
            .map(periodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.deleteById(id);
    }
}
