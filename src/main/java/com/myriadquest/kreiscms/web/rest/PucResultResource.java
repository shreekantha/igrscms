package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.PucResultService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.PucResultDTO;
import com.myriadquest.kreiscms.service.dto.PucResultCriteria;
import com.myriadquest.kreiscms.service.PucResultQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.PucResult}.
 */
@RestController
@RequestMapping("/api")
public class PucResultResource {

    private final Logger log = LoggerFactory.getLogger(PucResultResource.class);

    private static final String ENTITY_NAME = "pucResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PucResultService pucResultService;

    private final PucResultQueryService pucResultQueryService;

    public PucResultResource(PucResultService pucResultService, PucResultQueryService pucResultQueryService) {
        this.pucResultService = pucResultService;
        this.pucResultQueryService = pucResultQueryService;
    }

    /**
     * {@code POST  /puc-results} : Create a new pucResult.
     *
     * @param pucResultDTO the pucResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pucResultDTO, or with status {@code 400 (Bad Request)} if the pucResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/puc-results")
    public ResponseEntity<PucResultDTO> createPucResult(@Valid @RequestBody PucResultDTO pucResultDTO) throws URISyntaxException {
        log.debug("REST request to save PucResult : {}", pucResultDTO);
        if (pucResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new pucResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PucResultDTO result = pucResultService.save(pucResultDTO);
        return ResponseEntity.created(new URI("/api/puc-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /puc-results} : Updates an existing pucResult.
     *
     * @param pucResultDTO the pucResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pucResultDTO,
     * or with status {@code 400 (Bad Request)} if the pucResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pucResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/puc-results")
    public ResponseEntity<PucResultDTO> updatePucResult(@Valid @RequestBody PucResultDTO pucResultDTO) throws URISyntaxException {
        log.debug("REST request to update PucResult : {}", pucResultDTO);
        if (pucResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PucResultDTO result = pucResultService.save(pucResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pucResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /puc-results} : get all the pucResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pucResults in body.
     */
    @GetMapping("/puc-results")
    public ResponseEntity<List<PucResultDTO>> getAllPucResults(PucResultCriteria criteria) {
        log.debug("REST request to get PucResults by criteria: {}", criteria);
        List<PucResultDTO> entityList = pucResultQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /puc-results/count} : count all the pucResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/puc-results/count")
    public ResponseEntity<Long> countPucResults(PucResultCriteria criteria) {
        log.debug("REST request to count PucResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(pucResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /puc-results/:id} : get the "id" pucResult.
     *
     * @param id the id of the pucResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pucResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/puc-results/{id}")
    public ResponseEntity<PucResultDTO> getPucResult(@PathVariable Long id) {
        log.debug("REST request to get PucResult : {}", id);
        Optional<PucResultDTO> pucResultDTO = pucResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pucResultDTO);
    }

    /**
     * {@code DELETE  /puc-results/:id} : delete the "id" pucResult.
     *
     * @param id the id of the pucResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/puc-results/{id}")
    public ResponseEntity<Void> deletePucResult(@PathVariable Long id) {
        log.debug("REST request to delete PucResult : {}", id);
        pucResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
