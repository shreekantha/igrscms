package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.repository.ClassTimeTableRepository;
import com.myriadquest.kreiscms.service.ClassTimeTableService;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;
import com.myriadquest.kreiscms.service.mapper.ClassTimeTableMapper;

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

import com.myriadquest.kreiscms.domain.enumeration.Day;
/**
 * Integration tests for the {@link ClassTimeTableResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClassTimeTableResourceIT {

    private static final Day DEFAULT_DAY = Day.MON;
    private static final Day UPDATED_DAY = Day.TUE;

    @Autowired
    private ClassTimeTableRepository classTimeTableRepository;

    @Autowired
    private ClassTimeTableMapper classTimeTableMapper;

    @Autowired
    private ClassTimeTableService classTimeTableService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassTimeTableMockMvc;

    private ClassTimeTable classTimeTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTable createEntity(EntityManager em) {
        ClassTimeTable classTimeTable = new ClassTimeTable()
            .day(DEFAULT_DAY);
        return classTimeTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassTimeTable createUpdatedEntity(EntityManager em) {
        ClassTimeTable classTimeTable = new ClassTimeTable()
            .day(UPDATED_DAY);
        return classTimeTable;
    }

    @BeforeEach
    public void initTest() {
        classTimeTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassTimeTable() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableRepository.findAll().size();
        // Create the ClassTimeTable
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);
        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeCreate + 1);
        ClassTimeTable testClassTimeTable = classTimeTableList.get(classTimeTableList.size() - 1);
        assertThat(testClassTimeTable.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    public void createClassTimeTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classTimeTableRepository.findAll().size();

        // Create the ClassTimeTable with an existing ID
        classTimeTable.setId(1L);
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = classTimeTableRepository.findAll().size();
        // set the field null
        classTimeTable.setDay(null);

        // Create the ClassTimeTable, which fails.
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);


        restClassTimeTableMockMvc.perform(post("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClassTimeTables() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get all the classTimeTableList
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classTimeTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())));
    }
    
    @Test
    @Transactional
    public void getClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        // Get the classTimeTable
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/{id}", classTimeTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classTimeTable.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingClassTimeTable() throws Exception {
        // Get the classTimeTable
        restClassTimeTableMockMvc.perform(get("/api/class-time-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        int databaseSizeBeforeUpdate = classTimeTableRepository.findAll().size();

        // Update the classTimeTable
        ClassTimeTable updatedClassTimeTable = classTimeTableRepository.findById(classTimeTable.getId()).get();
        // Disconnect from session so that the updates on updatedClassTimeTable are not directly saved in db
        em.detach(updatedClassTimeTable);
        updatedClassTimeTable
            .day(UPDATED_DAY);
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(updatedClassTimeTable);

        restClassTimeTableMockMvc.perform(put("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isOk());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeUpdate);
        ClassTimeTable testClassTimeTable = classTimeTableList.get(classTimeTableList.size() - 1);
        assertThat(testClassTimeTable.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingClassTimeTable() throws Exception {
        int databaseSizeBeforeUpdate = classTimeTableRepository.findAll().size();

        // Create the ClassTimeTable
        ClassTimeTableDTO classTimeTableDTO = classTimeTableMapper.toDto(classTimeTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassTimeTableMockMvc.perform(put("/api/class-time-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classTimeTableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassTimeTable in the database
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassTimeTable() throws Exception {
        // Initialize the database
        classTimeTableRepository.saveAndFlush(classTimeTable);

        int databaseSizeBeforeDelete = classTimeTableRepository.findAll().size();

        // Delete the classTimeTable
        restClassTimeTableMockMvc.perform(delete("/api/class-time-tables/{id}", classTimeTable.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassTimeTable> classTimeTableList = classTimeTableRepository.findAll();
        assertThat(classTimeTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
