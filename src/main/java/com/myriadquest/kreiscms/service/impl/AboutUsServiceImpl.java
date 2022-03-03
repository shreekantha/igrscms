package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.AboutUsService;
import com.myriadquest.kreiscms.domain.AboutUs;
import com.myriadquest.kreiscms.repository.AboutUsRepository;
import com.myriadquest.kreiscms.service.dto.AboutUsDTO;
import com.myriadquest.kreiscms.service.mapper.AboutUsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AboutUs}.
 */
@Service
@Transactional
public class AboutUsServiceImpl implements AboutUsService {

    private final Logger log = LoggerFactory.getLogger(AboutUsServiceImpl.class);

    private final AboutUsRepository aboutUsRepository;

    private final AboutUsMapper aboutUsMapper;

    public AboutUsServiceImpl(AboutUsRepository aboutUsRepository, AboutUsMapper aboutUsMapper) {
        this.aboutUsRepository = aboutUsRepository;
        this.aboutUsMapper = aboutUsMapper;
    }

    @Override
    public AboutUsDTO save(AboutUsDTO aboutUsDTO) {
        log.debug("Request to save AboutUs : {}", aboutUsDTO);
        AboutUs aboutUs = aboutUsMapper.toEntity(aboutUsDTO);
        aboutUs = aboutUsRepository.save(aboutUs);
        return aboutUsMapper.toDto(aboutUs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AboutUsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Aboutuses");
        return aboutUsRepository.findAll(pageable)
            .map(aboutUsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AboutUsDTO> findOne(Long id) {
        log.debug("Request to get AboutUs : {}", id);
        return aboutUsRepository.findById(id)
            .map(aboutUsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AboutUs : {}", id);
        aboutUsRepository.deleteById(id);
    }
}
