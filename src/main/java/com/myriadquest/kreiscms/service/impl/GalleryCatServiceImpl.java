package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.GalleryCatService;
import com.myriadquest.kreiscms.domain.GalleryCat;
import com.myriadquest.kreiscms.repository.GalleryCatRepository;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryCatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GalleryCat}.
 */
@Service
@Transactional
public class GalleryCatServiceImpl implements GalleryCatService {

    private final Logger log = LoggerFactory.getLogger(GalleryCatServiceImpl.class);

    private final GalleryCatRepository galleryCatRepository;

    private final GalleryCatMapper galleryCatMapper;

    public GalleryCatServiceImpl(GalleryCatRepository galleryCatRepository, GalleryCatMapper galleryCatMapper) {
        this.galleryCatRepository = galleryCatRepository;
        this.galleryCatMapper = galleryCatMapper;
    }

    @Override
    public GalleryCatDTO save(GalleryCatDTO galleryCatDTO) {
        log.debug("Request to save GalleryCat : {}", galleryCatDTO);
        GalleryCat galleryCat = galleryCatMapper.toEntity(galleryCatDTO);
        galleryCat = galleryCatRepository.save(galleryCat);
        return galleryCatMapper.toDto(galleryCat);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GalleryCatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GalleryCats");
        return galleryCatRepository.findAll(pageable)
            .map(galleryCatMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GalleryCatDTO> findOne(Long id) {
        log.debug("Request to get GalleryCat : {}", id);
        return galleryCatRepository.findById(id)
            .map(galleryCatMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GalleryCat : {}", id);
        galleryCatRepository.deleteById(id);
    }
}
