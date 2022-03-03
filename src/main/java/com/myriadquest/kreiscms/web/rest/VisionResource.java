package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.VisionService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.VisionDTO;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Vision}.
 */
@RestController
@RequestMapping("/api")
public class VisionResource {

    private final Logger log = LoggerFactory.getLogger(VisionResource.class);

    private static final String ENTITY_NAME = "vision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisionService visionService;

    public VisionResource(VisionService visionService) {
        this.visionService = visionService;
    }

    /**
     * {@code POST  /visions} : Create a new vision.
     *
     * @param visionDTO the visionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visionDTO, or with status {@code 400 (Bad Request)} if the vision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visions")
    public ResponseEntity<VisionDTO> createVision(@Valid @RequestBody VisionDTO visionDTO) throws URISyntaxException {
        log.debug("REST request to save Vision : {}", visionDTO);
        if (visionDTO.getId() != null) {
            throw new BadRequestAlertException("A new vision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisionDTO result = visionService.save(visionDTO);
        return ResponseEntity.created(new URI("/api/visions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visions} : Updates an existing vision.
     *
     * @param visionDTO the visionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visionDTO,
     * or with status {@code 400 (Bad Request)} if the visionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visions")
    public ResponseEntity<VisionDTO> updateVision(@Valid @RequestBody VisionDTO visionDTO) throws URISyntaxException {
        log.debug("REST request to update Vision : {}", visionDTO);
        if (visionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VisionDTO result = visionService.save(visionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visions} : get all the visions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visions in body.
     */
    @GetMapping("/visions")
    public ResponseEntity<List<VisionDTO>> getAllVisions(Pageable pageable) {
        log.debug("REST request to get a page of Visions");
        Page<VisionDTO> page = visionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visions/:id} : get the "id" vision.
     *
     * @param id the id of the visionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visions/{id}")
    public ResponseEntity<VisionDTO> getVision(@PathVariable Long id) {
        log.debug("REST request to get Vision : {}", id);
        Optional<VisionDTO> visionDTO = visionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visionDTO);
    }

    /**
     * {@code DELETE  /visions/:id} : delete the "id" vision.
     *
     * @param id the id of the visionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visions/{id}")
    public ResponseEntity<Void> deleteVision(@PathVariable Long id) {
        log.debug("REST request to delete Vision : {}", id);
        visionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
