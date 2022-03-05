package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.ClassTimeTableConfigService;
import com.myriadquest.kreiscms.domain.ClassTimeTableConfig;
import com.myriadquest.kreiscms.repository.ClassTimeTableConfigRepository;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClassTimeTableConfig}.
 */
@Service
@Transactional
public class ClassTimeTableConfigServiceImpl implements ClassTimeTableConfigService {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableConfigServiceImpl.class);

    private final ClassTimeTableConfigRepository classTimeTableConfigRepository;

    private final ClassTimeTableConfigMapper classTimeTableConfigMapper;

    public ClassTimeTableConfigServiceImpl(ClassTimeTableConfigRepository classTimeTableConfigRepository, ClassTimeTableConfigMapper classTimeTableConfigMapper) {
        this.classTimeTableConfigRepository = classTimeTableConfigRepository;
        this.classTimeTableConfigMapper = classTimeTableConfigMapper;
    }

    @Override
    public ClassTimeTableConfigDTO save(ClassTimeTableConfigDTO classTimeTableConfigDTO) {
        log.debug("Request to save ClassTimeTableConfig : {}", classTimeTableConfigDTO);
        ClassTimeTableConfig classTimeTableConfig = classTimeTableConfigMapper.toEntity(classTimeTableConfigDTO);
        classTimeTableConfig = classTimeTableConfigRepository.save(classTimeTableConfig);
        return classTimeTableConfigMapper.toDto(classTimeTableConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassTimeTableConfigDTO> findAll() {
        log.debug("Request to get all ClassTimeTableConfigs");
        return classTimeTableConfigRepository.findAll().stream()
            .map(classTimeTableConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ClassTimeTableConfigDTO> findOne(Long id) {
        log.debug("Request to get ClassTimeTableConfig : {}", id);
        return classTimeTableConfigRepository.findById(id)
            .map(classTimeTableConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassTimeTableConfig : {}", id);
        classTimeTableConfigRepository.deleteById(id);
    }
}
