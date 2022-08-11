package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.SslcResultService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.SslcResultDTO;
import com.myriadquest.kreiscms.service.dto.SslcResultCriteria;
import com.myriadquest.kreiscms.service.SslcResultQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.SslcResult}.
 */
@RestController
@RequestMapping("/api")
public class SslcResultResource {

    private final Logger log = LoggerFactory.getLogger(SslcResultResource.class);

    private static final String ENTITY_NAME = "sslcResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SslcResultService sslcResultService;

    private final SslcResultQueryService sslcResultQueryService;

    public SslcResultResource(SslcResultService sslcResultService, SslcResultQueryService sslcResultQueryService) {
        this.sslcResultService = sslcResultService;
        this.sslcResultQueryService = sslcResultQueryService;
    }

    /**
     * {@code POST  /sslc-results} : Create a new sslcResult.
     *
     * @param sslcResultDTO the sslcResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sslcResultDTO, or with status {@code 400 (Bad Request)} if the sslcResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sslc-results")
    public ResponseEntity<SslcResultDTO> createSslcResult(@Valid @RequestBody SslcResultDTO sslcResultDTO) throws URISyntaxException {
        log.debug("REST request to save SslcResult : {}", sslcResultDTO);
        if (sslcResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new sslcResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SslcResultDTO result = sslcResultService.save(sslcResultDTO);
        return ResponseEntity.created(new URI("/api/sslc-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sslc-results} : Updates an existing sslcResult.
     *
     * @param sslcResultDTO the sslcResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sslcResultDTO,
     * or with status {@code 400 (Bad Request)} if the sslcResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sslcResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sslc-results")
    public ResponseEntity<SslcResultDTO> updateSslcResult(@Valid @RequestBody SslcResultDTO sslcResultDTO) throws URISyntaxException {
        log.debug("REST request to update SslcResult : {}", sslcResultDTO);
        if (sslcResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SslcResultDTO result = sslcResultService.save(sslcResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sslcResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sslc-results} : get all the sslcResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sslcResults in body.
     */
    @GetMapping("/sslc-results")
    public ResponseEntity<List<SslcResultDTO>> getAllSslcResults(SslcResultCriteria criteria) {
        log.debug("REST request to get SslcResults by criteria: {}", criteria);
        List<SslcResultDTO> entityList = sslcResultQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /sslc-results/count} : count all the sslcResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sslc-results/count")
    public ResponseEntity<Long> countSslcResults(SslcResultCriteria criteria) {
        log.debug("REST request to count SslcResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(sslcResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sslc-results/:id} : get the "id" sslcResult.
     *
     * @param id the id of the sslcResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sslcResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sslc-results/{id}")
    public ResponseEntity<SslcResultDTO> getSslcResult(@PathVariable Long id) {
        log.debug("REST request to get SslcResult : {}", id);
        Optional<SslcResultDTO> sslcResultDTO = sslcResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sslcResultDTO);
    }

    /**
     * {@code DELETE  /sslc-results/:id} : delete the "id" sslcResult.
     *
     * @param id the id of the sslcResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sslc-results/{id}")
    public ResponseEntity<Void> deleteSslcResult(@PathVariable Long id) {
        log.debug("REST request to delete SslcResult : {}", id);
        sslcResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
