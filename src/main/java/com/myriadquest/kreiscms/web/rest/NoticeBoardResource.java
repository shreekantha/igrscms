package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.NoticeBoardService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;

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

    public NoticeBoardResource(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of noticeBoards in body.
     */
    @GetMapping("/notice-boards")
    public List<NoticeBoardDTO> getAllNoticeBoards() {
        log.debug("REST request to get all NoticeBoards");
        return noticeBoardService.findAll();
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
