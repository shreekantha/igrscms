package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.SpeakerDeskService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.SpeakerDeskQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.SpeakerDesk}.
 */
@RestController
@RequestMapping("/api")
public class SpeakerDeskResource {

    private final Logger log = LoggerFactory.getLogger(SpeakerDeskResource.class);

    private static final String ENTITY_NAME = "speakerDesk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpeakerDeskService speakerDeskService;

    private final SpeakerDeskQueryService speakerDeskQueryService;

    public SpeakerDeskResource(SpeakerDeskService speakerDeskService, SpeakerDeskQueryService speakerDeskQueryService) {
        this.speakerDeskService = speakerDeskService;
        this.speakerDeskQueryService = speakerDeskQueryService;
    }

    /**
     * {@code POST  /speaker-desks} : Create a new speakerDesk.
     *
     * @param speakerDeskDTO the speakerDeskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new speakerDeskDTO, or with status {@code 400 (Bad Request)} if the speakerDesk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/speaker-desks")
    public ResponseEntity<SpeakerDeskDTO> createSpeakerDesk(@Valid @RequestBody SpeakerDeskDTO speakerDeskDTO) throws URISyntaxException {
        log.debug("REST request to save SpeakerDesk : {}", speakerDeskDTO);
        if (speakerDeskDTO.getId() != null) {
            throw new BadRequestAlertException("A new speakerDesk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeakerDeskDTO result = speakerDeskService.save(speakerDeskDTO);
        return ResponseEntity.created(new URI("/api/speaker-desks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /speaker-desks} : Updates an existing speakerDesk.
     *
     * @param speakerDeskDTO the speakerDeskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated speakerDeskDTO,
     * or with status {@code 400 (Bad Request)} if the speakerDeskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the speakerDeskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/speaker-desks")
    public ResponseEntity<SpeakerDeskDTO> updateSpeakerDesk(@Valid @RequestBody SpeakerDeskDTO speakerDeskDTO) throws URISyntaxException {
        log.debug("REST request to update SpeakerDesk : {}", speakerDeskDTO);
        if (speakerDeskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpeakerDeskDTO result = speakerDeskService.save(speakerDeskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, speakerDeskDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /speaker-desks} : get all the speakerDesks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of speakerDesks in body.
     */
    @GetMapping("/speaker-desks")
    public ResponseEntity<List<SpeakerDeskDTO>> getAllSpeakerDesks(SpeakerDeskCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get SpeakerDesks by criteria: {}", criteria);
        Page<SpeakerDeskDTO> page = speakerDeskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /speaker-desks/count} : count all the speakerDesks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/speaker-desks/count")
    public ResponseEntity<Long> countSpeakerDesks(SpeakerDeskCriteria criteria) {
        log.debug("REST request to count SpeakerDesks by criteria: {}", criteria);
        return ResponseEntity.ok().body(speakerDeskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /speaker-desks/:id} : get the "id" speakerDesk.
     *
     * @param id the id of the speakerDeskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the speakerDeskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/speaker-desks/{id}")
    public ResponseEntity<SpeakerDeskDTO> getSpeakerDesk(@PathVariable Long id) {
        log.debug("REST request to get SpeakerDesk : {}", id);
        Optional<SpeakerDeskDTO> speakerDeskDTO = speakerDeskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(speakerDeskDTO);
    }

    /**
     * {@code DELETE  /speaker-desks/:id} : delete the "id" speakerDesk.
     *
     * @param id the id of the speakerDeskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/speaker-desks/{id}")
    public ResponseEntity<Void> deleteSpeakerDesk(@PathVariable Long id) {
        log.debug("REST request to delete SpeakerDesk : {}", id);
        speakerDeskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
