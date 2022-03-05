package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.AcademicCalendarService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.AcademicCalendar}.
 */
@RestController
@RequestMapping("/api")
public class AcademicCalendarResource {

    private final Logger log = LoggerFactory.getLogger(AcademicCalendarResource.class);

    private static final String ENTITY_NAME = "academicCalendar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicCalendarService academicCalendarService;

    public AcademicCalendarResource(AcademicCalendarService academicCalendarService) {
        this.academicCalendarService = academicCalendarService;
    }

    /**
     * {@code POST  /academic-calendars} : Create a new academicCalendar.
     *
     * @param academicCalendarDTO the academicCalendarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicCalendarDTO, or with status {@code 400 (Bad Request)} if the academicCalendar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-calendars")
    public ResponseEntity<AcademicCalendarDTO> createAcademicCalendar(@Valid @RequestBody AcademicCalendarDTO academicCalendarDTO) throws URISyntaxException {
        log.debug("REST request to save AcademicCalendar : {}", academicCalendarDTO);
        if (academicCalendarDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicCalendar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicCalendarDTO result = academicCalendarService.save(academicCalendarDTO);
        return ResponseEntity.created(new URI("/api/academic-calendars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-calendars} : Updates an existing academicCalendar.
     *
     * @param academicCalendarDTO the academicCalendarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicCalendarDTO,
     * or with status {@code 400 (Bad Request)} if the academicCalendarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicCalendarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-calendars")
    public ResponseEntity<AcademicCalendarDTO> updateAcademicCalendar(@Valid @RequestBody AcademicCalendarDTO academicCalendarDTO) throws URISyntaxException {
        log.debug("REST request to update AcademicCalendar : {}", academicCalendarDTO);
        if (academicCalendarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcademicCalendarDTO result = academicCalendarService.save(academicCalendarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, academicCalendarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /academic-calendars} : get all the academicCalendars.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicCalendars in body.
     */
    @GetMapping("/academic-calendars")
    public ResponseEntity<List<AcademicCalendarDTO>> getAllAcademicCalendars(Pageable pageable) {
        log.debug("REST request to get a page of AcademicCalendars");
        Page<AcademicCalendarDTO> page = academicCalendarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academic-calendars/:id} : get the "id" academicCalendar.
     *
     * @param id the id of the academicCalendarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicCalendarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-calendars/{id}")
    public ResponseEntity<AcademicCalendarDTO> getAcademicCalendar(@PathVariable Long id) {
        log.debug("REST request to get AcademicCalendar : {}", id);
        Optional<AcademicCalendarDTO> academicCalendarDTO = academicCalendarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicCalendarDTO);
    }

    /**
     * {@code DELETE  /academic-calendars/:id} : delete the "id" academicCalendar.
     *
     * @param id the id of the academicCalendarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-calendars/{id}")
    public ResponseEntity<Void> deleteAcademicCalendar(@PathVariable Long id) {
        log.debug("REST request to delete AcademicCalendar : {}", id);
        academicCalendarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
