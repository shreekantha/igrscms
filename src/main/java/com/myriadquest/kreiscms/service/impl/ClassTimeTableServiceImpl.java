package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.ClassTimeTableService;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.repository.ClassTimeTableRepository;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClassTimeTable}.
 */
@Service
@Transactional
public class ClassTimeTableServiceImpl implements ClassTimeTableService {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableServiceImpl.class);

    private final ClassTimeTableRepository classTimeTableRepository;

    private final ClassTimeTableMapper classTimeTableMapper;

    public ClassTimeTableServiceImpl(ClassTimeTableRepository classTimeTableRepository, ClassTimeTableMapper classTimeTableMapper) {
        this.classTimeTableRepository = classTimeTableRepository;
        this.classTimeTableMapper = classTimeTableMapper;
    }

    @Override
    public ClassTimeTableDTO save(ClassTimeTableDTO classTimeTableDTO) {
        log.debug("Request to save ClassTimeTable : {}", classTimeTableDTO);
        ClassTimeTable classTimeTable = classTimeTableMapper.toEntity(classTimeTableDTO);
        classTimeTable = classTimeTableRepository.save(classTimeTable);
        return classTimeTableMapper.toDto(classTimeTable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassTimeTableDTO> findAll() {
        log.debug("Request to get all ClassTimeTables");
        return classTimeTableRepository.findAll().stream()
            .map(classTimeTableMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ClassTimeTableDTO> findOne(Long id) {
        log.debug("Request to get ClassTimeTable : {}", id);
        return classTimeTableRepository.findById(id)
            .map(classTimeTableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassTimeTable : {}", id);
        classTimeTableRepository.deleteById(id);
    }
}
