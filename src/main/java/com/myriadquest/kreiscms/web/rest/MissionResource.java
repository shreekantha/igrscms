package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.MissionService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.MissionDTO;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Mission}.
 */
@RestController
@RequestMapping("/api")
public class MissionResource {

    private final Logger log = LoggerFactory.getLogger(MissionResource.class);

    private static final String ENTITY_NAME = "mission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MissionService missionService;

    public MissionResource(MissionService missionService) {
        this.missionService = missionService;
    }

    /**
     * {@code POST  /missions} : Create a new mission.
     *
     * @param missionDTO the missionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new missionDTO, or with status {@code 400 (Bad Request)} if the mission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/missions")
    public ResponseEntity<MissionDTO> createMission(@Valid @RequestBody MissionDTO missionDTO) throws URISyntaxException {
        log.debug("REST request to save Mission : {}", missionDTO);
        if (missionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MissionDTO result = missionService.save(missionDTO);
        return ResponseEntity.created(new URI("/api/missions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /missions} : Updates an existing mission.
     *
     * @param missionDTO the missionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missionDTO,
     * or with status {@code 400 (Bad Request)} if the missionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the missionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/missions")
    public ResponseEntity<MissionDTO> updateMission(@Valid @RequestBody MissionDTO missionDTO) throws URISyntaxException {
        log.debug("REST request to update Mission : {}", missionDTO);
        if (missionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MissionDTO result = missionService.save(missionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, missionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /missions} : get all the missions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of missions in body.
     */
    @GetMapping("/missions")
    public ResponseEntity<List<MissionDTO>> getAllMissions(Pageable pageable) {
        log.debug("REST request to get a page of Missions");
        Page<MissionDTO> page = missionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /missions/:id} : get the "id" mission.
     *
     * @param id the id of the missionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the missionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/missions/{id}")
    public ResponseEntity<MissionDTO> getMission(@PathVariable Long id) {
        log.debug("REST request to get Mission : {}", id);
        Optional<MissionDTO> missionDTO = missionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(missionDTO);
    }

    /**
     * {@code DELETE  /missions/:id} : delete the "id" mission.
     *
     * @param id the id of the missionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/missions/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        log.debug("REST request to delete Mission : {}", id);
        missionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
