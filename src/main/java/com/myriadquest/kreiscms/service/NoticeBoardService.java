package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.NoticeBoard}.
 */
public interface NoticeBoardService {

    /**
     * Save a noticeBoard.
     *
     * @param noticeBoardDTO the entity to save.
     * @return the persisted entity.
     */
    NoticeBoardDTO save(NoticeBoardDTO noticeBoardDTO);

    /**
     * Get all the noticeBoards.
     *
     * @return the list of entities.
     */
    List<NoticeBoardDTO> findAll();


    /**
     * Get the "id" noticeBoard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NoticeBoardDTO> findOne(Long id);

    /**
     * Delete the "id" noticeBoard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
