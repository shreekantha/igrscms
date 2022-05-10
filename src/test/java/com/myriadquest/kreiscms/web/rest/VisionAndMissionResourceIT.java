package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.VisionAndMission;
import com.myriadquest.kreiscms.repository.VisionAndMissionRepository;
import com.myriadquest.kreiscms.service.VisionAndMissionService;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;
import com.myriadquest.kreiscms.service.mapper.VisionAndMissionMapper;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionCriteria;
import com.myriadquest.kreiscms.service.VisionAndMissionQueryService;

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
 * Integration tests for the {@link VisionAndMissionResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VisionAndMissionResourceIT {

    private static final String DEFAULT_VISION = "AAAAAAAAAA";
    private static final String UPDATED_VISION = "BBBBBBBBBB";

    private static final String DEFAULT_MISSION = "AAAAAAAAAA";
    private static final String UPDATED_MISSION = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private VisionAndMissionRepository visionAndMissionRepository;

    @Autowired
    private VisionAndMissionMapper visionAndMissionMapper;

    @Autowired
    private VisionAndMissionService visionAndMissionService;

    @Autowired
    private VisionAndMissionQueryService visionAndMissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisionAndMissionMockMvc;

    private VisionAndMission visionAndMission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisionAndMission createEntity(EntityManager em) {
        VisionAndMission visionAndMission = new VisionAndMission()
            .vision(DEFAULT_VISION)
            .mission(DEFAULT_MISSION)
            .tenantId(DEFAULT_TENANT_ID);
        return visionAndMission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisionAndMission createUpdatedEntity(EntityManager em) {
        VisionAndMission visionAndMission = new VisionAndMission()
            .vision(UPDATED_VISION)
            .mission(UPDATED_MISSION)
            .tenantId(UPDATED_TENANT_ID);
        return visionAndMission;
    }

    @BeforeEach
    public void initTest() {
        visionAndMission = createEntity(em);
    }

    @Test
    @Transactional
    public void createVisionAndMission() throws Exception {
        int databaseSizeBeforeCreate = visionAndMissionRepository.findAll().size();
        // Create the VisionAndMission
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(visionAndMission);
        restVisionAndMissionMockMvc.perform(post("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isCreated());

        // Validate the VisionAndMission in the database
        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeCreate + 1);
        VisionAndMission testVisionAndMission = visionAndMissionList.get(visionAndMissionList.size() - 1);
        assertThat(testVisionAndMission.getVision()).isEqualTo(DEFAULT_VISION);
        assertThat(testVisionAndMission.getMission()).isEqualTo(DEFAULT_MISSION);
        assertThat(testVisionAndMission.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createVisionAndMissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visionAndMissionRepository.findAll().size();

        // Create the VisionAndMission with an existing ID
        visionAndMission.setId(1L);
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(visionAndMission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisionAndMissionMockMvc.perform(post("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisionAndMission in the database
        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = visionAndMissionRepository.findAll().size();
        // set the field null
        visionAndMission.setVision(null);

        // Create the VisionAndMission, which fails.
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(visionAndMission);


        restVisionAndMissionMockMvc.perform(post("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isBadRequest());

        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = visionAndMissionRepository.findAll().size();
        // set the field null
        visionAndMission.setMission(null);

        // Create the VisionAndMission, which fails.
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(visionAndMission);


        restVisionAndMissionMockMvc.perform(post("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isBadRequest());

        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissions() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visionAndMission.getId().intValue())))
            .andExpect(jsonPath("$.[*].vision").value(hasItem(DEFAULT_VISION)))
            .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getVisionAndMission() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get the visionAndMission
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions/{id}", visionAndMission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visionAndMission.getId().intValue()))
            .andExpect(jsonPath("$.vision").value(DEFAULT_VISION))
            .andExpect(jsonPath("$.mission").value(DEFAULT_MISSION))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getVisionAndMissionsByIdFiltering() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        Long id = visionAndMission.getId();

        defaultVisionAndMissionShouldBeFound("id.equals=" + id);
        defaultVisionAndMissionShouldNotBeFound("id.notEquals=" + id);

        defaultVisionAndMissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVisionAndMissionShouldNotBeFound("id.greaterThan=" + id);

        defaultVisionAndMissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVisionAndMissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionIsEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision equals to DEFAULT_VISION
        defaultVisionAndMissionShouldBeFound("vision.equals=" + DEFAULT_VISION);

        // Get all the visionAndMissionList where vision equals to UPDATED_VISION
        defaultVisionAndMissionShouldNotBeFound("vision.equals=" + UPDATED_VISION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision not equals to DEFAULT_VISION
        defaultVisionAndMissionShouldNotBeFound("vision.notEquals=" + DEFAULT_VISION);

        // Get all the visionAndMissionList where vision not equals to UPDATED_VISION
        defaultVisionAndMissionShouldBeFound("vision.notEquals=" + UPDATED_VISION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionIsInShouldWork() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision in DEFAULT_VISION or UPDATED_VISION
        defaultVisionAndMissionShouldBeFound("vision.in=" + DEFAULT_VISION + "," + UPDATED_VISION);

        // Get all the visionAndMissionList where vision equals to UPDATED_VISION
        defaultVisionAndMissionShouldNotBeFound("vision.in=" + UPDATED_VISION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision is not null
        defaultVisionAndMissionShouldBeFound("vision.specified=true");

        // Get all the visionAndMissionList where vision is null
        defaultVisionAndMissionShouldNotBeFound("vision.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision contains DEFAULT_VISION
        defaultVisionAndMissionShouldBeFound("vision.contains=" + DEFAULT_VISION);

        // Get all the visionAndMissionList where vision contains UPDATED_VISION
        defaultVisionAndMissionShouldNotBeFound("vision.contains=" + UPDATED_VISION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByVisionNotContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where vision does not contain DEFAULT_VISION
        defaultVisionAndMissionShouldNotBeFound("vision.doesNotContain=" + DEFAULT_VISION);

        // Get all the visionAndMissionList where vision does not contain UPDATED_VISION
        defaultVisionAndMissionShouldBeFound("vision.doesNotContain=" + UPDATED_VISION);
    }


    @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionIsEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission equals to DEFAULT_MISSION
        defaultVisionAndMissionShouldBeFound("mission.equals=" + DEFAULT_MISSION);

        // Get all the visionAndMissionList where mission equals to UPDATED_MISSION
        defaultVisionAndMissionShouldNotBeFound("mission.equals=" + UPDATED_MISSION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission not equals to DEFAULT_MISSION
        defaultVisionAndMissionShouldNotBeFound("mission.notEquals=" + DEFAULT_MISSION);

        // Get all the visionAndMissionList where mission not equals to UPDATED_MISSION
        defaultVisionAndMissionShouldBeFound("mission.notEquals=" + UPDATED_MISSION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionIsInShouldWork() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission in DEFAULT_MISSION or UPDATED_MISSION
        defaultVisionAndMissionShouldBeFound("mission.in=" + DEFAULT_MISSION + "," + UPDATED_MISSION);

        // Get all the visionAndMissionList where mission equals to UPDATED_MISSION
        defaultVisionAndMissionShouldNotBeFound("mission.in=" + UPDATED_MISSION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission is not null
        defaultVisionAndMissionShouldBeFound("mission.specified=true");

        // Get all the visionAndMissionList where mission is null
        defaultVisionAndMissionShouldNotBeFound("mission.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission contains DEFAULT_MISSION
        defaultVisionAndMissionShouldBeFound("mission.contains=" + DEFAULT_MISSION);

        // Get all the visionAndMissionList where mission contains UPDATED_MISSION
        defaultVisionAndMissionShouldNotBeFound("mission.contains=" + UPDATED_MISSION);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByMissionNotContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where mission does not contain DEFAULT_MISSION
        defaultVisionAndMissionShouldNotBeFound("mission.doesNotContain=" + DEFAULT_MISSION);

        // Get all the visionAndMissionList where mission does not contain UPDATED_MISSION
        defaultVisionAndMissionShouldBeFound("mission.doesNotContain=" + UPDATED_MISSION);
    }


    @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId equals to DEFAULT_TENANT_ID
        defaultVisionAndMissionShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the visionAndMissionList where tenantId equals to UPDATED_TENANT_ID
        defaultVisionAndMissionShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId not equals to DEFAULT_TENANT_ID
        defaultVisionAndMissionShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the visionAndMissionList where tenantId not equals to UPDATED_TENANT_ID
        defaultVisionAndMissionShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultVisionAndMissionShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the visionAndMissionList where tenantId equals to UPDATED_TENANT_ID
        defaultVisionAndMissionShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId is not null
        defaultVisionAndMissionShouldBeFound("tenantId.specified=true");

        // Get all the visionAndMissionList where tenantId is null
        defaultVisionAndMissionShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId contains DEFAULT_TENANT_ID
        defaultVisionAndMissionShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the visionAndMissionList where tenantId contains UPDATED_TENANT_ID
        defaultVisionAndMissionShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllVisionAndMissionsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        // Get all the visionAndMissionList where tenantId does not contain DEFAULT_TENANT_ID
        defaultVisionAndMissionShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the visionAndMissionList where tenantId does not contain UPDATED_TENANT_ID
        defaultVisionAndMissionShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVisionAndMissionShouldBeFound(String filter) throws Exception {
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visionAndMission.getId().intValue())))
            .andExpect(jsonPath("$.[*].vision").value(hasItem(DEFAULT_VISION)))
            .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVisionAndMissionShouldNotBeFound(String filter) throws Exception {
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingVisionAndMission() throws Exception {
        // Get the visionAndMission
        restVisionAndMissionMockMvc.perform(get("/api/vision-and-missions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVisionAndMission() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        int databaseSizeBeforeUpdate = visionAndMissionRepository.findAll().size();

        // Update the visionAndMission
        VisionAndMission updatedVisionAndMission = visionAndMissionRepository.findById(visionAndMission.getId()).get();
        // Disconnect from session so that the updates on updatedVisionAndMission are not directly saved in db
        em.detach(updatedVisionAndMission);
        updatedVisionAndMission
            .vision(UPDATED_VISION)
            .mission(UPDATED_MISSION)
            .tenantId(UPDATED_TENANT_ID);
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(updatedVisionAndMission);

        restVisionAndMissionMockMvc.perform(put("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isOk());

        // Validate the VisionAndMission in the database
        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeUpdate);
        VisionAndMission testVisionAndMission = visionAndMissionList.get(visionAndMissionList.size() - 1);
        assertThat(testVisionAndMission.getVision()).isEqualTo(UPDATED_VISION);
        assertThat(testVisionAndMission.getMission()).isEqualTo(UPDATED_MISSION);
        assertThat(testVisionAndMission.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingVisionAndMission() throws Exception {
        int databaseSizeBeforeUpdate = visionAndMissionRepository.findAll().size();

        // Create the VisionAndMission
        VisionAndMissionDTO visionAndMissionDTO = visionAndMissionMapper.toDto(visionAndMission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisionAndMissionMockMvc.perform(put("/api/vision-and-missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visionAndMissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VisionAndMission in the database
        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVisionAndMission() throws Exception {
        // Initialize the database
        visionAndMissionRepository.saveAndFlush(visionAndMission);

        int databaseSizeBeforeDelete = visionAndMissionRepository.findAll().size();

        // Delete the visionAndMission
        restVisionAndMissionMockMvc.perform(delete("/api/vision-and-missions/{id}", visionAndMission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VisionAndMission> visionAndMissionList = visionAndMissionRepository.findAll();
        assertThat(visionAndMissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
