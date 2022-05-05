package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.domain.Degree;
import com.myriadquest.kreiscms.domain.Department;
import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.domain.Course;
import com.myriadquest.kreiscms.repository.ExamTimeTableRepository;
import com.myriadquest.kreiscms.service.ExamTimeTableService;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ExamTimeTableMapper;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableCriteria;
import com.myriadquest.kreiscms.service.ExamTimeTableQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myriadquest.kreiscms.domain.enumeration.ExamType;
/**
 * Integration tests for the {@link ExamTimeTableResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExamTimeTableResourceIT {

    private static final ExamType DEFAULT_EXAM_TYPE = ExamType.FA;
    private static final ExamType UPDATED_EXAM_TYPE = ExamType.SA;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ExamTimeTableRepository examTimeTableRepository;

    @Autowired
    private ExamTimeTableMapper examTimeTableMapper;

    @Autowired
    private ExamTimeTableService examTimeTableService;

    @Autowired
    private ExamTimeTableQueryService examTimeTableQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamTimeTableMockMvc;

    private ExamTimeTable examTimeTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamTimeTable createEntity(EntityManager em) {
        ExamTimeTable examTimeTable = new ExamTimeTable()
            .examType(DEFAULT_EXAM_TYPE)
            .date(DEFAULT_DATE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .tenantId(DEFAULT_TENANT_ID);
        return examTimeTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamTimeTable createUpdatedEntity(EntityManager em) {
        ExamTimeTable examTimeTable = new ExamTimeTable()
            .examType(UPDATED_EXAM_TYPE)
            .date(UPDATED_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .tenantId(UPDATED_TENANT_ID);
        return examTimeTable;
    }

    @BeforeEach
    public void initTest() {
        examTimeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamTimeTable() throws Exception {
        int databaseSizeBeforeCreate = examTimeTableRepository.findAll().size();
        // Create the ExamTimeTable
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);
        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamTimeTable in the database
        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeCreate + 1);
        ExamTimeTable testExamTimeTable = examTimeTableList.get(examTimeTableList.size() - 1);
        assertThat(testExamTimeTable.getExamType()).isEqualTo(DEFAULT_EXAM_TYPE);
        assertThat(testExamTimeTable.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExamTimeTable.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testExamTimeTable.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testExamTimeTable.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createExamTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examTimeTableRepository.findAll().size();

        // Create the ExamTimeTable with an existing ID
        examTimeTable.setId(1L);
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamTimeTable in the database
        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkExamTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTimeTableRepository.findAll().size();
        // set the field null
        examTimeTable.setExamType(null);

        // Create the ExamTimeTable, which fails.
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);


        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTimeTableRepository.findAll().size();
        // set the field null
        examTimeTable.setDate(null);

        // Create the ExamTimeTable, which fails.
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);


        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTimeTableRepository.findAll().size();
        // set the field null
        examTimeTable.setStartTime(null);

        // Create the ExamTimeTable, which fails.
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);


        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examTimeTableRepository.findAll().size();
        // set the field null
        examTimeTable.setEndTime(null);

        // Create the ExamTimeTable, which fails.
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);


        restExamTimeTableMockMvc.perform(post("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamTimeTables() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examTimeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].examType").value(hasItem(DEFAULT_EXAM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getExamTimeTable() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get the examTimeTable
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables/{id}", examTimeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examTimeTable.getId().intValue()))
            .andExpect(jsonPath("$.examType").value(DEFAULT_EXAM_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getExamTimeTablesByIdFiltering() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        Long id = examTimeTable.getId();

        defaultExamTimeTableShouldBeFound("id.equals=" + id);
        defaultExamTimeTableShouldNotBeFound("id.notEquals=" + id);

        defaultExamTimeTableShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExamTimeTableShouldNotBeFound("id.greaterThan=" + id);

        defaultExamTimeTableShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExamTimeTableShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByExamTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where examType equals to DEFAULT_EXAM_TYPE
        defaultExamTimeTableShouldBeFound("examType.equals=" + DEFAULT_EXAM_TYPE);

        // Get all the examTimeTableList where examType equals to UPDATED_EXAM_TYPE
        defaultExamTimeTableShouldNotBeFound("examType.equals=" + UPDATED_EXAM_TYPE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByExamTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where examType not equals to DEFAULT_EXAM_TYPE
        defaultExamTimeTableShouldNotBeFound("examType.notEquals=" + DEFAULT_EXAM_TYPE);

        // Get all the examTimeTableList where examType not equals to UPDATED_EXAM_TYPE
        defaultExamTimeTableShouldBeFound("examType.notEquals=" + UPDATED_EXAM_TYPE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByExamTypeIsInShouldWork() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where examType in DEFAULT_EXAM_TYPE or UPDATED_EXAM_TYPE
        defaultExamTimeTableShouldBeFound("examType.in=" + DEFAULT_EXAM_TYPE + "," + UPDATED_EXAM_TYPE);

        // Get all the examTimeTableList where examType equals to UPDATED_EXAM_TYPE
        defaultExamTimeTableShouldNotBeFound("examType.in=" + UPDATED_EXAM_TYPE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByExamTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where examType is not null
        defaultExamTimeTableShouldBeFound("examType.specified=true");

        // Get all the examTimeTableList where examType is null
        defaultExamTimeTableShouldNotBeFound("examType.specified=false");
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date equals to DEFAULT_DATE
        defaultExamTimeTableShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date equals to UPDATED_DATE
        defaultExamTimeTableShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date not equals to DEFAULT_DATE
        defaultExamTimeTableShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date not equals to UPDATED_DATE
        defaultExamTimeTableShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date in DEFAULT_DATE or UPDATED_DATE
        defaultExamTimeTableShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the examTimeTableList where date equals to UPDATED_DATE
        defaultExamTimeTableShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date is not null
        defaultExamTimeTableShouldBeFound("date.specified=true");

        // Get all the examTimeTableList where date is null
        defaultExamTimeTableShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date is greater than or equal to DEFAULT_DATE
        defaultExamTimeTableShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date is greater than or equal to UPDATED_DATE
        defaultExamTimeTableShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date is less than or equal to DEFAULT_DATE
        defaultExamTimeTableShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date is less than or equal to SMALLER_DATE
        defaultExamTimeTableShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date is less than DEFAULT_DATE
        defaultExamTimeTableShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date is less than UPDATED_DATE
        defaultExamTimeTableShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where date is greater than DEFAULT_DATE
        defaultExamTimeTableShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the examTimeTableList where date is greater than SMALLER_DATE
        defaultExamTimeTableShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where startTime equals to DEFAULT_START_TIME
        defaultExamTimeTableShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the examTimeTableList where startTime equals to UPDATED_START_TIME
        defaultExamTimeTableShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where startTime not equals to DEFAULT_START_TIME
        defaultExamTimeTableShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the examTimeTableList where startTime not equals to UPDATED_START_TIME
        defaultExamTimeTableShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultExamTimeTableShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the examTimeTableList where startTime equals to UPDATED_START_TIME
        defaultExamTimeTableShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where startTime is not null
        defaultExamTimeTableShouldBeFound("startTime.specified=true");

        // Get all the examTimeTableList where startTime is null
        defaultExamTimeTableShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where endTime equals to DEFAULT_END_TIME
        defaultExamTimeTableShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the examTimeTableList where endTime equals to UPDATED_END_TIME
        defaultExamTimeTableShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where endTime not equals to DEFAULT_END_TIME
        defaultExamTimeTableShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the examTimeTableList where endTime not equals to UPDATED_END_TIME
        defaultExamTimeTableShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultExamTimeTableShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the examTimeTableList where endTime equals to UPDATED_END_TIME
        defaultExamTimeTableShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where endTime is not null
        defaultExamTimeTableShouldBeFound("endTime.specified=true");

        // Get all the examTimeTableList where endTime is null
        defaultExamTimeTableShouldNotBeFound("endTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId equals to DEFAULT_TENANT_ID
        defaultExamTimeTableShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the examTimeTableList where tenantId equals to UPDATED_TENANT_ID
        defaultExamTimeTableShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId not equals to DEFAULT_TENANT_ID
        defaultExamTimeTableShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the examTimeTableList where tenantId not equals to UPDATED_TENANT_ID
        defaultExamTimeTableShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultExamTimeTableShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the examTimeTableList where tenantId equals to UPDATED_TENANT_ID
        defaultExamTimeTableShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId is not null
        defaultExamTimeTableShouldBeFound("tenantId.specified=true");

        // Get all the examTimeTableList where tenantId is null
        defaultExamTimeTableShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId contains DEFAULT_TENANT_ID
        defaultExamTimeTableShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the examTimeTableList where tenantId contains UPDATED_TENANT_ID
        defaultExamTimeTableShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllExamTimeTablesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        // Get all the examTimeTableList where tenantId does not contain DEFAULT_TENANT_ID
        defaultExamTimeTableShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the examTimeTableList where tenantId does not contain UPDATED_TENANT_ID
        defaultExamTimeTableShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByAcademicCalendarIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);
        AcademicCalendar academicCalendar = AcademicCalendarResourceIT.createEntity(em);
        em.persist(academicCalendar);
        em.flush();
        examTimeTable.setAcademicCalendar(academicCalendar);
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Long academicCalendarId = academicCalendar.getId();

        // Get all the examTimeTableList where academicCalendar equals to academicCalendarId
        defaultExamTimeTableShouldBeFound("academicCalendarId.equals=" + academicCalendarId);

        // Get all the examTimeTableList where academicCalendar equals to academicCalendarId + 1
        defaultExamTimeTableShouldNotBeFound("academicCalendarId.equals=" + (academicCalendarId + 1));
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Degree degree = DegreeResourceIT.createEntity(em);
        em.persist(degree);
        em.flush();
        examTimeTable.setDegree(degree);
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Long degreeId = degree.getId();

        // Get all the examTimeTableList where degree equals to degreeId
        defaultExamTimeTableShouldBeFound("degreeId.equals=" + degreeId);

        // Get all the examTimeTableList where degree equals to degreeId + 1
        defaultExamTimeTableShouldNotBeFound("degreeId.equals=" + (degreeId + 1));
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        examTimeTable.setDepartment(department);
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Long departmentId = department.getId();

        // Get all the examTimeTableList where department equals to departmentId
        defaultExamTimeTableShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the examTimeTableList where department equals to departmentId + 1
        defaultExamTimeTableShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Term term = TermResourceIT.createEntity(em);
        em.persist(term);
        em.flush();
        examTimeTable.setTerm(term);
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Long termId = term.getId();

        // Get all the examTimeTableList where term equals to termId
        defaultExamTimeTableShouldBeFound("termId.equals=" + termId);

        // Get all the examTimeTableList where term equals to termId + 1
        defaultExamTimeTableShouldNotBeFound("termId.equals=" + (termId + 1));
    }


    @Test
    @Transactional
    public void getAllExamTimeTablesByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Course course = CourseResourceIT.createEntity(em);
        em.persist(course);
        em.flush();
        examTimeTable.setCourse(course);
        examTimeTableRepository.saveAndFlush(examTimeTable);
        Long courseId = course.getId();

        // Get all the examTimeTableList where course equals to courseId
        defaultExamTimeTableShouldBeFound("courseId.equals=" + courseId);

        // Get all the examTimeTableList where course equals to courseId + 1
        defaultExamTimeTableShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExamTimeTableShouldBeFound(String filter) throws Exception {
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examTimeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].examType").value(hasItem(DEFAULT_EXAM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExamTimeTableShouldNotBeFound(String filter) throws Exception {
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingExamTimeTable() throws Exception {
        // Get the examTimeTable
        restExamTimeTableMockMvc.perform(get("/api/exam-time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamTimeTable() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        int databaseSizeBeforeUpdate = examTimeTableRepository.findAll().size();

        // Update the examTimeTable
        ExamTimeTable updatedExamTimeTable = examTimeTableRepository.findById(examTimeTable.getId()).get();
        // Disconnect from session so that the updates on updatedExamTimeTable are not directly saved in db
        em.detach(updatedExamTimeTable);
        updatedExamTimeTable
            .examType(UPDATED_EXAM_TYPE)
            .date(UPDATED_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .tenantId(UPDATED_TENANT_ID);
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(updatedExamTimeTable);

        restExamTimeTableMockMvc.perform(put("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isOk());

        // Validate the ExamTimeTable in the database
        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeUpdate);
        ExamTimeTable testExamTimeTable = examTimeTableList.get(examTimeTableList.size() - 1);
        assertThat(testExamTimeTable.getExamType()).isEqualTo(UPDATED_EXAM_TYPE);
        assertThat(testExamTimeTable.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExamTimeTable.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testExamTimeTable.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testExamTimeTable.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingExamTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = examTimeTableRepository.findAll().size();

        // Create the ExamTimeTable
        ExamTimeTableDTO examTimeTableDTO = examTimeTableMapper.toDto(examTimeTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamTimeTableMockMvc.perform(put("/api/exam-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(examTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamTimeTable in the database
        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamTimeTable() throws Exception {
        // Initialize the database
        examTimeTableRepository.saveAndFlush(examTimeTable);

        int databaseSizeBeforeDelete = examTimeTableRepository.findAll().size();

        // Delete the examTimeTable
        restExamTimeTableMockMvc.perform(delete("/api/exam-time-tables/{id}", examTimeTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamTimeTable> examTimeTableList = examTimeTableRepository.findAll();
        assertThat(examTimeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
