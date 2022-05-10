package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.TestimonialService;
import com.myriadquest.kreiscms.domain.Testimonial;
import com.myriadquest.kreiscms.repository.TestimonialRepository;
import com.myriadquest.kreiscms.service.dto.TestimonialDTO;
import com.myriadquest.kreiscms.service.mapper.TestimonialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Testimonial}.
 */
@Service
@Transactional
public class TestimonialServiceImpl implements TestimonialService {

    private final Logger log = LoggerFactory.getLogger(TestimonialServiceImpl.class);

    private final TestimonialRepository testimonialRepository;

    private final TestimonialMapper testimonialMapper;

    public TestimonialServiceImpl(TestimonialRepository testimonialRepository, TestimonialMapper testimonialMapper) {
        this.testimonialRepository = testimonialRepository;
        this.testimonialMapper = testimonialMapper;
    }

    @Override
    public TestimonialDTO save(TestimonialDTO testimonialDTO) {
        log.debug("Request to save Testimonial : {}", testimonialDTO);
        Testimonial testimonial = testimonialMapper.toEntity(testimonialDTO);
        testimonial = testimonialRepository.save(testimonial);
        return testimonialMapper.toDto(testimonial);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestimonialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Testimonials");
        return testimonialRepository.findAll(pageable)
            .map(testimonialMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TestimonialDTO> findOne(Long id) {
        log.debug("Request to get Testimonial : {}", id);
        return testimonialRepository.findById(id)
            .map(testimonialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Testimonial : {}", id);
        testimonialRepository.deleteById(id);
    }
}
