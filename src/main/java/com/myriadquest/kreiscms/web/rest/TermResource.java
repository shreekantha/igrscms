package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.TermService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.TermDTO;
import com.myriadquest.kreiscms.service.dto.TermCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.service.TermQueryService;

import io.github.jhipster.service.filter.StringFilter;
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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Term}.
 */
@RestController
@RequestMapping("/api")
public class TermResource {

    private final Logger log = LoggerFactory.getLogger(TermResource.class);

    private static final String ENTITY_NAME = "term";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermService termService;

    private final TermQueryService termQueryService;

    public TermResource(TermService termService, TermQueryService termQueryService) {
        this.termService = termService;
        this.termQueryService = termQueryService;
    }

    /**
     * {@code POST  /terms} : Create a new term.
     *
     * @param termDTO the termDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termDTO, or with status {@code 400 (Bad Request)} if the term has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/terms")
    public ResponseEntity<TermDTO> createTerm(@Valid @RequestBody TermDTO termDTO) throws URISyntaxException {
        log.debug("REST request to save Term : {}", termDTO);
        if (termDTO.getId() != null) {
            throw new BadRequestAlertException("A new term cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TermDTO result = termService.save(termDTO);
        return ResponseEntity.created(new URI("/api/terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /terms} : Updates an existing term.
     *
     * @param termDTO the termDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termDTO,
     * or with status {@code 400 (Bad Request)} if the termDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/terms")
    public ResponseEntity<TermDTO> updateTerm(@Valid @RequestBody TermDTO termDTO) throws URISyntaxException {
        log.debug("REST request to update Term : {}", termDTO);
        if (termDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TermDTO result = termService.save(termDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /terms} : get all the terms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of terms in body.
     */
    @GetMapping("/terms")
    public ResponseEntity<List<TermDTO>> getAllTerms(TermCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
        log.debug("REST request to get Terms by criteria: {}", criteria);
        Page<TermDTO> page = termQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terms/count} : count all the terms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/terms/count")
    public ResponseEntity<Long> countTerms(TermCriteria criteria) {
        log.debug("REST request to count Terms by criteria: {}", criteria);
        return ResponseEntity.ok().body(termQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /terms/:id} : get the "id" term.
     *
     * @param id the id of the termDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/terms/{id}")
    public ResponseEntity<TermDTO> getTerm(@PathVariable Long id) {
        log.debug("REST request to get Term : {}", id);
        Optional<TermDTO> termDTO = termService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termDTO);
    }

    /**
     * {@code DELETE  /terms/:id} : delete the "id" term.
     *
     * @param id the id of the termDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/terms/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        log.debug("REST request to delete Term : {}", id);
        termService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
