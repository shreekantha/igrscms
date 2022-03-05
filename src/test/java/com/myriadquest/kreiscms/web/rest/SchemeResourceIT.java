package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Scheme;
import com.myriadquest.kreiscms.repository.SchemeRepository;
import com.myriadquest.kreiscms.service.SchemeService;
import com.myriadquest.kreiscms.service.dto.SchemeDTO;
import com.myriadquest.kreiscms.service.mapper.SchemeMapper;

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
 * Integration tests for the {@link SchemeResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SchemeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STARTED_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_STARTED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private SchemeRepository schemeRepository;

    @Autowired
    private SchemeMapper schemeMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchemeMockMvc;

    private Scheme scheme;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheme createEntity(EntityManager em) {
        Scheme scheme = new Scheme()
            .name(DEFAULT_NAME)
            .startedAcademicYear(DEFAULT_STARTED_ACADEMIC_YEAR)
            .year(DEFAULT_YEAR);
        return scheme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scheme createUpdatedEntity(EntityManager em) {
        Scheme scheme = new Scheme()
            .name(UPDATED_NAME)
            .startedAcademicYear(UPDATED_STARTED_ACADEMIC_YEAR)
            .year(UPDATED_YEAR);
        return scheme;
    }

    @BeforeEach
    public void initTest() {
        scheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheme() throws Exception {
        int databaseSizeBeforeCreate = schemeRepository.findAll().size();
        // Create the Scheme
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);
        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isCreated());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeCreate + 1);
        Scheme testScheme = schemeList.get(schemeList.size() - 1);
        assertThat(testScheme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScheme.getStartedAcademicYear()).isEqualTo(DEFAULT_STARTED_ACADEMIC_YEAR);
        assertThat(testScheme.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createSchemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schemeRepository.findAll().size();

        // Create the Scheme with an existing ID
        scheme.setId(1L);
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setName(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);


        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartedAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setStartedAcademicYear(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);


        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = schemeRepository.findAll().size();
        // set the field null
        scheme.setYear(null);

        // Create the Scheme, which fails.
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);


        restSchemeMockMvc.perform(post("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchemes() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get all the schemeList
        restSchemeMockMvc.perform(get("/api/schemes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startedAcademicYear").value(hasItem(DEFAULT_STARTED_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    @Transactional
    public void getScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", scheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheme.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startedAcademicYear").value(DEFAULT_STARTED_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingScheme() throws Exception {
        // Get the scheme
        restSchemeMockMvc.perform(get("/api/schemes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();

        // Update the scheme
        Scheme updatedScheme = schemeRepository.findById(scheme.getId()).get();
        // Disconnect from session so that the updates on updatedScheme are not directly saved in db
        em.detach(updatedScheme);
        updatedScheme
            .name(UPDATED_NAME)
            .startedAcademicYear(UPDATED_STARTED_ACADEMIC_YEAR)
            .year(UPDATED_YEAR);
        SchemeDTO schemeDTO = schemeMapper.toDto(updatedScheme);

        restSchemeMockMvc.perform(put("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isOk());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate);
        Scheme testScheme = schemeList.get(schemeList.size() - 1);
        assertThat(testScheme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScheme.getStartedAcademicYear()).isEqualTo(UPDATED_STARTED_ACADEMIC_YEAR);
        assertThat(testScheme.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingScheme() throws Exception {
        int databaseSizeBeforeUpdate = schemeRepository.findAll().size();

        // Create the Scheme
        SchemeDTO schemeDTO = schemeMapper.toDto(scheme);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemeMockMvc.perform(put("/api/schemes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schemeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scheme in the database
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheme() throws Exception {
        // Initialize the database
        schemeRepository.saveAndFlush(scheme);

        int databaseSizeBeforeDelete = schemeRepository.findAll().size();

        // Delete the scheme
        restSchemeMockMvc.perform(delete("/api/schemes/{id}", scheme.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scheme> schemeList = schemeRepository.findAll();
        assertThat(schemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
