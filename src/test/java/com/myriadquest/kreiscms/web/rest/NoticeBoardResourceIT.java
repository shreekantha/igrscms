package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.NoticeBoard;
import com.myriadquest.kreiscms.repository.NoticeBoardRepository;
import com.myriadquest.kreiscms.service.NoticeBoardService;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;
import com.myriadquest.kreiscms.service.mapper.NoticeBoardMapper;

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

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private NoticeBoardMapper noticeBoardMapper;

    @Autowired
    private NoticeBoardService noticeBoardService;

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
            .active(DEFAULT_ACTIVE);
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
            .active(UPDATED_ACTIVE);
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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
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
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
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
            .active(UPDATED_ACTIVE);
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
