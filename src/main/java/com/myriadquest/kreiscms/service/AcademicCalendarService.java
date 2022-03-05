package com.myriadquest.kreiscms.service;

import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.myriadquest.kreiscms.domain.AcademicCalendar}.
 */
public interface AcademicCalendarService {

    /**
     * Save a academicCalendar.
     *
     * @param academicCalendarDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicCalendarDTO save(AcademicCalendarDTO academicCalendarDTO);

    /**
     * Get all the academicCalendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcademicCalendarDTO> findAll(Pageable pageable);


    /**
     * Get the "id" academicCalendar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicCalendarDTO> findOne(Long id);

    /**
     * Delete the "id" academicCalendar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
