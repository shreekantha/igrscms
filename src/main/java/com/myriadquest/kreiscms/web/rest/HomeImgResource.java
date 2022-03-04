package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.HomeImgService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.HomeImg}.
 */
@RestController
@RequestMapping("/api")
public class HomeImgResource {

    private final Logger log = LoggerFactory.getLogger(HomeImgResource.class);

    private static final String ENTITY_NAME = "homeImg";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HomeImgService homeImgService;

    public HomeImgResource(HomeImgService homeImgService) {
        this.homeImgService = homeImgService;
    }

    /**
     * {@code POST  /home-imgs} : Create a new homeImg.
     *
     * @param homeImgDTO the homeImgDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new homeImgDTO, or with status {@code 400 (Bad Request)} if the homeImg has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/home-imgs")
    public ResponseEntity<HomeImgDTO> createHomeImg(@Valid @RequestBody HomeImgDTO homeImgDTO) throws URISyntaxException {
        log.debug("REST request to save HomeImg : {}", homeImgDTO);
        if (homeImgDTO.getId() != null) {
            throw new BadRequestAlertException("A new homeImg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HomeImgDTO result = homeImgService.save(homeImgDTO);
        return ResponseEntity.created(new URI("/api/home-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /home-imgs} : Updates an existing homeImg.
     *
     * @param homeImgDTO the homeImgDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated homeImgDTO,
     * or with status {@code 400 (Bad Request)} if the homeImgDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the homeImgDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/home-imgs")
    public ResponseEntity<HomeImgDTO> updateHomeImg(@Valid @RequestBody HomeImgDTO homeImgDTO) throws URISyntaxException {
        log.debug("REST request to update HomeImg : {}", homeImgDTO);
        if (homeImgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HomeImgDTO result = homeImgService.save(homeImgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, homeImgDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /home-imgs} : get all the homeImgs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of homeImgs in body.
     */
    @GetMapping("/home-imgs")
    public List<HomeImgDTO> getAllHomeImgs() {
        log.debug("REST request to get all HomeImgs");
        return homeImgService.findAll();
    }

    /**
     * {@code GET  /home-imgs/:id} : get the "id" homeImg.
     *
     * @param id the id of the homeImgDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the homeImgDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/home-imgs/{id}")
    public ResponseEntity<HomeImgDTO> getHomeImg(@PathVariable Long id) {
        log.debug("REST request to get HomeImg : {}", id);
        Optional<HomeImgDTO> homeImgDTO = homeImgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(homeImgDTO);
    }

    /**
     * {@code DELETE  /home-imgs/:id} : delete the "id" homeImg.
     *
     * @param id the id of the homeImgDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/home-imgs/{id}")
    public ResponseEntity<Void> deleteHomeImg(@PathVariable Long id) {
        log.debug("REST request to delete HomeImg : {}", id);
        homeImgService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
