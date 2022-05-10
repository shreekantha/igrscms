package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.HomeImgService;
import com.myriadquest.kreiscms.domain.HomeImg;
import com.myriadquest.kreiscms.repository.HomeImgRepository;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;
import com.myriadquest.kreiscms.service.mapper.HomeImgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public Page<HomeImgDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HomeImgs");
        return homeImgRepository.findAll(pageable)
            .map(homeImgMapper::toDto);
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
