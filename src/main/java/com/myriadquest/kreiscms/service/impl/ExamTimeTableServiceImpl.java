package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.ExamTimeTableService;
import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.repository.ExamTimeTableRepository;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ExamTimeTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ExamTimeTable}.
 */
@Service
@Transactional
public class ExamTimeTableServiceImpl implements ExamTimeTableService {

    private final Logger log = LoggerFactory.getLogger(ExamTimeTableServiceImpl.class);

    private final ExamTimeTableRepository examTimeTableRepository;

    private final ExamTimeTableMapper examTimeTableMapper;

    public ExamTimeTableServiceImpl(ExamTimeTableRepository examTimeTableRepository, ExamTimeTableMapper examTimeTableMapper) {
        this.examTimeTableRepository = examTimeTableRepository;
        this.examTimeTableMapper = examTimeTableMapper;
    }

    @Override
    public ExamTimeTableDTO save(ExamTimeTableDTO examTimeTableDTO) {
        log.debug("Request to save ExamTimeTable : {}", examTimeTableDTO);
        ExamTimeTable examTimeTable = examTimeTableMapper.toEntity(examTimeTableDTO);
        examTimeTable = examTimeTableRepository.save(examTimeTable);
        return examTimeTableMapper.toDto(examTimeTable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExamTimeTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamTimeTables");
        return examTimeTableRepository.findAll(pageable)
            .map(examTimeTableMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExamTimeTableDTO> findOne(Long id) {
        log.debug("Request to get ExamTimeTable : {}", id);
        return examTimeTableRepository.findById(id)
            .map(examTimeTableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamTimeTable : {}", id);
        examTimeTableRepository.deleteById(id);
    }
}
