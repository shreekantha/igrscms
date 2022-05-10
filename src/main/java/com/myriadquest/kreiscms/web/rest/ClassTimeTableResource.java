package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.ClassTimeTableService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.domain.enumeration.Day;
import com.myriadquest.kreiscms.service.ClassTimeTableQueryService;

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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.ClassTimeTable}.
 */
@RestController
@RequestMapping("/api")
public class ClassTimeTableResource {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableResource.class);

    private static final String ENTITY_NAME = "classTimeTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassTimeTableService classTimeTableService;

    private final ClassTimeTableQueryService classTimeTableQueryService;

    public ClassTimeTableResource(ClassTimeTableService classTimeTableService, ClassTimeTableQueryService classTimeTableQueryService) {
        this.classTimeTableService = classTimeTableService;
        this.classTimeTableQueryService = classTimeTableQueryService;
    }

    /**
     * {@code POST  /class-time-tables} : Create a new classTimeTable.
     *
     * @param classTimeTableDTO the classTimeTableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classTimeTableDTO, or with status {@code 400 (Bad Request)} if the classTimeTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-time-tables")
    public ResponseEntity<ClassTimeTableDTO> createClassTimeTable(@Valid @RequestBody ClassTimeTableDTO classTimeTableDTO) throws URISyntaxException {
        log.debug("REST request to save ClassTimeTable : {}", classTimeTableDTO);
        if (classTimeTableDTO.getId() != null) {
            throw new BadRequestAlertException("A new classTimeTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassTimeTableDTO result = classTimeTableService.save(classTimeTableDTO);
        return ResponseEntity.created(new URI("/api/class-time-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-time-tables} : Updates an existing classTimeTable.
     *
     * @param classTimeTableDTO the classTimeTableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classTimeTableDTO,
     * or with status {@code 400 (Bad Request)} if the classTimeTableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classTimeTableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-time-tables")
    public ResponseEntity<ClassTimeTableDTO> updateClassTimeTable(@Valid @RequestBody ClassTimeTableDTO classTimeTableDTO) throws URISyntaxException {
        log.debug("REST request to update ClassTimeTable : {}", classTimeTableDTO);
        if (classTimeTableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassTimeTableDTO result = classTimeTableService.save(classTimeTableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classTimeTableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-time-tables} : get all the classTimeTables.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classTimeTables in body.
     */
    @GetMapping("/class-time-tables")
    public ResponseEntity<List<ClassTimeTableDTO>> getAllClassTimeTables(ClassTimeTableCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get ClassTimeTables by criteria: {}", criteria);
        Page<ClassTimeTableDTO> page = classTimeTableQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/class-time-tables/group-by-day")
    public Map<Day, List<ClassTimeTableDTO>> getClassTimeTables(ClassTimeTableCriteria criteria) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get ClassTimeTables by criteria: {}", criteria);
        List<ClassTimeTableDTO> list = classTimeTableQueryService.findByCriteria(criteria);
    return   list.stream().collect(Collectors.groupingBy(ClassTimeTableDTO::getDay));
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /class-time-tables/count} : count all the classTimeTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/class-time-tables/count")
    public ResponseEntity<Long> countClassTimeTables(ClassTimeTableCriteria criteria) {
        log.debug("REST request to count ClassTimeTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(classTimeTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-time-tables/:id} : get the "id" classTimeTable.
     *
     * @param id the id of the classTimeTableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classTimeTableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-time-tables/{id}")
    public ResponseEntity<ClassTimeTableDTO> getClassTimeTable(@PathVariable Long id) {
        log.debug("REST request to get ClassTimeTable : {}", id);
        Optional<ClassTimeTableDTO> classTimeTableDTO = classTimeTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classTimeTableDTO);
    }

    /**
     * {@code DELETE  /class-time-tables/:id} : delete the "id" classTimeTable.
     *
     * @param id the id of the classTimeTableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-time-tables/{id}")
    public ResponseEntity<Void> deleteClassTimeTable(@PathVariable Long id) {
        log.debug("REST request to delete ClassTimeTable : {}", id);
        classTimeTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
