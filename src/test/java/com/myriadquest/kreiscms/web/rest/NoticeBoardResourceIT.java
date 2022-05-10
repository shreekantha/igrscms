package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.NoticeBoard;
import com.myriadquest.kreiscms.repository.NoticeBoardRepository;
import com.myriadquest.kreiscms.service.NoticeBoardService;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;
import com.myriadquest.kreiscms.service.mapper.NoticeBoardMapper;
import com.myriadquest.kreiscms.service.dto.NoticeBoardCriteria;
import com.myriadquest.kreiscms.service.NoticeBoardQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NoticeBoardResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NoticeBoardResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private NoticeBoardMapper noticeBoardMapper;

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Autowired
    private NoticeBoardQueryService noticeBoardQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticeBoardMockMvc;

    private NoticeBoard noticeBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeBoard createEntity(EntityManager em) {
        NoticeBoard noticeBoard = new NoticeBoard()
            .details(DEFAULT_DETAILS)
            .active(DEFAULT_ACTIVE)
            .tenantId(DEFAULT_TENANT_ID);
        return noticeBoard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NoticeBoard createUpdatedEntity(EntityManager em) {
        NoticeBoard noticeBoard = new NoticeBoard()
            .details(UPDATED_DETAILS)
            .active(UPDATED_ACTIVE)
            .tenantId(UPDATED_TENANT_ID);
        return noticeBoard;
    }

    @BeforeEach
    public void initTest() {
        noticeBoard = createEntity(em);
    }

    @Test
    @Transactional
    public void createNoticeBoard() throws Exception {
        int databaseSizeBeforeCreate = noticeBoardRepository.findAll().size();
        // Create the NoticeBoard
        NoticeBoardDTO noticeBoardDTO = noticeBoardMapper.toDto(noticeBoard);
        restNoticeBoardMockMvc.perform(post("/api/notice-boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the NoticeBoard in the database
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeCreate + 1);
        NoticeBoard testNoticeBoard = noticeBoardList.get(noticeBoardList.size() - 1);
        assertThat(testNoticeBoard.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testNoticeBoard.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testNoticeBoard.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createNoticeBoardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noticeBoardRepository.findAll().size();

        // Create the NoticeBoard with an existing ID
        noticeBoard.setId(1L);
        NoticeBoardDTO noticeBoardDTO = noticeBoardMapper.toDto(noticeBoard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticeBoardMockMvc.perform(post("/api/notice-boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoticeBoard in the database
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = noticeBoardRepository.findAll().size();
        // set the field null
        noticeBoard.setActive(null);

        // Create the NoticeBoard, which fails.
        NoticeBoardDTO noticeBoardDTO = noticeBoardMapper.toDto(noticeBoard);


        restNoticeBoardMockMvc.perform(post("/api/notice-boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeBoardDTO)))
            .andExpect(status().isBadRequest());

        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNoticeBoards() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList
        restNoticeBoardMockMvc.perform(get("/api/notice-boards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticeBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getNoticeBoard() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get the noticeBoard
        restNoticeBoardMockMvc.perform(get("/api/notice-boards/{id}", noticeBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(noticeBoard.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getNoticeBoardsByIdFiltering() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        Long id = noticeBoard.getId();

        defaultNoticeBoardShouldBeFound("id.equals=" + id);
        defaultNoticeBoardShouldNotBeFound("id.notEquals=" + id);

        defaultNoticeBoardShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoticeBoardShouldNotBeFound("id.greaterThan=" + id);

        defaultNoticeBoardShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoticeBoardShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNoticeBoardsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where active equals to DEFAULT_ACTIVE
        defaultNoticeBoardShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the noticeBoardList where active equals to UPDATED_ACTIVE
        defaultNoticeBoardShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where active not equals to DEFAULT_ACTIVE
        defaultNoticeBoardShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the noticeBoardList where active not equals to UPDATED_ACTIVE
        defaultNoticeBoardShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultNoticeBoardShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the noticeBoardList where active equals to UPDATED_ACTIVE
        defaultNoticeBoardShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where active is not null
        defaultNoticeBoardShouldBeFound("active.specified=true");

        // Get all the noticeBoardList where active is null
        defaultNoticeBoardShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId equals to DEFAULT_TENANT_ID
        defaultNoticeBoardShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the noticeBoardList where tenantId equals to UPDATED_TENANT_ID
        defaultNoticeBoardShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId not equals to DEFAULT_TENANT_ID
        defaultNoticeBoardShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the noticeBoardList where tenantId not equals to UPDATED_TENANT_ID
        defaultNoticeBoardShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultNoticeBoardShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the noticeBoardList where tenantId equals to UPDATED_TENANT_ID
        defaultNoticeBoardShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId is not null
        defaultNoticeBoardShouldBeFound("tenantId.specified=true");

        // Get all the noticeBoardList where tenantId is null
        defaultNoticeBoardShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId contains DEFAULT_TENANT_ID
        defaultNoticeBoardShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the noticeBoardList where tenantId contains UPDATED_TENANT_ID
        defaultNoticeBoardShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNoticeBoardsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        // Get all the noticeBoardList where tenantId does not contain DEFAULT_TENANT_ID
        defaultNoticeBoardShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the noticeBoardList where tenantId does not contain UPDATED_TENANT_ID
        defaultNoticeBoardShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoticeBoardShouldBeFound(String filter) throws Exception {
        restNoticeBoardMockMvc.perform(get("/api/notice-boards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(noticeBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restNoticeBoardMockMvc.perform(get("/api/notice-boards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoticeBoardShouldNotBeFound(String filter) throws Exception {
        restNoticeBoardMockMvc.perform(get("/api/notice-boards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoticeBoardMockMvc.perform(get("/api/notice-boards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNoticeBoard() throws Exception {
        // Get the noticeBoard
        restNoticeBoardMockMvc.perform(get("/api/notice-boards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNoticeBoard() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        int databaseSizeBeforeUpdate = noticeBoardRepository.findAll().size();

        // Update the noticeBoard
        NoticeBoard updatedNoticeBoard = noticeBoardRepository.findById(noticeBoard.getId()).get();
        // Disconnect from session so that the updates on updatedNoticeBoard are not directly saved in db
        em.detach(updatedNoticeBoard);
        updatedNoticeBoard
            .details(UPDATED_DETAILS)
            .active(UPDATED_ACTIVE)
            .tenantId(UPDATED_TENANT_ID);
        NoticeBoardDTO noticeBoardDTO = noticeBoardMapper.toDto(updatedNoticeBoard);

        restNoticeBoardMockMvc.perform(put("/api/notice-boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeBoardDTO)))
            .andExpect(status().isOk());

        // Validate the NoticeBoard in the database
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeUpdate);
        NoticeBoard testNoticeBoard = noticeBoardList.get(noticeBoardList.size() - 1);
        assertThat(testNoticeBoard.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testNoticeBoard.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testNoticeBoard.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNoticeBoard() throws Exception {
        int databaseSizeBeforeUpdate = noticeBoardRepository.findAll().size();

        // Create the NoticeBoard
        NoticeBoardDTO noticeBoardDTO = noticeBoardMapper.toDto(noticeBoard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeBoardMockMvc.perform(put("/api/notice-boards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NoticeBoard in the database
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNoticeBoard() throws Exception {
        // Initialize the database
        noticeBoardRepository.saveAndFlush(noticeBoard);

        int databaseSizeBeforeDelete = noticeBoardRepository.findAll().size();

        // Delete the noticeBoard
        restNoticeBoardMockMvc.perform(delete("/api/notice-boards/{id}", noticeBoard.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NoticeBoard> noticeBoardList = noticeBoardRepository.findAll();
        assertThat(noticeBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
