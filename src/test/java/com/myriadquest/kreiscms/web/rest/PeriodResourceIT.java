package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Period;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.repository.PeriodRepository;
import com.myriadquest.kreiscms.service.PeriodService;
import com.myriadquest.kreiscms.service.dto.PeriodDTO;
import com.myriadquest.kreiscms.service.mapper.PeriodMapper;
import com.myriadquest.kreiscms.service.dto.PeriodCriteria;
import com.myriadquest.kreiscms.service.PeriodQueryService;

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
 * Integration tests for the {@link PeriodResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeriodResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_END_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private PeriodMapper periodMapper;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private PeriodQueryService periodQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodMockMvc;

    private Period period;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Period createEntity(EntityManager em) {
        Period period = new Period()
            .number(DEFAULT_NUMBER)
            .label(DEFAULT_LABEL)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .tenantId(DEFAULT_TENANT_ID);
        return period;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Period createUpdatedEntity(EntityManager em) {
        Period period = new Period()
            .number(UPDATED_NUMBER)
            .label(UPDATED_LABEL)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .tenantId(UPDATED_TENANT_ID);
        return period;
    }

    @BeforeEach
    public void initTest() {
        period = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriod() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();
        // Create the Period
        PeriodDTO periodDTO = periodMapper.toDto(period);
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isCreated());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate + 1);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPeriod.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPeriod.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testPeriod.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testPeriod.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createPeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodRepository.findAll().size();

        // Create the Period with an existing ID
        period.setId(1L);
        PeriodDTO periodDTO = periodMapper.toDto(period);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setNumber(null);

        // Create the Period, which fails.
        PeriodDTO periodDTO = periodMapper.toDto(period);


        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setLabel(null);

        // Create the Period, which fails.
        PeriodDTO periodDTO = periodMapper.toDto(period);


        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setStartTime(null);

        // Create the Period, which fails.
        PeriodDTO periodDTO = periodMapper.toDto(period);


        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodRepository.findAll().size();
        // set the field null
        period.setEndTime(null);

        // Create the Period, which fails.
        PeriodDTO periodDTO = periodMapper.toDto(period);


        restPeriodMockMvc.perform(post("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriods() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(period.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getPeriod() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", period.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(period.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getPeriodsByIdFiltering() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        Long id = period.getId();

        defaultPeriodShouldBeFound("id.equals=" + id);
        defaultPeriodShouldNotBeFound("id.notEquals=" + id);

        defaultPeriodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPeriodShouldNotBeFound("id.greaterThan=" + id);

        defaultPeriodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPeriodShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPeriodsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number equals to DEFAULT_NUMBER
        defaultPeriodShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the periodList where number equals to UPDATED_NUMBER
        defaultPeriodShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number not equals to DEFAULT_NUMBER
        defaultPeriodShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the periodList where number not equals to UPDATED_NUMBER
        defaultPeriodShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultPeriodShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the periodList where number equals to UPDATED_NUMBER
        defaultPeriodShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number is not null
        defaultPeriodShouldBeFound("number.specified=true");

        // Get all the periodList where number is null
        defaultPeriodShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number is greater than or equal to DEFAULT_NUMBER
        defaultPeriodShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the periodList where number is greater than or equal to UPDATED_NUMBER
        defaultPeriodShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number is less than or equal to DEFAULT_NUMBER
        defaultPeriodShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the periodList where number is less than or equal to SMALLER_NUMBER
        defaultPeriodShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number is less than DEFAULT_NUMBER
        defaultPeriodShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the periodList where number is less than UPDATED_NUMBER
        defaultPeriodShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPeriodsByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where number is greater than DEFAULT_NUMBER
        defaultPeriodShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the periodList where number is greater than SMALLER_NUMBER
        defaultPeriodShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPeriodsByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label equals to DEFAULT_LABEL
        defaultPeriodShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the periodList where label equals to UPDATED_LABEL
        defaultPeriodShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllPeriodsByLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label not equals to DEFAULT_LABEL
        defaultPeriodShouldNotBeFound("label.notEquals=" + DEFAULT_LABEL);

        // Get all the periodList where label not equals to UPDATED_LABEL
        defaultPeriodShouldBeFound("label.notEquals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllPeriodsByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultPeriodShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the periodList where label equals to UPDATED_LABEL
        defaultPeriodShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllPeriodsByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label is not null
        defaultPeriodShouldBeFound("label.specified=true");

        // Get all the periodList where label is null
        defaultPeriodShouldNotBeFound("label.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeriodsByLabelContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label contains DEFAULT_LABEL
        defaultPeriodShouldBeFound("label.contains=" + DEFAULT_LABEL);

        // Get all the periodList where label contains UPDATED_LABEL
        defaultPeriodShouldNotBeFound("label.contains=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllPeriodsByLabelNotContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where label does not contain DEFAULT_LABEL
        defaultPeriodShouldNotBeFound("label.doesNotContain=" + DEFAULT_LABEL);

        // Get all the periodList where label does not contain UPDATED_LABEL
        defaultPeriodShouldBeFound("label.doesNotContain=" + UPDATED_LABEL);
    }


    @Test
    @Transactional
    public void getAllPeriodsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime equals to DEFAULT_START_TIME
        defaultPeriodShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the periodList where startTime equals to UPDATED_START_TIME
        defaultPeriodShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime not equals to DEFAULT_START_TIME
        defaultPeriodShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the periodList where startTime not equals to UPDATED_START_TIME
        defaultPeriodShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultPeriodShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the periodList where startTime equals to UPDATED_START_TIME
        defaultPeriodShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime is not null
        defaultPeriodShouldBeFound("startTime.specified=true");

        // Get all the periodList where startTime is null
        defaultPeriodShouldNotBeFound("startTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeriodsByStartTimeContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime contains DEFAULT_START_TIME
        defaultPeriodShouldBeFound("startTime.contains=" + DEFAULT_START_TIME);

        // Get all the periodList where startTime contains UPDATED_START_TIME
        defaultPeriodShouldNotBeFound("startTime.contains=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByStartTimeNotContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where startTime does not contain DEFAULT_START_TIME
        defaultPeriodShouldNotBeFound("startTime.doesNotContain=" + DEFAULT_START_TIME);

        // Get all the periodList where startTime does not contain UPDATED_START_TIME
        defaultPeriodShouldBeFound("startTime.doesNotContain=" + UPDATED_START_TIME);
    }


    @Test
    @Transactional
    public void getAllPeriodsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime equals to DEFAULT_END_TIME
        defaultPeriodShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the periodList where endTime equals to UPDATED_END_TIME
        defaultPeriodShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime not equals to DEFAULT_END_TIME
        defaultPeriodShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the periodList where endTime not equals to UPDATED_END_TIME
        defaultPeriodShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultPeriodShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the periodList where endTime equals to UPDATED_END_TIME
        defaultPeriodShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime is not null
        defaultPeriodShouldBeFound("endTime.specified=true");

        // Get all the periodList where endTime is null
        defaultPeriodShouldNotBeFound("endTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeriodsByEndTimeContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime contains DEFAULT_END_TIME
        defaultPeriodShouldBeFound("endTime.contains=" + DEFAULT_END_TIME);

        // Get all the periodList where endTime contains UPDATED_END_TIME
        defaultPeriodShouldNotBeFound("endTime.contains=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllPeriodsByEndTimeNotContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where endTime does not contain DEFAULT_END_TIME
        defaultPeriodShouldNotBeFound("endTime.doesNotContain=" + DEFAULT_END_TIME);

        // Get all the periodList where endTime does not contain UPDATED_END_TIME
        defaultPeriodShouldBeFound("endTime.doesNotContain=" + UPDATED_END_TIME);
    }


    @Test
    @Transactional
    public void getAllPeriodsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId equals to DEFAULT_TENANT_ID
        defaultPeriodShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the periodList where tenantId equals to UPDATED_TENANT_ID
        defaultPeriodShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPeriodsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId not equals to DEFAULT_TENANT_ID
        defaultPeriodShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the periodList where tenantId not equals to UPDATED_TENANT_ID
        defaultPeriodShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPeriodsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultPeriodShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the periodList where tenantId equals to UPDATED_TENANT_ID
        defaultPeriodShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPeriodsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId is not null
        defaultPeriodShouldBeFound("tenantId.specified=true");

        // Get all the periodList where tenantId is null
        defaultPeriodShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPeriodsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId contains DEFAULT_TENANT_ID
        defaultPeriodShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the periodList where tenantId contains UPDATED_TENANT_ID
        defaultPeriodShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPeriodsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        // Get all the periodList where tenantId does not contain DEFAULT_TENANT_ID
        defaultPeriodShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the periodList where tenantId does not contain UPDATED_TENANT_ID
        defaultPeriodShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllPeriodsByClassTimeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);
        ClassTimeTable classTimeTable = ClassTimeTableResourceIT.createEntity(em);
        em.persist(classTimeTable);
        em.flush();
        period.addClassTimeTable(classTimeTable);
        periodRepository.saveAndFlush(period);
        Long classTimeTableId = classTimeTable.getId();

        // Get all the periodList where classTimeTable equals to classTimeTableId
        defaultPeriodShouldBeFound("classTimeTableId.equals=" + classTimeTableId);

        // Get all the periodList where classTimeTable equals to classTimeTableId + 1
        defaultPeriodShouldNotBeFound("classTimeTableId.equals=" + (classTimeTableId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeriodShouldBeFound(String filter) throws Exception {
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(period.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restPeriodMockMvc.perform(get("/api/periods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeriodShouldNotBeFound(String filter) throws Exception {
        restPeriodMockMvc.perform(get("/api/periods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodMockMvc.perform(get("/api/periods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPeriod() throws Exception {
        // Get the period
        restPeriodMockMvc.perform(get("/api/periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriod() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Update the period
        Period updatedPeriod = periodRepository.findById(period.getId()).get();
        // Disconnect from session so that the updates on updatedPeriod are not directly saved in db
        em.detach(updatedPeriod);
        updatedPeriod
            .number(UPDATED_NUMBER)
            .label(UPDATED_LABEL)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .tenantId(UPDATED_TENANT_ID);
        PeriodDTO periodDTO = periodMapper.toDto(updatedPeriod);

        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isOk());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate);
        Period testPeriod = periodList.get(periodList.size() - 1);
        assertThat(testPeriod.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPeriod.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPeriod.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testPeriod.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testPeriod.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriod() throws Exception {
        int databaseSizeBeforeUpdate = periodRepository.findAll().size();

        // Create the Period
        PeriodDTO periodDTO = periodMapper.toDto(period);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodMockMvc.perform(put("/api/periods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(periodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Period in the database
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriod() throws Exception {
        // Initialize the database
        periodRepository.saveAndFlush(period);

        int databaseSizeBeforeDelete = periodRepository.findAll().size();

        // Delete the period
        restPeriodMockMvc.perform(delete("/api/periods/{id}", period.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Period> periodList = periodRepository.findAll();
        assertThat(periodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
