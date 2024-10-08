package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.domain.enumeration.UserType;
import com.myriadquest.kreiscms.service.ProfileService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.ProfileDTO;

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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Profile}.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private static final String ENTITY_NAME = "profile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfileService profileService;

    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * {@code POST  /profiles} : Create a new profile.
     *
     * @param profileDTO the profileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profileDTO, or with status {@code 400 (Bad Request)} if the profile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profiles")
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            throw new BadRequestAlertException("A new profile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profiles} : Updates an existing profile.
     *
     * @param profileDTO the profileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profileDTO,
     * or with status {@code 400 (Bad Request)} if the profileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profiles")
    public ResponseEntity<ProfileDTO> updateProfile(@Valid @RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProfileDTO result = profileService.save(profileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profiles} : get all the profiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profiles in body.
     */
    @GetMapping("/profiles")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(Pageable pageable) {
        log.debug("REST request to get a page of Profiles");
        Page<ProfileDTO> page = profileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/profiles/designation")
	public Map<UserType, List<ProfileDTO>> getAllProfilesByDesignation(Pageable pageable) {
		log.debug("REST request to get a page of Profiles");
		Page<ProfileDTO> page = profileService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

		return page.getContent().stream().collect(Collectors.groupingBy(ProfileDTO::getUserType));
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

    /**
     * {@code GET  /profiles/:id} : get the "id" profile.
     *
     * @param id the id of the profileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Optional<ProfileDTO> profileDTO = profileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profileDTO);
    }

    /**
     * {@code DELETE  /profiles/:id} : delete the "id" profile.
     *
     * @param id the id of the profileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profiles/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
