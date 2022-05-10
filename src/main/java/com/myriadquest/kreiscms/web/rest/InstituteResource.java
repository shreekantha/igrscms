package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.InstituteService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;
import com.myriadquest.kreiscms.service.dto.InstituteCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.InstituteQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Institute}.
 */
@RestController
@RequestMapping("/api")
public class InstituteResource {

    private final Logger log = LoggerFactory.getLogger(InstituteResource.class);

    private static final String ENTITY_NAME = "institute";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstituteService instituteService;

    private final InstituteQueryService instituteQueryService;

    public InstituteResource(InstituteService instituteService, InstituteQueryService instituteQueryService) {
        this.instituteService = instituteService;
        this.instituteQueryService = instituteQueryService;
    }

    /**
     * {@code POST  /institutes} : Create a new institute.
     *
     * @param instituteDTO the instituteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instituteDTO, or with status {@code 400 (Bad Request)} if the institute has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institutes")
    public ResponseEntity<InstituteDTO> createInstitute(@Valid @RequestBody InstituteDTO instituteDTO) throws URISyntaxException {
        log.debug("REST request to save Institute : {}", instituteDTO);
        if (instituteDTO.getId() != null) {
            throw new BadRequestAlertException("A new institute cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstituteDTO result = instituteService.save(instituteDTO);
        return ResponseEntity.created(new URI("/api/institutes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institutes} : Updates an existing institute.
     *
     * @param instituteDTO the instituteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instituteDTO,
     * or with status {@code 400 (Bad Request)} if the instituteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instituteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institutes")
    public ResponseEntity<InstituteDTO> updateInstitute(@Valid @RequestBody InstituteDTO instituteDTO) throws URISyntaxException {
        log.debug("REST request to update Institute : {}", instituteDTO);
        if (instituteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstituteDTO result = instituteService.save(instituteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, instituteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institutes} : get all the institutes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutes in body.
     */
    @GetMapping("/institutes")
    public ResponseEntity<List<InstituteDTO>> getAllInstitutes(InstituteCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get Institutes by criteria: {}", criteria);
        Page<InstituteDTO> page = instituteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /institutes/count} : count all the institutes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/institutes/count")
    public ResponseEntity<Long> countInstitutes(InstituteCriteria criteria) {
        log.debug("REST request to count Institutes by criteria: {}", criteria);
        return ResponseEntity.ok().body(instituteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /institutes/:id} : get the "id" institute.
     *
     * @param id the id of the instituteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instituteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institutes/{id}")
    public ResponseEntity<InstituteDTO> getInstitute(@PathVariable Long id) {
       
    	log.debug("REST request to get Institute : {}", id);
        Optional<InstituteDTO> instituteDTO = instituteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instituteDTO);
    }

    /**
     * {@code DELETE  /institutes/:id} : delete the "id" institute.
     *
     * @param id the id of the instituteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institutes/{id}")
    public ResponseEntity<Void> deleteInstitute(@PathVariable Long id) {
        log.debug("REST request to delete Institute : {}", id);
        instituteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
