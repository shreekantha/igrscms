package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.PeriodService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.PeriodDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Period}.
 */
@RestController
@RequestMapping("/api")
public class PeriodResource {

    private final Logger log = LoggerFactory.getLogger(PeriodResource.class);

    private static final String ENTITY_NAME = "period";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeriodService periodService;

    public PeriodResource(PeriodService periodService) {
        this.periodService = periodService;
    }

    /**
     * {@code POST  /periods} : Create a new period.
     *
     * @param periodDTO the periodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new periodDTO, or with status {@code 400 (Bad Request)} if the period has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/periods")
    public ResponseEntity<PeriodDTO> createPeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to save Period : {}", periodDTO);
        if (periodDTO.getId() != null) {
            throw new BadRequestAlertException("A new period cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.created(new URI("/api/periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /periods} : Updates an existing period.
     *
     * @param periodDTO the periodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated periodDTO,
     * or with status {@code 400 (Bad Request)} if the periodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the periodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/periods")
    public ResponseEntity<PeriodDTO> updatePeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to update Period : {}", periodDTO);
        if (periodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, periodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /periods} : get all the periods.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of periods in body.
     */
    @GetMapping("/periods")
    public List<PeriodDTO> getAllPeriods() {
        log.debug("REST request to get all Periods");
        return periodService.findAll();
    }

    /**
     * {@code GET  /periods/:id} : get the "id" period.
     *
     * @param id the id of the periodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the periodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/periods/{id}")
    public ResponseEntity<PeriodDTO> getPeriod(@PathVariable Long id) {
        log.debug("REST request to get Period : {}", id);
        Optional<PeriodDTO> periodDTO = periodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodDTO);
    }

    /**
     * {@code DELETE  /periods/:id} : delete the "id" period.
     *
     * @param id the id of the periodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/periods/{id}")
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        log.debug("REST request to delete Period : {}", id);
        periodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
