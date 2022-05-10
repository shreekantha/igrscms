package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Vision;
import com.myriadquest.kreiscms.repository.VisionRepository;
import com.myriadquest.kreiscms.service.VisionService;
import com.myriadquest.kreiscms.service.dto.VisionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionMapper;
import com.myriadquest.kreiscms.service.dto.VisionCriteria;
import com.myriadquest.kreiscms.service.VisionQueryService;

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

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private VisionRepository visionRepository;

    @Autowired
    private VisionMapper visionMapper;

    @Autowired
    private VisionService visionService;

    @Autowired
    private VisionQueryService visionQueryService;

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
            .detail(DEFAULT_DETAIL)
            .tenantId(DEFAULT_TENANT_ID);
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
            .detail(UPDATED_DETAIL)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testVision.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
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
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getVisionsByIdFiltering() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        Long id = vision.getId();

        defaultVisionShouldBeFound("id.equals=" + id);
        defaultVisionShouldNotBeFound("id.notEquals=" + id);

        defaultVisionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVisionShouldNotBeFound("id.greaterThan=" + id);

        defaultVisionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVisionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVisionsByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail equals to DEFAULT_DETAIL
        defaultVisionShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the visionList where detail equals to UPDATED_DETAIL
        defaultVisionShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllVisionsByDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail not equals to DEFAULT_DETAIL
        defaultVisionShouldNotBeFound("detail.notEquals=" + DEFAULT_DETAIL);

        // Get all the visionList where detail not equals to UPDATED_DETAIL
        defaultVisionShouldBeFound("detail.notEquals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllVisionsByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultVisionShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the visionList where detail equals to UPDATED_DETAIL
        defaultVisionShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllVisionsByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail is not null
        defaultVisionShouldBeFound("detail.specified=true");

        // Get all the visionList where detail is null
        defaultVisionShouldNotBeFound("detail.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisionsByDetailContainsSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail contains DEFAULT_DETAIL
        defaultVisionShouldBeFound("detail.contains=" + DEFAULT_DETAIL);

        // Get all the visionList where detail contains UPDATED_DETAIL
        defaultVisionShouldNotBeFound("detail.contains=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllVisionsByDetailNotContainsSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where detail does not contain DEFAULT_DETAIL
        defaultVisionShouldNotBeFound("detail.doesNotContain=" + DEFAULT_DETAIL);

        // Get all the visionList where detail does not contain UPDATED_DETAIL
        defaultVisionShouldBeFound("detail.doesNotContain=" + UPDATED_DETAIL);
    }


    @Test
    @Transactional
    public void getAllVisionsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId equals to DEFAULT_TENANT_ID
        defaultVisionShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the visionList where tenantId equals to UPDATED_TENANT_ID
        defaultVisionShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId not equals to DEFAULT_TENANT_ID
        defaultVisionShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the visionList where tenantId not equals to UPDATED_TENANT_ID
        defaultVisionShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultVisionShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the visionList where tenantId equals to UPDATED_TENANT_ID
        defaultVisionShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId is not null
        defaultVisionShouldBeFound("tenantId.specified=true");

        // Get all the visionList where tenantId is null
        defaultVisionShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisionsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId contains DEFAULT_TENANT_ID
        defaultVisionShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the visionList where tenantId contains UPDATED_TENANT_ID
        defaultVisionShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        visionRepository.saveAndFlush(vision);

        // Get all the visionList where tenantId does not contain DEFAULT_TENANT_ID
        defaultVisionShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the visionList where tenantId does not contain UPDATED_TENANT_ID
        defaultVisionShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVisionShouldBeFound(String filter) throws Exception {
        restVisionMockMvc.perform(get("/api/visions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vision.getId().intValue())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restVisionMockMvc.perform(get("/api/visions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVisionShouldNotBeFound(String filter) throws Exception {
        restVisionMockMvc.perform(get("/api/visions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVisionMockMvc.perform(get("/api/visions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .detail(UPDATED_DETAIL)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testVision.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
