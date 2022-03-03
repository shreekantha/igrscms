package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Vision;
import com.myriadquest.kreiscms.repository.VisionRepository;
import com.myriadquest.kreiscms.service.VisionService;
import com.myriadquest.kreiscms.service.dto.VisionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VisionResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisionResourceIT {

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private VisionRepository visionRepository;

    @Autowired
    private VisionMapper visionMapper;

    @Autowired
    private VisionService visionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisionMockMvc;

    private Vision vision;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vision createEntity(EntityManager em) {
        Vision vision = new Vision()
            .detail(DEFAULT_DETAIL);
        return vision;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vision createUpdatedEntity(EntityManager em) {
        Vision vision = new Vision()
            .detail(UPDATED_DETAIL);
        return vision;
    }

    @BeforeEach
    public void initTest() {
        vision = createEntity(em);
    }

    @Test
    @Transactional
    public void createVision() throws Exception {
        int databaseSizeBeforeCreate = visionRepository.findAll().size();
        // Create the Vision
        VisionDTO visionDTO = visionMapper.toDto(vision);
        restVisionMockMvc.perform(post("/api/visions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionDTO)))
            .andExpect(status().isCreated());

        // Validate the Vision in the database
        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeCreate + 1);
        Vision testVision = visionList.get(visionList.size() - 1);
        assertThat(testVision.getDetail()).isEqualTo(DEFAULT_DETAIL);
    }

    @Test
    @Transactional
    public void createVisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visionRepository.findAll().size();

        // Create the Vision with an existing ID
        vision.setId(1L);
        VisionDTO visionDTO = visionMapper.toDto(vision);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisionMockMvc.perform(post("/api/visions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vision in the database
        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDetailIsRequired() throws Exception {
        int databaseSizeBeforeTest = visionRepository.findAll().size();
        // set the field null
        vision.setDetail(null);

        // Create the Vision, which fails.
        VisionDTO visionDTO = visionMapper.toDto(vision);


        restVisionMockMvc.perform(post("/api/visions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionDTO)))
            .andExpect(status().isBadRequest());

        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisions() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList
        restVisionMockMvc.perform(get("/api/visions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vision.getId().intValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)));
    }
    
    @Test
    @Transactional
    public void getVision() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get the vision
        restVisionMockMvc.perform(get("/api/visions/{id}", vision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vision.getId().intValue()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL));
    }
    @Test
    @Transactional
    public void getNonExistingVision() throws Exception {
        // Get the vision
        restVisionMockMvc.perform(get("/api/visions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVision() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        int databaseSizeBeforeUpdate = visionRepository.findAll().size();

        // Update the vision
        Vision updatedVision = visionRepository.findById(vision.getId()).get();
        // Disconnect from session so that the updates on updatedVision are not directly saved in db
        em.detach(updatedVision);
        updatedVision
            .detail(UPDATED_DETAIL);
        VisionDTO visionDTO = visionMapper.toDto(updatedVision);

        restVisionMockMvc.perform(put("/api/visions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionDTO)))
            .andExpect(status().isOk());

        // Validate the Vision in the database
        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeUpdate);
        Vision testVision = visionList.get(visionList.size() - 1);
        assertThat(testVision.getDetail()).isEqualTo(UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingVision() throws Exception {
        int databaseSizeBeforeUpdate = visionRepository.findAll().size();

        // Create the Vision
        VisionDTO visionDTO = visionMapper.toDto(vision);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisionMockMvc.perform(put("/api/visions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vision in the database
        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVision() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        int databaseSizeBeforeDelete = visionRepository.findAll().size();

        // Delete the vision
        restVisionMockMvc.perform(delete("/api/visions/{id}", vision.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vision> visionList = visionRepository.findAll();
        assertThat(visionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
