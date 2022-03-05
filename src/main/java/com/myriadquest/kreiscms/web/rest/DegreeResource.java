package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.DegreeService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.DegreeDTO;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Degree}.
 */
@RestController
@RequestMapping("/api")
public class DegreeResource {

    private final Logger log = LoggerFactory.getLogger(DegreeResource.class);

    private static final String ENTITY_NAME = "degree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DegreeService degreeService;

    public DegreeResource(DegreeService degreeService) {
        this.degreeService = degreeService;
    }

    /**
     * {@code POST  /degrees} : Create a new degree.
     *
     * @param degreeDTO the degreeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new degreeDTO, or with status {@code 400 (Bad Request)} if the degree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/degrees")
    public ResponseEntity<DegreeDTO> createDegree(@Valid @RequestBody DegreeDTO degreeDTO) throws URISyntaxException {
        log.debug("REST request to save Degree : {}", degreeDTO);
        if (degreeDTO.getId() != null) {
            throw new BadRequestAlertException("A new degree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DegreeDTO result = degreeService.save(degreeDTO);
        return ResponseEntity.created(new URI("/api/degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /degrees} : Updates an existing degree.
     *
     * @param degreeDTO the degreeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated degreeDTO,
     * or with status {@code 400 (Bad Request)} if the degreeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the degreeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/degrees")
    public ResponseEntity<DegreeDTO> updateDegree(@Valid @RequestBody DegreeDTO degreeDTO) throws URISyntaxException {
        log.debug("REST request to update Degree : {}", degreeDTO);
        if (degreeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DegreeDTO result = degreeService.save(degreeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, degreeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /degrees} : get all the degrees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of degrees in body.
     */
    @GetMapping("/degrees")
    public ResponseEntity<List<DegreeDTO>> getAllDegrees(Pageable pageable) {
        log.debug("REST request to get a page of Degrees");
        Page<DegreeDTO> page = degreeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /degrees/:id} : get the "id" degree.
     *
     * @param id the id of the degreeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the degreeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/degrees/{id}")
    public ResponseEntity<DegreeDTO> getDegree(@PathVariable Long id) {
        log.debug("REST request to get Degree : {}", id);
        Optional<DegreeDTO> degreeDTO = degreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(degreeDTO);
    }

    /**
     * {@code DELETE  /degrees/:id} : delete the "id" degree.
     *
     * @param id the id of the degreeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/degrees/{id}")
    public ResponseEntity<Void> deleteDegree(@PathVariable Long id) {
        log.debug("REST request to delete Degree : {}", id);
        degreeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
