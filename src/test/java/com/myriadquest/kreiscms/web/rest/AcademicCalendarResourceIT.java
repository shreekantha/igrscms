package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.domain.Degree;
import com.myriadquest.kreiscms.repository.AcademicCalendarRepository;
import com.myriadquest.kreiscms.service.AcademicCalendarService;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;
import com.myriadquest.kreiscms.service.mapper.AcademicCalendarMapper;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarCriteria;
import com.myriadquest.kreiscms.service.AcademicCalendarQueryService;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AcademicCalendarResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AcademicCalendarResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private AcademicCalendarRepository academicCalendarRepository;

    @Autowired
    private AcademicCalendarMapper academicCalendarMapper;

    @Autowired
    private AcademicCalendarService academicCalendarService;

    @Autowired
    private AcademicCalendarQueryService academicCalendarQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicCalendarMockMvc;

    private AcademicCalendar academicCalendar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicCalendar createEntity(EntityManager em) {
        AcademicCalendar academicCalendar = new AcademicCalendar()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .active(DEFAULT_ACTIVE)
            .tenantId(DEFAULT_TENANT_ID);
        return academicCalendar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicCalendar createUpdatedEntity(EntityManager em) {
        AcademicCalendar academicCalendar = new AcademicCalendar()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .active(UPDATED_ACTIVE)
            .tenantId(UPDATED_TENANT_ID);
        return academicCalendar;
    }

    @BeforeEach
    public void initTest() {
        academicCalendar = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicCalendar() throws Exception {
        int databaseSizeBeforeCreate = academicCalendarRepository.findAll().size();
        // Create the AcademicCalendar
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);
        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicCalendar in the database
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicCalendar testAcademicCalendar = academicCalendarList.get(academicCalendarList.size() - 1);
        assertThat(testAcademicCalendar.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAcademicCalendar.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAcademicCalendar.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testAcademicCalendar.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAcademicCalendar.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createAcademicCalendarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicCalendarRepository.findAll().size();

        // Create the AcademicCalendar with an existing ID
        academicCalendar.setId(1L);
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicCalendar in the database
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicCalendarRepository.findAll().size();
        // set the field null
        academicCalendar.setStartDate(null);

        // Create the AcademicCalendar, which fails.
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);


        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicCalendarRepository.findAll().size();
        // set the field null
        academicCalendar.setEndDate(null);

        // Create the AcademicCalendar, which fails.
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);


        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicCalendarRepository.findAll().size();
        // set the field null
        academicCalendar.setAcademicYear(null);

        // Create the AcademicCalendar, which fails.
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);


        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicCalendarRepository.findAll().size();
        // set the field null
        academicCalendar.setActive(null);

        // Create the AcademicCalendar, which fails.
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);


        restAcademicCalendarMockMvc.perform(post("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendars() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicCalendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getAcademicCalendar() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get the academicCalendar
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars/{id}", academicCalendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicCalendar.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getAcademicCalendarsByIdFiltering() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        Long id = academicCalendar.getId();

        defaultAcademicCalendarShouldBeFound("id.equals=" + id);
        defaultAcademicCalendarShouldNotBeFound("id.notEquals=" + id);

        defaultAcademicCalendarShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAcademicCalendarShouldNotBeFound("id.greaterThan=" + id);

        defaultAcademicCalendarShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAcademicCalendarShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate equals to DEFAULT_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate equals to UPDATED_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate not equals to DEFAULT_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate not equals to UPDATED_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the academicCalendarList where startDate equals to UPDATED_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate is not null
        defaultAcademicCalendarShouldBeFound("startDate.specified=true");

        // Get all the academicCalendarList where startDate is null
        defaultAcademicCalendarShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate is greater than or equal to UPDATED_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate is less than or equal to DEFAULT_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate is less than or equal to SMALLER_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate is less than DEFAULT_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate is less than UPDATED_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where startDate is greater than DEFAULT_START_DATE
        defaultAcademicCalendarShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the academicCalendarList where startDate is greater than SMALLER_START_DATE
        defaultAcademicCalendarShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate equals to DEFAULT_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate equals to UPDATED_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate not equals to DEFAULT_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate not equals to UPDATED_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the academicCalendarList where endDate equals to UPDATED_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate is not null
        defaultAcademicCalendarShouldBeFound("endDate.specified=true");

        // Get all the academicCalendarList where endDate is null
        defaultAcademicCalendarShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate is greater than or equal to UPDATED_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate is less than or equal to DEFAULT_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate is less than or equal to SMALLER_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate is less than DEFAULT_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate is less than UPDATED_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where endDate is greater than DEFAULT_END_DATE
        defaultAcademicCalendarShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the academicCalendarList where endDate is greater than SMALLER_END_DATE
        defaultAcademicCalendarShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear equals to DEFAULT_ACADEMIC_YEAR
        defaultAcademicCalendarShouldBeFound("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the academicCalendarList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldNotBeFound("academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear not equals to DEFAULT_ACADEMIC_YEAR
        defaultAcademicCalendarShouldNotBeFound("academicYear.notEquals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the academicCalendarList where academicYear not equals to UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldBeFound("academicYear.notEquals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear in DEFAULT_ACADEMIC_YEAR or UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldBeFound("academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR);

        // Get all the academicCalendarList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldNotBeFound("academicYear.in=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear is not null
        defaultAcademicCalendarShouldBeFound("academicYear.specified=true");

        // Get all the academicCalendarList where academicYear is null
        defaultAcademicCalendarShouldNotBeFound("academicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear contains DEFAULT_ACADEMIC_YEAR
        defaultAcademicCalendarShouldBeFound("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the academicCalendarList where academicYear contains UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldNotBeFound("academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where academicYear does not contain DEFAULT_ACADEMIC_YEAR
        defaultAcademicCalendarShouldNotBeFound("academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the academicCalendarList where academicYear does not contain UPDATED_ACADEMIC_YEAR
        defaultAcademicCalendarShouldBeFound("academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where active equals to DEFAULT_ACTIVE
        defaultAcademicCalendarShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the academicCalendarList where active equals to UPDATED_ACTIVE
        defaultAcademicCalendarShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where active not equals to DEFAULT_ACTIVE
        defaultAcademicCalendarShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the academicCalendarList where active not equals to UPDATED_ACTIVE
        defaultAcademicCalendarShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAcademicCalendarShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the academicCalendarList where active equals to UPDATED_ACTIVE
        defaultAcademicCalendarShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where active is not null
        defaultAcademicCalendarShouldBeFound("active.specified=true");

        // Get all the academicCalendarList where active is null
        defaultAcademicCalendarShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId equals to DEFAULT_TENANT_ID
        defaultAcademicCalendarShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the academicCalendarList where tenantId equals to UPDATED_TENANT_ID
        defaultAcademicCalendarShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId not equals to DEFAULT_TENANT_ID
        defaultAcademicCalendarShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the academicCalendarList where tenantId not equals to UPDATED_TENANT_ID
        defaultAcademicCalendarShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultAcademicCalendarShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the academicCalendarList where tenantId equals to UPDATED_TENANT_ID
        defaultAcademicCalendarShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId is not null
        defaultAcademicCalendarShouldBeFound("tenantId.specified=true");

        // Get all the academicCalendarList where tenantId is null
        defaultAcademicCalendarShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId contains DEFAULT_TENANT_ID
        defaultAcademicCalendarShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the academicCalendarList where tenantId contains UPDATED_TENANT_ID
        defaultAcademicCalendarShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAcademicCalendarsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        // Get all the academicCalendarList where tenantId does not contain DEFAULT_TENANT_ID
        defaultAcademicCalendarShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the academicCalendarList where tenantId does not contain UPDATED_TENANT_ID
        defaultAcademicCalendarShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByClassTimeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);
        ClassTimeTable classTimeTable = ClassTimeTableResourceIT.createEntity(em);
        em.persist(classTimeTable);
        em.flush();
        academicCalendar.addClassTimeTable(classTimeTable);
        academicCalendarRepository.saveAndFlush(academicCalendar);
        Long classTimeTableId = classTimeTable.getId();

        // Get all the academicCalendarList where classTimeTable equals to classTimeTableId
        defaultAcademicCalendarShouldBeFound("classTimeTableId.equals=" + classTimeTableId);

        // Get all the academicCalendarList where classTimeTable equals to classTimeTableId + 1
        defaultAcademicCalendarShouldNotBeFound("classTimeTableId.equals=" + (classTimeTableId + 1));
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByExamTimeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);
        ExamTimeTable examTimeTable = ExamTimeTableResourceIT.createEntity(em);
        em.persist(examTimeTable);
        em.flush();
        academicCalendar.addExamTimeTable(examTimeTable);
        academicCalendarRepository.saveAndFlush(academicCalendar);
        Long examTimeTableId = examTimeTable.getId();

        // Get all the academicCalendarList where examTimeTable equals to examTimeTableId
        defaultAcademicCalendarShouldBeFound("examTimeTableId.equals=" + examTimeTableId);

        // Get all the academicCalendarList where examTimeTable equals to examTimeTableId + 1
        defaultAcademicCalendarShouldNotBeFound("examTimeTableId.equals=" + (examTimeTableId + 1));
    }


    @Test
    @Transactional
    public void getAllAcademicCalendarsByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);
        Degree degree = DegreeResourceIT.createEntity(em);
        em.persist(degree);
        em.flush();
        academicCalendar.setDegree(degree);
        academicCalendarRepository.saveAndFlush(academicCalendar);
        Long degreeId = degree.getId();

        // Get all the academicCalendarList where degree equals to degreeId
        defaultAcademicCalendarShouldBeFound("degreeId.equals=" + degreeId);

        // Get all the academicCalendarList where degree equals to degreeId + 1
        defaultAcademicCalendarShouldNotBeFound("degreeId.equals=" + (degreeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAcademicCalendarShouldBeFound(String filter) throws Exception {
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicCalendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAcademicCalendarShouldNotBeFound(String filter) throws Exception {
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicCalendar() throws Exception {
        // Get the academicCalendar
        restAcademicCalendarMockMvc.perform(get("/api/academic-calendars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicCalendar() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        int databaseSizeBeforeUpdate = academicCalendarRepository.findAll().size();

        // Update the academicCalendar
        AcademicCalendar updatedAcademicCalendar = academicCalendarRepository.findById(academicCalendar.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicCalendar are not directly saved in db
        em.detach(updatedAcademicCalendar);
        updatedAcademicCalendar
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .active(UPDATED_ACTIVE)
            .tenantId(UPDATED_TENANT_ID);
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(updatedAcademicCalendar);

        restAcademicCalendarMockMvc.perform(put("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicCalendar in the database
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeUpdate);
        AcademicCalendar testAcademicCalendar = academicCalendarList.get(academicCalendarList.size() - 1);
        assertThat(testAcademicCalendar.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAcademicCalendar.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAcademicCalendar.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testAcademicCalendar.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAcademicCalendar.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicCalendar() throws Exception {
        int databaseSizeBeforeUpdate = academicCalendarRepository.findAll().size();

        // Create the AcademicCalendar
        AcademicCalendarDTO academicCalendarDTO = academicCalendarMapper.toDto(academicCalendar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicCalendarMockMvc.perform(put("/api/academic-calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(academicCalendarDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicCalendar in the database
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcademicCalendar() throws Exception {
        // Initialize the database
        academicCalendarRepository.saveAndFlush(academicCalendar);

        int databaseSizeBeforeDelete = academicCalendarRepository.findAll().size();

        // Delete the academicCalendar
        restAcademicCalendarMockMvc.perform(delete("/api/academic-calendars/{id}", academicCalendar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findAll();
        assertThat(academicCalendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
