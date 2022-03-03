package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.GalleryService;
import com.myriadquest.kreiscms.domain.Gallery;
import com.myriadquest.kreiscms.repository.GalleryRepository;
import com.myriadquest.kreiscms.service.dto.GalleryDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Gallery}.
 */
@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {

    private final Logger log = LoggerFactory.getLogger(GalleryServiceImpl.class);

    private final GalleryRepository galleryRepository;

    private final GalleryMapper galleryMapper;

    public GalleryServiceImpl(GalleryRepository galleryRepository, GalleryMapper galleryMapper) {
        this.galleryRepository = galleryRepository;
        this.galleryMapper = galleryMapper;
    }

    @Override
    public GalleryDTO save(GalleryDTO galleryDTO) {
        log.debug("Request to save Gallery : {}", galleryDTO);
        Gallery gallery = galleryMapper.toEntity(galleryDTO);
        gallery = galleryRepository.save(gallery);
        return galleryMapper.toDto(gallery);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GalleryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Galleries");
        return galleryRepository.findAll(pageable)
            .map(galleryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GalleryDTO> findOne(Long id) {
        log.debug("Request to get Gallery : {}", id);
        return galleryRepository.findById(id)
            .map(galleryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gallery : {}", id);
        galleryRepository.deleteById(id);
    }
}
