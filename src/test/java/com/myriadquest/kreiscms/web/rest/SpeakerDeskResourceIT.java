package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.SpeakerDesk;
import com.myriadquest.kreiscms.repository.SpeakerDeskRepository;
import com.myriadquest.kreiscms.service.SpeakerDeskService;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;
import com.myriadquest.kreiscms.service.mapper.SpeakerDeskMapper;

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
 * Integration tests for the {@link SpeakerDeskResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpeakerDeskResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    @Autowired
    private SpeakerDeskRepository speakerDeskRepository;

    @Autowired
    private SpeakerDeskMapper speakerDeskMapper;

    @Autowired
    private SpeakerDeskService speakerDeskService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpeakerDeskMockMvc;

    private SpeakerDesk speakerDesk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeakerDesk createEntity(EntityManager em) {
        SpeakerDesk speakerDesk = new SpeakerDesk()
            .name(DEFAULT_NAME)
            .note(DEFAULT_NOTE)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return speakerDesk;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SpeakerDesk createUpdatedEntity(EntityManager em) {
        SpeakerDesk speakerDesk = new SpeakerDesk()
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return speakerDesk;
    }

    @BeforeEach
    public void initTest() {
        speakerDesk = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpeakerDesk() throws Exception {
        int databaseSizeBeforeCreate = speakerDeskRepository.findAll().size();
        // Create the SpeakerDesk
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);
        restSpeakerDeskMockMvc.perform(post("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isCreated());

        // Validate the SpeakerDesk in the database
        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeCreate + 1);
        SpeakerDesk testSpeakerDesk = speakerDeskList.get(speakerDeskList.size() - 1);
        assertThat(testSpeakerDesk.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSpeakerDesk.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testSpeakerDesk.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testSpeakerDesk.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testSpeakerDesk.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createSpeakerDeskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = speakerDeskRepository.findAll().size();

        // Create the SpeakerDesk with an existing ID
        speakerDesk.setId(1L);
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpeakerDeskMockMvc.perform(post("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpeakerDesk in the database
        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = speakerDeskRepository.findAll().size();
        // set the field null
        speakerDesk.setName(null);

        // Create the SpeakerDesk, which fails.
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);


        restSpeakerDeskMockMvc.perform(post("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isBadRequest());

        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = speakerDeskRepository.findAll().size();
        // set the field null
        speakerDesk.setNote(null);

        // Create the SpeakerDesk, which fails.
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);


        restSpeakerDeskMockMvc.perform(post("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isBadRequest());

        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = speakerDeskRepository.findAll().size();
        // set the field null
        speakerDesk.setImgLink(null);

        // Create the SpeakerDesk, which fails.
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);


        restSpeakerDeskMockMvc.perform(post("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isBadRequest());

        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpeakerDesks() throws Exception {
        // Initialize the database
        speakerDeskRepository.saveAndFlush(speakerDesk);

        // Get all the speakerDeskList
        restSpeakerDeskMockMvc.perform(get("/api/speaker-desks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speakerDesk.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }
    
    @Test
    @Transactional
    public void getSpeakerDesk() throws Exception {
        // Initialize the database
        speakerDeskRepository.saveAndFlush(speakerDesk);

        // Get the speakerDesk
        restSpeakerDeskMockMvc.perform(get("/api/speaker-desks/{id}", speakerDesk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speakerDesk.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }
    @Test
    @Transactional
    public void getNonExistingSpeakerDesk() throws Exception {
        // Get the speakerDesk
        restSpeakerDeskMockMvc.perform(get("/api/speaker-desks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpeakerDesk() throws Exception {
        // Initialize the database
        speakerDeskRepository.saveAndFlush(speakerDesk);

        int databaseSizeBeforeUpdate = speakerDeskRepository.findAll().size();

        // Update the speakerDesk
        SpeakerDesk updatedSpeakerDesk = speakerDeskRepository.findById(speakerDesk.getId()).get();
        // Disconnect from session so that the updates on updatedSpeakerDesk are not directly saved in db
        em.detach(updatedSpeakerDesk);
        updatedSpeakerDesk
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(updatedSpeakerDesk);

        restSpeakerDeskMockMvc.perform(put("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isOk());

        // Validate the SpeakerDesk in the database
        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeUpdate);
        SpeakerDesk testSpeakerDesk = speakerDeskList.get(speakerDeskList.size() - 1);
        assertThat(testSpeakerDesk.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSpeakerDesk.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testSpeakerDesk.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testSpeakerDesk.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testSpeakerDesk.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpeakerDesk() throws Exception {
        int databaseSizeBeforeUpdate = speakerDeskRepository.findAll().size();

        // Create the SpeakerDesk
        SpeakerDeskDTO speakerDeskDTO = speakerDeskMapper.toDto(speakerDesk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpeakerDeskMockMvc.perform(put("/api/speaker-desks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(speakerDeskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SpeakerDesk in the database
        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpeakerDesk() throws Exception {
        // Initialize the database
        speakerDeskRepository.saveAndFlush(speakerDesk);

        int databaseSizeBeforeDelete = speakerDeskRepository.findAll().size();

        // Delete the speakerDesk
        restSpeakerDeskMockMvc.perform(delete("/api/speaker-desks/{id}", speakerDesk.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SpeakerDesk> speakerDeskList = speakerDeskRepository.findAll();
        assertThat(speakerDeskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
