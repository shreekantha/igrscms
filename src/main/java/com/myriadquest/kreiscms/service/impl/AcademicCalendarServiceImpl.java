package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.AcademicCalendarService;
import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.repository.AcademicCalendarRepository;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;
import com.myriadquest.kreiscms.service.mapper.AcademicCalendarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AcademicCalendar}.
 */
@Service
@Transactional
public class AcademicCalendarServiceImpl implements AcademicCalendarService {

    private final Logger log = LoggerFactory.getLogger(AcademicCalendarServiceImpl.class);

    private final AcademicCalendarRepository academicCalendarRepository;

    private final AcademicCalendarMapper academicCalendarMapper;

    public AcademicCalendarServiceImpl(AcademicCalendarRepository academicCalendarRepository, AcademicCalendarMapper academicCalendarMapper) {
        this.academicCalendarRepository = academicCalendarRepository;
        this.academicCalendarMapper = academicCalendarMapper;
    }

    @Override
    public AcademicCalendarDTO save(AcademicCalendarDTO academicCalendarDTO) {
        log.debug("Request to save AcademicCalendar : {}", academicCalendarDTO);
        AcademicCalendar academicCalendar = academicCalendarMapper.toEntity(academicCalendarDTO);
        academicCalendar = academicCalendarRepository.save(academicCalendar);
        return academicCalendarMapper.toDto(academicCalendar);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AcademicCalendarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicCalendars");
        return academicCalendarRepository.findAll(pageable)
            .map(academicCalendarMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicCalendarDTO> findOne(Long id) {
        log.debug("Request to get AcademicCalendar : {}", id);
        return academicCalendarRepository.findById(id)
            .map(academicCalendarMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicCalendar : {}", id);
        academicCalendarRepository.deleteById(id);
    }
}
