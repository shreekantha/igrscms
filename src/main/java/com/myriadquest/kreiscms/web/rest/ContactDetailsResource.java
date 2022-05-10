package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.ContactDetailsService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;
import com.myriadquest.kreiscms.service.dto.ContactDetailsCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.ContactDetailsQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.ContactDetails}.
 */
@RestController
@RequestMapping("/api")
public class ContactDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsResource.class);

    private static final String ENTITY_NAME = "contactDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactDetailsService contactDetailsService;

    private final ContactDetailsQueryService contactDetailsQueryService;

    public ContactDetailsResource(ContactDetailsService contactDetailsService, ContactDetailsQueryService contactDetailsQueryService) {
        this.contactDetailsService = contactDetailsService;
        this.contactDetailsQueryService = contactDetailsQueryService;
    }

    /**
     * {@code POST  /contact-details} : Create a new contactDetails.
     *
     * @param contactDetailsDTO the contactDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactDetailsDTO, or with status {@code 400 (Bad Request)} if the contactDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-details")
    public ResponseEntity<ContactDetailsDTO> createContactDetails(@Valid @RequestBody ContactDetailsDTO contactDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ContactDetails : {}", contactDetailsDTO);
        if (contactDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactDetailsDTO result = contactDetailsService.save(contactDetailsDTO);
        return ResponseEntity.created(new URI("/api/contact-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-details} : Updates an existing contactDetails.
     *
     * @param contactDetailsDTO the contactDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the contactDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-details")
    public ResponseEntity<ContactDetailsDTO> updateContactDetails(@Valid @RequestBody ContactDetailsDTO contactDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ContactDetails : {}", contactDetailsDTO);
        if (contactDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactDetailsDTO result = contactDetailsService.save(contactDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contact-details} : get all the contactDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactDetails in body.
     */
    @GetMapping("/contact-details")
    public ResponseEntity<List<ContactDetailsDTO>> getAllContactDetails(ContactDetailsCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get ContactDetails by criteria: {}", criteria);
        Page<ContactDetailsDTO> page = contactDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-details/count} : count all the contactDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-details/count")
    public ResponseEntity<Long> countContactDetails(ContactDetailsCriteria criteria) {
        log.debug("REST request to count ContactDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-details/:id} : get the "id" contactDetails.
     *
     * @param id the id of the contactDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-details/{id}")
    public ResponseEntity<ContactDetailsDTO> getContactDetails(@PathVariable Long id) {
        log.debug("REST request to get ContactDetails : {}", id);
        Optional<ContactDetailsDTO> contactDetailsDTO = contactDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactDetailsDTO);
    }

    /**
     * {@code DELETE  /contact-details/:id} : delete the "id" contactDetails.
     *
     * @param id the id of the contactDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-details/{id}")
    public ResponseEntity<Void> deleteContactDetails(@PathVariable Long id) {
        log.debug("REST request to delete ContactDetails : {}", id);
        contactDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
