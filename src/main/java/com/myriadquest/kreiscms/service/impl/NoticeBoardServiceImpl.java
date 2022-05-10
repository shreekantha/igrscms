package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.NoticeBoardService;
import com.myriadquest.kreiscms.domain.NoticeBoard;
import com.myriadquest.kreiscms.repository.NoticeBoardRepository;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;
import com.myriadquest.kreiscms.service.mapper.NoticeBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NoticeBoard}.
 */
@Service
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final Logger log = LoggerFactory.getLogger(NoticeBoardServiceImpl.class);

    private final NoticeBoardRepository noticeBoardRepository;

    private final NoticeBoardMapper noticeBoardMapper;

    public NoticeBoardServiceImpl(NoticeBoardRepository noticeBoardRepository, NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardMapper = noticeBoardMapper;
    }

    @Override
    public NoticeBoardDTO save(NoticeBoardDTO noticeBoardDTO) {
        log.debug("Request to save NoticeBoard : {}", noticeBoardDTO);
        NoticeBoard noticeBoard = noticeBoardMapper.toEntity(noticeBoardDTO);
        noticeBoard = noticeBoardRepository.save(noticeBoard);
        return noticeBoardMapper.toDto(noticeBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoticeBoardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoticeBoards");
        return noticeBoardRepository.findAll(pageable)
            .map(noticeBoardMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NoticeBoardDTO> findOne(Long id) {
        log.debug("Request to get NoticeBoard : {}", id);
        return noticeBoardRepository.findById(id)
            .map(noticeBoardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoticeBoard : {}", id);
        noticeBoardRepository.deleteById(id);
    }
}
