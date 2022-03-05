package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.ExamTimeTable}.
 */
public interface ExamTimeTableService {

    /**
     * Save a examTimeTable.
     *
     * @param examTimeTableDTO the entity to save.
     * @return the persisted entity.
     */
    ExamTimeTableDTO save(ExamTimeTableDTO examTimeTableDTO);

    /**
     * Get all the examTimeTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamTimeTableDTO> findAll(Pageable pageable);


    /**
     * Get the "id" examTimeTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamTimeTableDTO> findOne(Long id);

    /**
     * Delete the "id" examTimeTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
