package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.AlumniProfileService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;
import com.myriadquest.kreiscms.service.dto.AlumniProfileCriteria;
import com.myriadquest.kreiscms.service.AlumniProfileQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.AlumniProfile}.
 */
@RestController
@RequestMapping("/api")
public class AlumniProfileResource {

    private final Logger log = LoggerFactory.getLogger(AlumniProfileResource.class);

    private static final String ENTITY_NAME = "alumniProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlumniProfileService alumniProfileService;

    private final AlumniProfileQueryService alumniProfileQueryService;

    public AlumniProfileResource(AlumniProfileService alumniProfileService, AlumniProfileQueryService alumniProfileQueryService) {
        this.alumniProfileService = alumniProfileService;
        this.alumniProfileQueryService = alumniProfileQueryService;
    }

    /**
     * {@code POST  /alumni-profiles} : Create a new alumniProfile.
     *
     * @param alumniProfileDTO the alumniProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alumniProfileDTO, or with status {@code 400 (Bad Request)} if the alumniProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alumni-profiles")
    public ResponseEntity<AlumniProfileDTO> createAlumniProfile(@Valid @RequestBody AlumniProfileDTO alumniProfileDTO) throws URISyntaxException {
        log.debug("REST request to save AlumniProfile : {}", alumniProfileDTO);
        if (alumniProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new alumniProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlumniProfileDTO result = alumniProfileService.save(alumniProfileDTO);
        return ResponseEntity.created(new URI("/api/alumni-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alumni-profiles} : Updates an existing alumniProfile.
     *
     * @param alumniProfileDTO the alumniProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alumniProfileDTO,
     * or with status {@code 400 (Bad Request)} if the alumniProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alumniProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alumni-profiles")
    public ResponseEntity<AlumniProfileDTO> updateAlumniProfile(@Valid @RequestBody AlumniProfileDTO alumniProfileDTO) throws URISyntaxException {
        log.debug("REST request to update AlumniProfile : {}", alumniProfileDTO);
        if (alumniProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AlumniProfileDTO result = alumniProfileService.save(alumniProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alumniProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /alumni-profiles} : get all the alumniProfiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alumniProfiles in body.
     */
    @GetMapping("/alumni-profiles")
    public ResponseEntity<List<AlumniProfileDTO>> getAllAlumniProfiles(AlumniProfileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AlumniProfiles by criteria: {}", criteria);
        Page<AlumniProfileDTO> page = alumniProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /alumni-profiles/count} : count all the alumniProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/alumni-profiles/count")
    public ResponseEntity<Long> countAlumniProfiles(AlumniProfileCriteria criteria) {
        log.debug("REST request to count AlumniProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(alumniProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /alumni-profiles/:id} : get the "id" alumniProfile.
     *
     * @param id the id of the alumniProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alumniProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alumni-profiles/{id}")
    public ResponseEntity<AlumniProfileDTO> getAlumniProfile(@PathVariable Long id) {
        log.debug("REST request to get AlumniProfile : {}", id);
        Optional<AlumniProfileDTO> alumniProfileDTO = alumniProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alumniProfileDTO);
    }

    /**
     * {@code DELETE  /alumni-profiles/:id} : delete the "id" alumniProfile.
     *
     * @param id the id of the alumniProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alumni-profiles/{id}")
    public ResponseEntity<Void> deleteAlumniProfile(@PathVariable Long id) {
        log.debug("REST request to delete AlumniProfile : {}", id);
        alumniProfileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
