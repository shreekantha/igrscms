package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.repository.ExamTimeTableRepository;
import com.myriadquest.kreiscms.service.ExamTimeTableService;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ExamTimeTableMapper;

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

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ExamTimeTableRepository examTimeTableRepository;

    @Autowired
    private ExamTimeTableMapper examTimeTableMapper;

    @Autowired
    private ExamTimeTableService examTimeTableService;

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
            .endTime(DEFAULT_END_TIME);
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
            .endTime(UPDATED_END_TIME);
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
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
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
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
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
            .endTime(UPDATED_END_TIME);
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
