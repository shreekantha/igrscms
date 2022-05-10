package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.service.NewsService;
import com.myriadquest.kreiscms.web.rest.errors.BadRequestAlertException;
import com.myriadquest.kreiscms.service.dto.NewsDTO;
import com.myriadquest.kreiscms.service.dto.NewsCriteria;
import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.service.NewsQueryService;

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
 * REST controller for managing {@link com.myriadquest.kreiscms.domain.News}.
 */
@RestController
@RequestMapping("/api")
public class NewsResource {

    private final Logger log = LoggerFactory.getLogger(NewsResource.class);

    private static final String ENTITY_NAME = "news";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsService newsService;

    private final NewsQueryService newsQueryService;

    public NewsResource(NewsService newsService, NewsQueryService newsQueryService) {
        this.newsService = newsService;
        this.newsQueryService = newsQueryService;
    }

    /**
     * {@code POST  /news} : Create a new news.
     *
     * @param newsDTO the newsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsDTO, or with status {@code 400 (Bad Request)} if the news has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news")
    public ResponseEntity<NewsDTO> createNews(@Valid @RequestBody NewsDTO newsDTO) throws URISyntaxException {
        log.debug("REST request to save News : {}", newsDTO);
        if (newsDTO.getId() != null) {
            throw new BadRequestAlertException("A new news cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsDTO result = newsService.save(newsDTO);
        return ResponseEntity.created(new URI("/api/news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news} : Updates an existing news.
     *
     * @param newsDTO the newsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsDTO,
     * or with status {@code 400 (Bad Request)} if the newsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news")
    public ResponseEntity<NewsDTO> updateNews(@Valid @RequestBody NewsDTO newsDTO) throws URISyntaxException {
        log.debug("REST request to update News : {}", newsDTO);
        if (newsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NewsDTO result = newsService.save(newsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, newsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /news} : get all the news.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of news in body.
     */
    @GetMapping("/news")
    public ResponseEntity<List<NewsDTO>> getAllNews(NewsCriteria criteria, Pageable pageable) {
    	criteria.setTenantId(IgrscmsApp.getTenantFilter());
    	log.debug("REST request to get News by criteria: {}", criteria);
        Page<NewsDTO> page = newsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /news/count} : count all the news.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/news/count")
    public ResponseEntity<Long> countNews(NewsCriteria criteria) {
        log.debug("REST request to count News by criteria: {}", criteria);
        return ResponseEntity.ok().body(newsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /news/:id} : get the "id" news.
     *
     * @param id the id of the newsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        log.debug("REST request to get News : {}", id);
        Optional<NewsDTO> newsDTO = newsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsDTO);
    }

    /**
     * {@code DELETE  /news/:id} : delete the "id" news.
     *
     * @param id the id of the newsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        log.debug("REST request to delete News : {}", id);
        newsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
