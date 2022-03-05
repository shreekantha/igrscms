package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.AcademicCalendar;
import com.myriadquest.kreiscms.repository.AcademicCalendarRepository;
import com.myriadquest.kreiscms.service.AcademicCalendarService;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;
import com.myriadquest.kreiscms.service.mapper.AcademicCalendarMapper;

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

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AcademicCalendarRepository academicCalendarRepository;

    @Autowired
    private AcademicCalendarMapper academicCalendarMapper;

    @Autowired
    private AcademicCalendarService academicCalendarService;

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
            .active(DEFAULT_ACTIVE);
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
            .active(UPDATED_ACTIVE);
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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
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
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
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
            .active(UPDATED_ACTIVE);
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
