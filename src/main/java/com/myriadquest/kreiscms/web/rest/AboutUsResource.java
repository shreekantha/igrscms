package com.myriadquest.kreiscms.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.AboutUsQueryService;
import com.myriadquest.kreiscms.service.AboutUsService;
import com.myriadquest.kreiscms.service.VisionAndMissionQueryService;
import com.myriadquest.kreiscms.service.dto.AboutUsCriteria;
import com.myriadquest.kreiscms.service.dto.AboutUsDTO;
import com.myriadquest.kreiscms.service.dto.AboutUsVmDTO;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionCriteria;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.AboutUs}.
 */
@RestController
@RequestMapping("/api")
public class AboutUsResource {

	private final Logger log = LoggerFactory.getLogger(AboutUsResource.class);

	private static final String ENTITY_NAME = "aboutUs";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final AboutUsService aboutUsService;

	private final AboutUsQueryService aboutUsQueryService;
	@Autowired
	private VisionAndMissionQueryService visionAndMissionQueryService;

	public AboutUsResource(AboutUsService aboutUsService, AboutUsQueryService aboutUsQueryService) {
		this.aboutUsService = aboutUsService;
		this.aboutUsQueryService = aboutUsQueryService;
	}

	/**
	 * {@code POST  /aboutuses} : Create a new aboutUs.
	 *
	 * @param aboutUsDTO the aboutUsDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new aboutUsDTO, or with status {@code 400 (Bad Request)} if
	 *         the aboutUs has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/aboutuses")
	public ResponseEntity<AboutUsDTO> createAboutUs(@Valid @RequestBody AboutUsDTO aboutUsDTO)
			throws URISyntaxException {
		log.debug("REST request to save AboutUs : {}", aboutUsDTO);
		if (aboutUsDTO.getId() != null) {
			throw new BadRequestAlertException("A new aboutUs cannot already have an ID", ENTITY_NAME, "idexists");
		}
		AboutUsDTO result = aboutUsService.save(aboutUsDTO);
		return ResponseEntity
				.created(new URI("/api/aboutuses/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /aboutuses} : Updates an existing aboutUs.
	 *
	 * @param aboutUsDTO the aboutUsDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated aboutUsDTO, or with status {@code 400 (Bad Request)} if
	 *         the aboutUsDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the aboutUsDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/aboutuses")
	public ResponseEntity<AboutUsDTO> updateAboutUs(@Valid @RequestBody AboutUsDTO aboutUsDTO)
			throws URISyntaxException {
		log.debug("REST request to update AboutUs : {}", aboutUsDTO);
		if (aboutUsDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		AboutUsDTO result = aboutUsService.save(aboutUsDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aboutUsDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /aboutuses} : get all the aboutuses.
	 *
	 * @param pageable the pagination information.
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of aboutuses in body.
	 */
	@GetMapping("/aboutuses")
	public ResponseEntity<List<AboutUsDTO>> getAllAboutuses(AboutUsCriteria criteria, Pageable pageable) {
		criteria.setTenantId(IgrscmsApp.getTenantFilter());
		log.debug("REST request to get Aboutuses by criteria: {}", criteria);
		Page<AboutUsDTO> page = aboutUsQueryService.findByCriteria(criteria, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/aboutuses/vm")
	public AboutUsVmDTO getAboutusAndVM(AboutUsCriteria criteria, Pageable pageable) {
		criteria.setTenantId(IgrscmsApp.getTenantFilter());
		VisionAndMissionCriteria vmCriteria = new VisionAndMissionCriteria();
		vmCriteria.setTenantId(IgrscmsApp.getTenantFilter());
		log.debug("REST request to get Aboutuses by criteria: {}", criteria);
		List<AboutUsDTO> page = aboutUsQueryService.findByCriteria(criteria);
		List<VisionAndMissionDTO> vms = visionAndMissionQueryService.findByCriteria(vmCriteria);

		AboutUsVmDTO aboutUsVmDTO=new AboutUsVmDTO();
		
		aboutUsVmDTO.setAboutUsDTOs(page);
		aboutUsVmDTO.setVms(vms);
		
		return aboutUsVmDTO;
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /aboutuses/count} : count all the aboutuses.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/aboutuses/count")
	public ResponseEntity<Long> countAboutuses(AboutUsCriteria criteria) {
		log.debug("REST request to count Aboutuses by criteria: {}", criteria);
		return ResponseEntity.ok().body(aboutUsQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /aboutuses/:id} : get the "id" aboutUs.
	 *
	 * @param id the id of the aboutUsDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the aboutUsDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/aboutuses/{id}")
	public ResponseEntity<AboutUsDTO> getAboutUs(@PathVariable Long id) {
		log.debug("REST request to get AboutUs : {}", id);
		Optional<AboutUsDTO> aboutUsDTO = aboutUsService.findOne(id);
		return ResponseUtil.wrapOrNotFound(aboutUsDTO);
	}

	/**
	 * {@code DELETE  /aboutuses/:id} : delete the "id" aboutUs.
	 *
	 * @param id the id of the aboutUsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/aboutuses/{id}")
	public ResponseEntity<Void> deleteAboutUs(@PathVariable Long id) {
		log.debug("REST request to delete AboutUs : {}", id);
		aboutUsService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}
}
