package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Mission;
import com.myriadquest.kreiscms.repository.MissionRepository;
import com.myriadquest.kreiscms.service.MissionService;
import com.myriadquest.kreiscms.service.dto.MissionDTO;
import com.myriadquest.kreiscms.service.mapper.MissionMapper;

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

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private MissionService missionService;

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
            .details(DEFAULT_DETAILS);
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
            .details(UPDATED_DETAILS);
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
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)));
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
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS));
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
            .details(UPDATED_DETAILS);
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
