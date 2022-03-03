package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.NewsService;
import com.myriadquest.kreiscms.domain.News;
import com.myriadquest.kreiscms.repository.NewsRepository;
import com.myriadquest.kreiscms.service.dto.NewsDTO;
import com.myriadquest.kreiscms.service.mapper.NewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link News}.
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    private final NewsRepository newsRepository;

    private final NewsMapper newsMapper;

    public NewsServiceImpl(NewsRepository newsRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
    }

    @Override
    public NewsDTO save(NewsDTO newsDTO) {
        log.debug("Request to save News : {}", newsDTO);
        News news = newsMapper.toEntity(newsDTO);
        news = newsRepository.save(news);
        return newsMapper.toDto(news);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all News");
        return newsRepository.findAll(pageable)
            .map(newsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NewsDTO> findOne(Long id) {
        log.debug("Request to get News : {}", id);
        return newsRepository.findById(id)
            .map(newsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete News : {}", id);
        newsRepository.deleteById(id);
    }
}
