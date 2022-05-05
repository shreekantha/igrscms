package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ClassTimeTableConfig;
import com.myriadquest.kreiscms.repository.ClassTimeTableConfigRepository;
import com.myriadquest.kreiscms.service.ClassTimeTableConfigService;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableConfigMapper;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigCriteria;
import com.myriadquest.kreiscms.service.ClassTimeTableConfigQueryService;

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

import com.myriadquest.kreiscms.domain.enumeration.TimeTableGenType;
/**
 * Integration tests for the {@link ClassTimeTableConfigResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassTimeTableConfigResourceIT {

    private static final TimeTableGenType DEFAULT_TIME_TABLE_GEN_TYPE = TimeTableGenType.ALL_DAYS_EXCEPT_SAT;
    private static final TimeTableGenType UPDATED_TIME_TABLE_GEN_TYPE = TimeTableGenType.ALL_DAYS;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ClassTimeTableConfigRepository classTimeTableConfigRepository;

    @Autowired
    private ClassTimeTableConfigMapper classTimeTableConfigMapper;

    @Autowired
    private ClassTimeTableConfigService classTimeTableConfigService;

    @Autowired
    private ClassTimeTableConfigQueryService classTimeTableConfigQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassTimeTableConfigMockMvc;

    private ClassTimeTableConfig classTimeTableConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTableConfig createEntity(EntityManager em) {
        ClassTimeTableConfig classTimeTableConfig = new ClassTimeTableConfig()
            .timeTableGenType(DEFAULT_TIME_TABLE_GEN_TYPE)
            .tenantId(DEFAULT_TENANT_ID);
        return classTimeTableConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTableConfig createUpdatedEntity(EntityManager em) {
        ClassTimeTableConfig classTimeTableConfig = new ClassTimeTableConfig()
            .timeTableGenType(UPDATED_TIME_TABLE_GEN_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        return classTimeTableConfig;
    }

    @BeforeEach
    public void initTest() {
        classTimeTableConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassTimeTableConfig() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableConfigRepository.findAll().size();
        // Create the ClassTimeTableConfig
        ClassTimeTableConfigDTO classTimeTableConfigDTO = classTimeTableConfigMapper.toDto(classTimeTableConfig);
        restClassTimeTableConfigMockMvc.perform(post("/api/class-time-table-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassTimeTableConfig in the database
        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ClassTimeTableConfig testClassTimeTableConfig = classTimeTableConfigList.get(classTimeTableConfigList.size() - 1);
        assertThat(testClassTimeTableConfig.getTimeTableGenType()).isEqualTo(DEFAULT_TIME_TABLE_GEN_TYPE);
        assertThat(testClassTimeTableConfig.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createClassTimeTableConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableConfigRepository.findAll().size();

        // Create the ClassTimeTableConfig with an existing ID
        classTimeTableConfig.setId(1L);
        ClassTimeTableConfigDTO classTimeTableConfigDTO = classTimeTableConfigMapper.toDto(classTimeTableConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassTimeTableConfigMockMvc.perform(post("/api/class-time-table-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTableConfig in the database
        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimeTableGenTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = classTimeTableConfigRepository.findAll().size();
        // set the field null
        classTimeTableConfig.setTimeTableGenType(null);

        // Create the ClassTimeTableConfig, which fails.
        ClassTimeTableConfigDTO classTimeTableConfigDTO = classTimeTableConfigMapper.toDto(classTimeTableConfig);


        restClassTimeTableConfigMockMvc.perform(post("/api/class-time-table-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigs() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTimeTableConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeTableGenType").value(hasItem(DEFAULT_TIME_TABLE_GEN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getClassTimeTableConfig() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get the classTimeTableConfig
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs/{id}", classTimeTableConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classTimeTableConfig.getId().intValue()))
            .andExpect(jsonPath("$.timeTableGenType").value(DEFAULT_TIME_TABLE_GEN_TYPE.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getClassTimeTableConfigsByIdFiltering() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        Long id = classTimeTableConfig.getId();

        defaultClassTimeTableConfigShouldBeFound("id.equals=" + id);
        defaultClassTimeTableConfigShouldNotBeFound("id.notEquals=" + id);

        defaultClassTimeTableConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassTimeTableConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultClassTimeTableConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassTimeTableConfigShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTimeTableGenTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where timeTableGenType equals to DEFAULT_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldBeFound("timeTableGenType.equals=" + DEFAULT_TIME_TABLE_GEN_TYPE);

        // Get all the classTimeTableConfigList where timeTableGenType equals to UPDATED_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldNotBeFound("timeTableGenType.equals=" + UPDATED_TIME_TABLE_GEN_TYPE);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTimeTableGenTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where timeTableGenType not equals to DEFAULT_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldNotBeFound("timeTableGenType.notEquals=" + DEFAULT_TIME_TABLE_GEN_TYPE);

        // Get all the classTimeTableConfigList where timeTableGenType not equals to UPDATED_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldBeFound("timeTableGenType.notEquals=" + UPDATED_TIME_TABLE_GEN_TYPE);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTimeTableGenTypeIsInShouldWork() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where timeTableGenType in DEFAULT_TIME_TABLE_GEN_TYPE or UPDATED_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldBeFound("timeTableGenType.in=" + DEFAULT_TIME_TABLE_GEN_TYPE + "," + UPDATED_TIME_TABLE_GEN_TYPE);

        // Get all the classTimeTableConfigList where timeTableGenType equals to UPDATED_TIME_TABLE_GEN_TYPE
        defaultClassTimeTableConfigShouldNotBeFound("timeTableGenType.in=" + UPDATED_TIME_TABLE_GEN_TYPE);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTimeTableGenTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where timeTableGenType is not null
        defaultClassTimeTableConfigShouldBeFound("timeTableGenType.specified=true");

        // Get all the classTimeTableConfigList where timeTableGenType is null
        defaultClassTimeTableConfigShouldNotBeFound("timeTableGenType.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId equals to DEFAULT_TENANT_ID
        defaultClassTimeTableConfigShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableConfigList where tenantId equals to UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId not equals to DEFAULT_TENANT_ID
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableConfigList where tenantId not equals to UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the classTimeTableConfigList where tenantId equals to UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId is not null
        defaultClassTimeTableConfigShouldBeFound("tenantId.specified=true");

        // Get all the classTimeTableConfigList where tenantId is null
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId contains DEFAULT_TENANT_ID
        defaultClassTimeTableConfigShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableConfigList where tenantId contains UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllClassTimeTableConfigsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        // Get all the classTimeTableConfigList where tenantId does not contain DEFAULT_TENANT_ID
        defaultClassTimeTableConfigShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the classTimeTableConfigList where tenantId does not contain UPDATED_TENANT_ID
        defaultClassTimeTableConfigShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassTimeTableConfigShouldBeFound(String filter) throws Exception {
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTimeTableConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeTableGenType").value(hasItem(DEFAULT_TIME_TABLE_GEN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassTimeTableConfigShouldNotBeFound(String filter) throws Exception {
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClassTimeTableConfig() throws Exception {
        // Get the classTimeTableConfig
        restClassTimeTableConfigMockMvc.perform(get("/api/class-time-table-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassTimeTableConfig() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        int databaseSizeBeforeUpdate = classTimeTableConfigRepository.findAll().size();

        // Update the classTimeTableConfig
        ClassTimeTableConfig updatedClassTimeTableConfig = classTimeTableConfigRepository.findById(classTimeTableConfig.getId()).get();
        // Disconnect from session so that the updates on updatedClassTimeTableConfig are not directly saved in db
        em.detach(updatedClassTimeTableConfig);
        updatedClassTimeTableConfig
            .timeTableGenType(UPDATED_TIME_TABLE_GEN_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        ClassTimeTableConfigDTO classTimeTableConfigDTO = classTimeTableConfigMapper.toDto(updatedClassTimeTableConfig);

        restClassTimeTableConfigMockMvc.perform(put("/api/class-time-table-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ClassTimeTableConfig in the database
        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeUpdate);
        ClassTimeTableConfig testClassTimeTableConfig = classTimeTableConfigList.get(classTimeTableConfigList.size() - 1);
        assertThat(testClassTimeTableConfig.getTimeTableGenType()).isEqualTo(UPDATED_TIME_TABLE_GEN_TYPE);
        assertThat(testClassTimeTableConfig.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingClassTimeTableConfig() throws Exception {
        int databaseSizeBeforeUpdate = classTimeTableConfigRepository.findAll().size();

        // Create the ClassTimeTableConfig
        ClassTimeTableConfigDTO classTimeTableConfigDTO = classTimeTableConfigMapper.toDto(classTimeTableConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassTimeTableConfigMockMvc.perform(put("/api/class-time-table-configs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTableConfig in the database
        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassTimeTableConfig() throws Exception {
        // Initialize the database
        classTimeTableConfigRepository.saveAndFlush(classTimeTableConfig);

        int databaseSizeBeforeDelete = classTimeTableConfigRepository.findAll().size();

        // Delete the classTimeTableConfig
        restClassTimeTableConfigMockMvc.perform(delete("/api/class-time-table-configs/{id}", classTimeTableConfig.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassTimeTableConfig> classTimeTableConfigList = classTimeTableConfigRepository.findAll();
        assertThat(classTimeTableConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
