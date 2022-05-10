package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Mission;
import com.myriadquest.kreiscms.repository.MissionRepository;
import com.myriadquest.kreiscms.service.MissionService;
import com.myriadquest.kreiscms.service.dto.MissionDTO;
import com.myriadquest.kreiscms.service.mapper.MissionMapper;
import com.myriadquest.kreiscms.service.dto.MissionCriteria;
import com.myriadquest.kreiscms.service.MissionQueryService;

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
 * Integration tests for the {@link MissionResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MissionResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionQueryService missionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMissionMockMvc;

    private Mission mission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createEntity(EntityManager em) {
        Mission mission = new Mission()
            .details(DEFAULT_DETAILS)
            .tenantId(DEFAULT_TENANT_ID);
        return mission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mission createUpdatedEntity(EntityManager em) {
        Mission mission = new Mission()
            .details(UPDATED_DETAILS)
            .tenantId(UPDATED_TENANT_ID);
        return mission;
    }

    @BeforeEach
    public void initTest() {
        mission = createEntity(em);
    }

    @Test
    @Transactional
    public void createMission() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();
        // Create the Mission
        MissionDTO missionDTO = missionMapper.toDto(mission);
        restMissionMockMvc.perform(post("/api/missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missionDTO)))
            .andExpect(status().isCreated());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate + 1);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testMission.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createMissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = missionRepository.findAll().size();

        // Create the Mission with an existing ID
        mission.setId(1L);
        MissionDTO missionDTO = missionMapper.toDto(mission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMissionMockMvc.perform(post("/api/missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = missionRepository.findAll().size();
        // set the field null
        mission.setDetails(null);

        // Create the Mission, which fails.
        MissionDTO missionDTO = missionMapper.toDto(mission);


        restMissionMockMvc.perform(post("/api/missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missionDTO)))
            .andExpect(status().isBadRequest());

        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMissions() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList
        restMissionMockMvc.perform(get("/api/missions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mission.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get the mission
        restMissionMockMvc.perform(get("/api/missions/{id}", mission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mission.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getMissionsByIdFiltering() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        Long id = mission.getId();

        defaultMissionShouldBeFound("id.equals=" + id);
        defaultMissionShouldNotBeFound("id.notEquals=" + id);

        defaultMissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMissionShouldNotBeFound("id.greaterThan=" + id);

        defaultMissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMissionsByDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details equals to DEFAULT_DETAILS
        defaultMissionShouldBeFound("details.equals=" + DEFAULT_DETAILS);

        // Get all the missionList where details equals to UPDATED_DETAILS
        defaultMissionShouldNotBeFound("details.equals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllMissionsByDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details not equals to DEFAULT_DETAILS
        defaultMissionShouldNotBeFound("details.notEquals=" + DEFAULT_DETAILS);

        // Get all the missionList where details not equals to UPDATED_DETAILS
        defaultMissionShouldBeFound("details.notEquals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllMissionsByDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details in DEFAULT_DETAILS or UPDATED_DETAILS
        defaultMissionShouldBeFound("details.in=" + DEFAULT_DETAILS + "," + UPDATED_DETAILS);

        // Get all the missionList where details equals to UPDATED_DETAILS
        defaultMissionShouldNotBeFound("details.in=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllMissionsByDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details is not null
        defaultMissionShouldBeFound("details.specified=true");

        // Get all the missionList where details is null
        defaultMissionShouldNotBeFound("details.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissionsByDetailsContainsSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details contains DEFAULT_DETAILS
        defaultMissionShouldBeFound("details.contains=" + DEFAULT_DETAILS);

        // Get all the missionList where details contains UPDATED_DETAILS
        defaultMissionShouldNotBeFound("details.contains=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllMissionsByDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where details does not contain DEFAULT_DETAILS
        defaultMissionShouldNotBeFound("details.doesNotContain=" + DEFAULT_DETAILS);

        // Get all the missionList where details does not contain UPDATED_DETAILS
        defaultMissionShouldBeFound("details.doesNotContain=" + UPDATED_DETAILS);
    }


    @Test
    @Transactional
    public void getAllMissionsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId equals to DEFAULT_TENANT_ID
        defaultMissionShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the missionList where tenantId equals to UPDATED_TENANT_ID
        defaultMissionShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllMissionsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId not equals to DEFAULT_TENANT_ID
        defaultMissionShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the missionList where tenantId not equals to UPDATED_TENANT_ID
        defaultMissionShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllMissionsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultMissionShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the missionList where tenantId equals to UPDATED_TENANT_ID
        defaultMissionShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllMissionsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId is not null
        defaultMissionShouldBeFound("tenantId.specified=true");

        // Get all the missionList where tenantId is null
        defaultMissionShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllMissionsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId contains DEFAULT_TENANT_ID
        defaultMissionShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the missionList where tenantId contains UPDATED_TENANT_ID
        defaultMissionShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllMissionsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        // Get all the missionList where tenantId does not contain DEFAULT_TENANT_ID
        defaultMissionShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the missionList where tenantId does not contain UPDATED_TENANT_ID
        defaultMissionShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMissionShouldBeFound(String filter) throws Exception {
        restMissionMockMvc.perform(get("/api/missions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mission.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restMissionMockMvc.perform(get("/api/missions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMissionShouldNotBeFound(String filter) throws Exception {
        restMissionMockMvc.perform(get("/api/missions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMissionMockMvc.perform(get("/api/missions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMission() throws Exception {
        // Get the mission
        restMissionMockMvc.perform(get("/api/missions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Update the mission
        Mission updatedMission = missionRepository.findById(mission.getId()).get();
        // Disconnect from session so that the updates on updatedMission are not directly saved in db
        em.detach(updatedMission);
        updatedMission
            .details(UPDATED_DETAILS)
            .tenantId(UPDATED_TENANT_ID);
        MissionDTO missionDTO = missionMapper.toDto(updatedMission);

        restMissionMockMvc.perform(put("/api/missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missionDTO)))
            .andExpect(status().isOk());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
        Mission testMission = missionList.get(missionList.size() - 1);
        assertThat(testMission.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testMission.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingMission() throws Exception {
        int databaseSizeBeforeUpdate = missionRepository.findAll().size();

        // Create the Mission
        MissionDTO missionDTO = missionMapper.toDto(mission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMissionMockMvc.perform(put("/api/missions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(missionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mission in the database
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMission() throws Exception {
        // Initialize the database
        missionRepository.saveAndFlush(mission);

        int databaseSizeBeforeDelete = missionRepository.findAll().size();

        // Delete the mission
        restMissionMockMvc.perform(delete("/api/missions/{id}", mission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mission> missionList = missionRepository.findAll();
        assertThat(missionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
