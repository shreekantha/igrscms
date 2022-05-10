package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.VisionAndMissionService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.VisionAndMissionQueryService;

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
 * REST controller for managing
 * {@link com.myriadquest.kreiscms.domain.VisionAndMission}.
 */
@RestController
@RequestMapping("/api")
public class VisionAndMissionResource {

	private final Logger log = LoggerFactory.getLogger(VisionAndMissionResource.class);

	private static final String ENTITY_NAME = "visionAndMission";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final VisionAndMissionService visionAndMissionService;

	private final VisionAndMissionQueryService visionAndMissionQueryService;

	public VisionAndMissionResource(VisionAndMissionService visionAndMissionService,
			VisionAndMissionQueryService visionAndMissionQueryService) {
		this.visionAndMissionService = visionAndMissionService;
		this.visionAndMissionQueryService = visionAndMissionQueryService;
	}

	/**
	 * {@code POST  /vision-and-missions} : Create a new visionAndMission.
	 *
	 * @param visionAndMissionDTO the visionAndMissionDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new visionAndMissionDTO, or with status
	 *         {@code 400 (Bad Request)} if the visionAndMission has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/vision-and-missions")
	public ResponseEntity<VisionAndMissionDTO> createVisionAndMission(
			@Valid @RequestBody VisionAndMissionDTO visionAndMissionDTO) throws URISyntaxException {
		log.debug("REST request to save VisionAndMission : {}", visionAndMissionDTO);
		if (visionAndMissionDTO.getId() != null) {
			throw new BadRequestAlertException("A new visionAndMission cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		VisionAndMissionDTO result = visionAndMissionService.save(visionAndMissionDTO);
		return ResponseEntity
				.created(new URI("/api/vision-and-missions/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /vision-and-missions} : Updates an existing visionAndMission.
	 *
	 * @param visionAndMissionDTO the visionAndMissionDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated visionAndMissionDTO, or with status
	 *         {@code 400 (Bad Request)} if the visionAndMissionDTO is not valid, or
	 *         with status {@code 500 (Internal Server Error)} if the
	 *         visionAndMissionDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/vision-and-missions")
	public ResponseEntity<VisionAndMissionDTO> updateVisionAndMission(
			@Valid @RequestBody VisionAndMissionDTO visionAndMissionDTO) throws URISyntaxException {
		log.debug("REST request to update VisionAndMission : {}", visionAndMissionDTO);
		if (visionAndMissionDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		VisionAndMissionDTO result = visionAndMissionService.save(visionAndMissionDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
				visionAndMissionDTO.getId().toString())).body(result);
	}

	/**
	 * {@code GET  /vision-and-missions} : get all the visionAndMissions.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of visionAndMissions in body.
	 */
	@GetMapping("/vision-and-missions")
	public ResponseEntity<List<VisionAndMissionDTO>> getAllVisionAndMissions(VisionAndMissionCriteria criteria,
			Pageable pageable) {
		criteria.setTenantId(IgrscmsApp.getTenantFilter());
		log.debug("REST request to get VisionAndMissions by criteria: {}", criteria);
		Page<VisionAndMissionDTO> page = visionAndMissionQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /vision-and-missions/count} : count all the visionAndMissions.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/vision-and-missions/count")
	public ResponseEntity<Long> countVisionAndMissions(VisionAndMissionCriteria criteria) {
		log.debug("REST request to count VisionAndMissions by criteria: {}", criteria);
		return ResponseEntity.ok().body(visionAndMissionQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /vision-and-missions/:id} : get the "id" visionAndMission.
	 *
	 * @param id the id of the visionAndMissionDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the visionAndMissionDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/vision-and-missions/{id}")
	public ResponseEntity<VisionAndMissionDTO> getVisionAndMission(@PathVariable Long id) {
		log.debug("REST request to get VisionAndMission : {}", id);
		Optional<VisionAndMissionDTO> visionAndMissionDTO = visionAndMissionService.findOne(id);
		return ResponseUtil.wrapOrNotFound(visionAndMissionDTO);
	}

	/**
	 * {@code DELETE  /vision-and-missions/:id} : delete the "id" visionAndMission.
	 *
	 * @param id the id of the visionAndMissionDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/vision-and-missions/{id}")
	public ResponseEntity<Void> deleteVisionAndMission(@PathVariable Long id) {
		log.debug("REST request to delete VisionAndMission : {}", id);
		visionAndMissionService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
