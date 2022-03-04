package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.HomeImgService;
import com.myriadquest.kreiscms.domain.HomeImg;
import com.myriadquest.kreiscms.repository.HomeImgRepository;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;
import com.myriadquest.kreiscms.service.mapper.HomeImgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link HomeImg}.
 */
@Service
@Transactional
public class HomeImgServiceImpl implements HomeImgService {

    private final Logger log = LoggerFactory.getLogger(HomeImgServiceImpl.class);

    private final HomeImgRepository homeImgRepository;

    private final HomeImgMapper homeImgMapper;

    public HomeImgServiceImpl(HomeImgRepository homeImgRepository, HomeImgMapper homeImgMapper) {
        this.homeImgRepository = homeImgRepository;
        this.homeImgMapper = homeImgMapper;
    }

    @Override
    public HomeImgDTO save(HomeImgDTO homeImgDTO) {
        log.debug("Request to save HomeImg : {}", homeImgDTO);
        HomeImg homeImg = homeImgMapper.toEntity(homeImgDTO);
        homeImg = homeImgRepository.save(homeImg);
        return homeImgMapper.toDto(homeImg);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeImgDTO> findAll() {
        log.debug("Request to get all HomeImgs");
        return homeImgRepository.findAll().stream()
            .map(homeImgMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<HomeImgDTO> findOne(Long id) {
        log.debug("Request to get HomeImg : {}", id);
        return homeImgRepository.findById(id)
            .map(homeImgMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete HomeImg : {}", id);
        homeImgRepository.deleteById(id);
    }
}
