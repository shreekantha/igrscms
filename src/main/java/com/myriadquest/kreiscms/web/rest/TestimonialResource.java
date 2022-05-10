package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.TestimonialService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.TestimonialDTO;
import com.myriadquest.kreiscms.service.dto.TestimonialCriteria;
import com.myriadquest.kreiscms.service.TestimonialQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.Testimonial}.
 */
@RestController
@RequestMapping("/api")
public class TestimonialResource {

    private final Logger log = LoggerFactory.getLogger(TestimonialResource.class);

    private static final String ENTITY_NAME = "testimonial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestimonialService testimonialService;

    private final TestimonialQueryService testimonialQueryService;

    public TestimonialResource(TestimonialService testimonialService, TestimonialQueryService testimonialQueryService) {
        this.testimonialService = testimonialService;
        this.testimonialQueryService = testimonialQueryService;
    }

    /**
     * {@code POST  /testimonials} : Create a new testimonial.
     *
     * @param testimonialDTO the testimonialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testimonialDTO, or with status {@code 400 (Bad Request)} if the testimonial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/testimonials")
    public ResponseEntity<TestimonialDTO> createTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) throws URISyntaxException {
        log.debug("REST request to save Testimonial : {}", testimonialDTO);
        if (testimonialDTO.getId() != null) {
            throw new BadRequestAlertException("A new testimonial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestimonialDTO result = testimonialService.save(testimonialDTO);
        return ResponseEntity.created(new URI("/api/testimonials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /testimonials} : Updates an existing testimonial.
     *
     * @param testimonialDTO the testimonialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testimonialDTO,
     * or with status {@code 400 (Bad Request)} if the testimonialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testimonialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/testimonials")
    public ResponseEntity<TestimonialDTO> updateTestimonial(@Valid @RequestBody TestimonialDTO testimonialDTO) throws URISyntaxException {
        log.debug("REST request to update Testimonial : {}", testimonialDTO);
        if (testimonialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestimonialDTO result = testimonialService.save(testimonialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testimonialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /testimonials} : get all the testimonials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testimonials in body.
     */
    @GetMapping("/testimonials")
    public ResponseEntity<List<TestimonialDTO>> getAllTestimonials(TestimonialCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Testimonials by criteria: {}", criteria);
        Page<TestimonialDTO> page = testimonialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /testimonials/count} : count all the testimonials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/testimonials/count")
    public ResponseEntity<Long> countTestimonials(TestimonialCriteria criteria) {
        log.debug("REST request to count Testimonials by criteria: {}", criteria);
        return ResponseEntity.ok().body(testimonialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /testimonials/:id} : get the "id" testimonial.
     *
     * @param id the id of the testimonialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testimonialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/testimonials/{id}")
    public ResponseEntity<TestimonialDTO> getTestimonial(@PathVariable Long id) {
        log.debug("REST request to get Testimonial : {}", id);
        Optional<TestimonialDTO> testimonialDTO = testimonialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testimonialDTO);
    }

    /**
     * {@code DELETE  /testimonials/:id} : delete the "id" testimonial.
     *
     * @param id the id of the testimonialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/testimonials/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        log.debug("REST request to delete Testimonial : {}", id);
        testimonialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
