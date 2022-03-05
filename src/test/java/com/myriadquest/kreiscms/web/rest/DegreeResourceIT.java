package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Degree;
import com.myriadquest.kreiscms.repository.DegreeRepository;
import com.myriadquest.kreiscms.service.DegreeService;
import com.myriadquest.kreiscms.service.dto.DegreeDTO;
import com.myriadquest.kreiscms.service.mapper.DegreeMapper;

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

import com.myriadquest.kreiscms.domain.enumeration.GraduationType;
import com.myriadquest.kreiscms.domain.enumeration.DegreeOrDeptType;
/**
 * Integration tests for the {@link DegreeResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DegreeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final GraduationType DEFAULT_GRADUATION_TYPE = GraduationType.SCHOOLING;
    private static final GraduationType UPDATED_GRADUATION_TYPE = GraduationType.PU;

    private static final DegreeOrDeptType DEFAULT_TYPE = DegreeOrDeptType.ACADEMIC;
    private static final DegreeOrDeptType UPDATED_TYPE = DegreeOrDeptType.NON_ACADEMIC;

    private static final Integer DEFAULT_NUM_OF_YEARS = 1;
    private static final Integer UPDATED_NUM_OF_YEARS = 2;

    private static final Boolean DEFAULT_MULTI_BATCH = false;
    private static final Boolean UPDATED_MULTI_BATCH = true;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private DegreeMapper degreeMapper;

    @Autowired
    private DegreeService degreeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDegreeMockMvc;

    private Degree degree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Degree createEntity(EntityManager em) {
        Degree degree = new Degree()
            .name(DEFAULT_NAME)
            .alias(DEFAULT_ALIAS)
            .graduationType(DEFAULT_GRADUATION_TYPE)
            .type(DEFAULT_TYPE)
            .numOfYears(DEFAULT_NUM_OF_YEARS)
            .multiBatch(DEFAULT_MULTI_BATCH);
        return degree;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Degree createUpdatedEntity(EntityManager em) {
        Degree degree = new Degree()
            .name(UPDATED_NAME)
            .alias(UPDATED_ALIAS)
            .graduationType(UPDATED_GRADUATION_TYPE)
            .type(UPDATED_TYPE)
            .numOfYears(UPDATED_NUM_OF_YEARS)
            .multiBatch(UPDATED_MULTI_BATCH);
        return degree;
    }

    @BeforeEach
    public void initTest() {
        degree = createEntity(em);
    }

    @Test
    @Transactional
    public void createDegree() throws Exception {
        int databaseSizeBeforeCreate = degreeRepository.findAll().size();
        // Create the Degree
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);
        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isCreated());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeCreate + 1);
        Degree testDegree = degreeList.get(degreeList.size() - 1);
        assertThat(testDegree.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDegree.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testDegree.getGraduationType()).isEqualTo(DEFAULT_GRADUATION_TYPE);
        assertThat(testDegree.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDegree.getNumOfYears()).isEqualTo(DEFAULT_NUM_OF_YEARS);
        assertThat(testDegree.isMultiBatch()).isEqualTo(DEFAULT_MULTI_BATCH);
    }

    @Test
    @Transactional
    public void createDegreeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = degreeRepository.findAll().size();

        // Create the Degree with an existing ID
        degree.setId(1L);
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setName(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);


        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAliasIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setAlias(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);


        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGraduationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setGraduationType(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);


        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setType(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);


        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumOfYearsIsRequired() throws Exception {
        int databaseSizeBeforeTest = degreeRepository.findAll().size();
        // set the field null
        degree.setNumOfYears(null);

        // Create the Degree, which fails.
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);


        restDegreeMockMvc.perform(post("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDegrees() throws Exception {
        // Initialize the database
        degreeRepository.saveAndFlush(degree);

        // Get all the degreeList
        restDegreeMockMvc.perform(get("/api/degrees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(degree.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].graduationType").value(hasItem(DEFAULT_GRADUATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].numOfYears").value(hasItem(DEFAULT_NUM_OF_YEARS)))
            .andExpect(jsonPath("$.[*].multiBatch").value(hasItem(DEFAULT_MULTI_BATCH.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDegree() throws Exception {
        // Initialize the database
        degreeRepository.saveAndFlush(degree);

        // Get the degree
        restDegreeMockMvc.perform(get("/api/degrees/{id}", degree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(degree.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.graduationType").value(DEFAULT_GRADUATION_TYPE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.numOfYears").value(DEFAULT_NUM_OF_YEARS))
            .andExpect(jsonPath("$.multiBatch").value(DEFAULT_MULTI_BATCH.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingDegree() throws Exception {
        // Get the degree
        restDegreeMockMvc.perform(get("/api/degrees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDegree() throws Exception {
        // Initialize the database
        degreeRepository.saveAndFlush(degree);

        int databaseSizeBeforeUpdate = degreeRepository.findAll().size();

        // Update the degree
        Degree updatedDegree = degreeRepository.findById(degree.getId()).get();
        // Disconnect from session so that the updates on updatedDegree are not directly saved in db
        em.detach(updatedDegree);
        updatedDegree
            .name(UPDATED_NAME)
            .alias(UPDATED_ALIAS)
            .graduationType(UPDATED_GRADUATION_TYPE)
            .type(UPDATED_TYPE)
            .numOfYears(UPDATED_NUM_OF_YEARS)
            .multiBatch(UPDATED_MULTI_BATCH);
        DegreeDTO degreeDTO = degreeMapper.toDto(updatedDegree);

        restDegreeMockMvc.perform(put("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isOk());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeUpdate);
        Degree testDegree = degreeList.get(degreeList.size() - 1);
        assertThat(testDegree.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDegree.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testDegree.getGraduationType()).isEqualTo(UPDATED_GRADUATION_TYPE);
        assertThat(testDegree.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDegree.getNumOfYears()).isEqualTo(UPDATED_NUM_OF_YEARS);
        assertThat(testDegree.isMultiBatch()).isEqualTo(UPDATED_MULTI_BATCH);
    }

    @Test
    @Transactional
    public void updateNonExistingDegree() throws Exception {
        int databaseSizeBeforeUpdate = degreeRepository.findAll().size();

        // Create the Degree
        DegreeDTO degreeDTO = degreeMapper.toDto(degree);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDegreeMockMvc.perform(put("/api/degrees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(degreeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Degree in the database
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDegree() throws Exception {
        // Initialize the database
        degreeRepository.saveAndFlush(degree);

        int databaseSizeBeforeDelete = degreeRepository.findAll().size();

        // Delete the degree
        restDegreeMockMvc.perform(delete("/api/degrees/{id}", degree.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Degree> degreeList = degreeRepository.findAll();
        assertThat(degreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
