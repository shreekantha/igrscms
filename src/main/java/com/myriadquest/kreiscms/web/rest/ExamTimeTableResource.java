package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.ExamTimeTableService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.service.ExamTimeTableQueryService;

import io.github.jhipster.service.filter.StringFilter;
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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.ExamTimeTable}.
 */
@RestController
@RequestMapping("/api")
public class ExamTimeTableResource {

    private final Logger log = LoggerFactory.getLogger(ExamTimeTableResource.class);

    private static final String ENTITY_NAME = "examTimeTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamTimeTableService examTimeTableService;

    private final ExamTimeTableQueryService examTimeTableQueryService;

    public ExamTimeTableResource(ExamTimeTableService examTimeTableService, ExamTimeTableQueryService examTimeTableQueryService) {
        this.examTimeTableService = examTimeTableService;
        this.examTimeTableQueryService = examTimeTableQueryService;
    }

    /**
     * {@code POST  /exam-time-tables} : Create a new examTimeTable.
     *
     * @param examTimeTableDTO the examTimeTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examTimeTableDTO, or with status {@code 400 (Bad Request)} if the examTimeTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-time-tables")
    public ResponseEntity<ExamTimeTableDTO> createExamTimeTable(@Valid @RequestBody ExamTimeTableDTO examTimeTableDTO) throws URISyntaxException {
        log.debug("REST request to save ExamTimeTable : {}", examTimeTableDTO);
        if (examTimeTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new examTimeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamTimeTableDTO result = examTimeTableService.save(examTimeTableDTO);
        return ResponseEntity.created(new URI("/api/exam-time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-time-tables} : Updates an existing examTimeTable.
     *
     * @param examTimeTableDTO the examTimeTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examTimeTableDTO,
     * or with status {@code 400 (Bad Request)} if the examTimeTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examTimeTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-time-tables")
    public ResponseEntity<ExamTimeTableDTO> updateExamTimeTable(@Valid @RequestBody ExamTimeTableDTO examTimeTableDTO) throws URISyntaxException {
        log.debug("REST request to update ExamTimeTable : {}", examTimeTableDTO);
        if (examTimeTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamTimeTableDTO result = examTimeTableService.save(examTimeTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examTimeTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exam-time-tables} : get all the examTimeTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examTimeTables in body.
     */
    @GetMapping("/exam-time-tables")
    public ResponseEntity<List<ExamTimeTableDTO>> getAllExamTimeTables(ExamTimeTableCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get ExamTimeTables by criteria: {}", criteria);
        Page<ExamTimeTableDTO> page = examTimeTableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exam-time-tables/count} : count all the examTimeTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/exam-time-tables/count")
    public ResponseEntity<Long> countExamTimeTables(ExamTimeTableCriteria criteria) {
        log.debug("REST request to count ExamTimeTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(examTimeTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /exam-time-tables/:id} : get the "id" examTimeTable.
     *
     * @param id the id of the examTimeTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examTimeTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-time-tables/{id}")
    public ResponseEntity<ExamTimeTableDTO> getExamTimeTable(@PathVariable Long id) {
        log.debug("REST request to get ExamTimeTable : {}", id);
        Optional<ExamTimeTableDTO> examTimeTableDTO = examTimeTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examTimeTableDTO);
    }

    /**
     * {@code DELETE  /exam-time-tables/:id} : delete the "id" examTimeTable.
     *
     * @param id the id of the examTimeTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-time-tables/{id}")
    public ResponseEntity<Void> deleteExamTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete ExamTimeTable : {}", id);
        examTimeTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
