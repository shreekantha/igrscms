package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.NoticeBoardService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;
import com.myriadquest.kreiscms.service.dto.NoticeBoardCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.NoticeBoardQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.NoticeBoard}.
 */
@RestController
@RequestMapping("/api")
public class NoticeBoardResource {

    private final Logger log = LoggerFactory.getLogger(NoticeBoardResource.class);

    private static final String ENTITY_NAME = "noticeBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NoticeBoardService noticeBoardService;

    private final NoticeBoardQueryService noticeBoardQueryService;

    public NoticeBoardResource(NoticeBoardService noticeBoardService, NoticeBoardQueryService noticeBoardQueryService) {
        this.noticeBoardService = noticeBoardService;
        this.noticeBoardQueryService = noticeBoardQueryService;
    }

    /**
     * {@code POST  /notice-boards} : Create a new noticeBoard.
     *
     * @param noticeBoardDTO the noticeBoardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new noticeBoardDTO, or with status {@code 400 (Bad Request)} if the noticeBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notice-boards")
    public ResponseEntity<NoticeBoardDTO> createNoticeBoard(@Valid @RequestBody NoticeBoardDTO noticeBoardDTO) throws URISyntaxException {
        log.debug("REST request to save NoticeBoard : {}", noticeBoardDTO);
        if (noticeBoardDTO.getId() != null) {
            throw new BadRequestAlertException("A new noticeBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NoticeBoardDTO result = noticeBoardService.save(noticeBoardDTO);
        return ResponseEntity.created(new URI("/api/notice-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notice-boards} : Updates an existing noticeBoard.
     *
     * @param noticeBoardDTO the noticeBoardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated noticeBoardDTO,
     * or with status {@code 400 (Bad Request)} if the noticeBoardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the noticeBoardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notice-boards")
    public ResponseEntity<NoticeBoardDTO> updateNoticeBoard(@Valid @RequestBody NoticeBoardDTO noticeBoardDTO) throws URISyntaxException {
        log.debug("REST request to update NoticeBoard : {}", noticeBoardDTO);
        if (noticeBoardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NoticeBoardDTO result = noticeBoardService.save(noticeBoardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, noticeBoardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notice-boards} : get all the noticeBoards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticeBoards in body.
     */
    @GetMapping("/notice-boards")
    public ResponseEntity<List<NoticeBoardDTO>> getAllNoticeBoards(NoticeBoardCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get NoticeBoards by criteria: {}", criteria);
        Page<NoticeBoardDTO> page = noticeBoardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notice-boards/count} : count all the noticeBoards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notice-boards/count")
    public ResponseEntity<Long> countNoticeBoards(NoticeBoardCriteria criteria) {
        log.debug("REST request to count NoticeBoards by criteria: {}", criteria);
        return ResponseEntity.ok().body(noticeBoardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notice-boards/:id} : get the "id" noticeBoard.
     *
     * @param id the id of the noticeBoardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the noticeBoardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notice-boards/{id}")
    public ResponseEntity<NoticeBoardDTO> getNoticeBoard(@PathVariable Long id) {
        log.debug("REST request to get NoticeBoard : {}", id);
        Optional<NoticeBoardDTO> noticeBoardDTO = noticeBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(noticeBoardDTO);
    }

    /**
     * {@code DELETE  /notice-boards/:id} : delete the "id" noticeBoard.
     *
     * @param id the id of the noticeBoardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notice-boards/{id}")
    public ResponseEntity<Void> deleteNoticeBoard(@PathVariable Long id) {
        log.debug("REST request to delete NoticeBoard : {}", id);
        noticeBoardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
