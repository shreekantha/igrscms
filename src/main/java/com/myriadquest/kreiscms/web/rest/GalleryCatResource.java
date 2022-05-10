package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.GalleryCatService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;
import com.myriadquest.kreiscms.service.dto.GalleryCatCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.GalleryCatQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.GalleryCat}.
 */
@RestController
@RequestMapping("/api")
public class GalleryCatResource {

    private final Logger log = LoggerFactory.getLogger(GalleryCatResource.class);

    private static final String ENTITY_NAME = "galleryCat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GalleryCatService galleryCatService;

    private final GalleryCatQueryService galleryCatQueryService;

    public GalleryCatResource(GalleryCatService galleryCatService, GalleryCatQueryService galleryCatQueryService) {
        this.galleryCatService = galleryCatService;
        this.galleryCatQueryService = galleryCatQueryService;
    }

    /**
     * {@code POST  /gallery-cats} : Create a new galleryCat.
     *
     * @param galleryCatDTO the galleryCatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galleryCatDTO, or with status {@code 400 (Bad Request)} if the galleryCat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gallery-cats")
    public ResponseEntity<GalleryCatDTO> createGalleryCat(@Valid @RequestBody GalleryCatDTO galleryCatDTO) throws URISyntaxException {
        log.debug("REST request to save GalleryCat : {}", galleryCatDTO);
        if (galleryCatDTO.getId() != null) {
            throw new BadRequestAlertException("A new galleryCat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GalleryCatDTO result = galleryCatService.save(galleryCatDTO);
        return ResponseEntity.created(new URI("/api/gallery-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gallery-cats} : Updates an existing galleryCat.
     *
     * @param galleryCatDTO the galleryCatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galleryCatDTO,
     * or with status {@code 400 (Bad Request)} if the galleryCatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galleryCatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gallery-cats")
    public ResponseEntity<GalleryCatDTO> updateGalleryCat(@Valid @RequestBody GalleryCatDTO galleryCatDTO) throws URISyntaxException {
        log.debug("REST request to update GalleryCat : {}", galleryCatDTO);
        if (galleryCatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GalleryCatDTO result = galleryCatService.save(galleryCatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galleryCatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gallery-cats} : get all the galleryCats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galleryCats in body.
     */
    @GetMapping("/gallery-cats")
    public ResponseEntity<List<GalleryCatDTO>> getAllGalleryCats(GalleryCatCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get GalleryCats by criteria: {}", criteria);
        Page<GalleryCatDTO> page = galleryCatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gallery-cats/count} : count all the galleryCats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gallery-cats/count")
    public ResponseEntity<Long> countGalleryCats(GalleryCatCriteria criteria) {
        log.debug("REST request to count GalleryCats by criteria: {}", criteria);
        return ResponseEntity.ok().body(galleryCatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gallery-cats/:id} : get the "id" galleryCat.
     *
     * @param id the id of the galleryCatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galleryCatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gallery-cats/{id}")
    public ResponseEntity<GalleryCatDTO> getGalleryCat(@PathVariable Long id) {
        log.debug("REST request to get GalleryCat : {}", id);
        Optional<GalleryCatDTO> galleryCatDTO = galleryCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galleryCatDTO);
    }

    /**
     * {@code DELETE  /gallery-cats/:id} : delete the "id" galleryCat.
     *
     * @param id the id of the galleryCatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gallery-cats/{id}")
    public ResponseEntity<Void> deleteGalleryCat(@PathVariable Long id) {
        log.debug("REST request to delete GalleryCat : {}", id);
        galleryCatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
