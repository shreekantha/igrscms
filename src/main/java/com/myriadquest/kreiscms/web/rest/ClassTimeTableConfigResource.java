package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.ClassTimeTableConfigService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.service.ClassTimeTableConfigQueryService;

import io.github.jhipster.service.filter.StringFilter;
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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.ClassTimeTableConfig}.
 */
@RestController
@RequestMapping("/api")
public class ClassTimeTableConfigResource {

    private final Logger log = LoggerFactory.getLogger(ClassTimeTableConfigResource.class);

    private static final String ENTITY_NAME = "classTimeTableConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassTimeTableConfigService classTimeTableConfigService;

    private final ClassTimeTableConfigQueryService classTimeTableConfigQueryService;

    public ClassTimeTableConfigResource(ClassTimeTableConfigService classTimeTableConfigService, ClassTimeTableConfigQueryService classTimeTableConfigQueryService) {
        this.classTimeTableConfigService = classTimeTableConfigService;
        this.classTimeTableConfigQueryService = classTimeTableConfigQueryService;
    }

    /**
     * {@code POST  /class-time-table-configs} : Create a new classTimeTableConfig.
     *
     * @param classTimeTableConfigDTO the classTimeTableConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classTimeTableConfigDTO, or with status {@code 400 (Bad Request)} if the classTimeTableConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-time-table-configs")
    public ResponseEntity<ClassTimeTableConfigDTO> createClassTimeTableConfig(@Valid @RequestBody ClassTimeTableConfigDTO classTimeTableConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ClassTimeTableConfig : {}", classTimeTableConfigDTO);
        if (classTimeTableConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new classTimeTableConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassTimeTableConfigDTO result = classTimeTableConfigService.save(classTimeTableConfigDTO);
        return ResponseEntity.created(new URI("/api/class-time-table-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-time-table-configs} : Updates an existing classTimeTableConfig.
     *
     * @param classTimeTableConfigDTO the classTimeTableConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classTimeTableConfigDTO,
     * or with status {@code 400 (Bad Request)} if the classTimeTableConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classTimeTableConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-time-table-configs")
    public ResponseEntity<ClassTimeTableConfigDTO> updateClassTimeTableConfig(@Valid @RequestBody ClassTimeTableConfigDTO classTimeTableConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ClassTimeTableConfig : {}", classTimeTableConfigDTO);
        if (classTimeTableConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassTimeTableConfigDTO result = classTimeTableConfigService.save(classTimeTableConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classTimeTableConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-time-table-configs} : get all the classTimeTableConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classTimeTableConfigs in body.
     */
    @GetMapping("/class-time-table-configs")
    public ResponseEntity<List<ClassTimeTableConfigDTO>> getAllClassTimeTableConfigs(ClassTimeTableConfigCriteria criteria) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get ClassTimeTableConfigs by criteria: {}", criteria);
        List<ClassTimeTableConfigDTO> entityList = classTimeTableConfigQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /class-time-table-configs/count} : count all the classTimeTableConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/class-time-table-configs/count")
    public ResponseEntity<Long> countClassTimeTableConfigs(ClassTimeTableConfigCriteria criteria) {
        log.debug("REST request to count ClassTimeTableConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(classTimeTableConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /class-time-table-configs/:id} : get the "id" classTimeTableConfig.
     *
     * @param id the id of the classTimeTableConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classTimeTableConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-time-table-configs/{id}")
    public ResponseEntity<ClassTimeTableConfigDTO> getClassTimeTableConfig(@PathVariable Long id) {
        log.debug("REST request to get ClassTimeTableConfig : {}", id);
        Optional<ClassTimeTableConfigDTO> classTimeTableConfigDTO = classTimeTableConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classTimeTableConfigDTO);
    }

    /**
     * {@code DELETE  /class-time-table-configs/:id} : delete the "id" classTimeTableConfig.
     *
     * @param id the id of the classTimeTableConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-time-table-configs/{id}")
    public ResponseEntity<Void> deleteClassTimeTableConfig(@PathVariable Long id) {
        log.debug("REST request to delete ClassTimeTableConfig : {}", id);
        classTimeTableConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
