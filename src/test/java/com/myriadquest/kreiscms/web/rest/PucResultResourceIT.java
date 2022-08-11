package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.PucResult;
import com.myriadquest.kreiscms.repository.PucResultRepository;
import com.myriadquest.kreiscms.service.PucResultService;
import com.myriadquest.kreiscms.service.dto.PucResultDTO;
import com.myriadquest.kreiscms.service.mapper.PucResultMapper;
import com.myriadquest.kreiscms.service.dto.PucResultCriteria;
import com.myriadquest.kreiscms.service.PucResultQueryService;

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
 * Integration tests for the {@link PucResultResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PucResultResourceIT {

    private static final String DEFAULT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_QUALITY_RESULT = 1D;
    private static final Double UPDATED_QUALITY_RESULT = 2D;
    private static final Double SMALLER_QUALITY_RESULT = 1D - 1D;

    private static final Double DEFAULT_TOP_RESULT = 1D;
    private static final Double UPDATED_TOP_RESULT = 2D;
    private static final Double SMALLER_TOP_RESULT = 1D - 1D;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private PucResultRepository pucResultRepository;

    @Autowired
    private PucResultMapper pucResultMapper;

    @Autowired
    private PucResultService pucResultService;

    @Autowired
    private PucResultQueryService pucResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPucResultMockMvc;

    private PucResult pucResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PucResult createEntity(EntityManager em) {
        PucResult pucResult = new PucResult()
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .qualityResult(DEFAULT_QUALITY_RESULT)
            .topResult(DEFAULT_TOP_RESULT)
            .tenantId(DEFAULT_TENANT_ID);
        return pucResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PucResult createUpdatedEntity(EntityManager em) {
        PucResult pucResult = new PucResult()
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .qualityResult(UPDATED_QUALITY_RESULT)
            .topResult(UPDATED_TOP_RESULT)
            .tenantId(UPDATED_TENANT_ID);
        return pucResult;
    }

    @BeforeEach
    public void initTest() {
        pucResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createPucResult() throws Exception {
        int databaseSizeBeforeCreate = pucResultRepository.findAll().size();
        // Create the PucResult
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);
        restPucResultMockMvc.perform(post("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isCreated());

        // Validate the PucResult in the database
        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeCreate + 1);
        PucResult testPucResult = pucResultList.get(pucResultList.size() - 1);
        assertThat(testPucResult.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testPucResult.getQualityResult()).isEqualTo(DEFAULT_QUALITY_RESULT);
        assertThat(testPucResult.getTopResult()).isEqualTo(DEFAULT_TOP_RESULT);
        assertThat(testPucResult.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createPucResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pucResultRepository.findAll().size();

        // Create the PucResult with an existing ID
        pucResult.setId(1L);
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPucResultMockMvc.perform(post("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PucResult in the database
        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = pucResultRepository.findAll().size();
        // set the field null
        pucResult.setAcademicYear(null);

        // Create the PucResult, which fails.
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);


        restPucResultMockMvc.perform(post("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isBadRequest());

        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQualityResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = pucResultRepository.findAll().size();
        // set the field null
        pucResult.setQualityResult(null);

        // Create the PucResult, which fails.
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);


        restPucResultMockMvc.perform(post("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isBadRequest());

        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = pucResultRepository.findAll().size();
        // set the field null
        pucResult.setTopResult(null);

        // Create the PucResult, which fails.
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);


        restPucResultMockMvc.perform(post("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isBadRequest());

        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPucResults() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList
        restPucResultMockMvc.perform(get("/api/puc-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pucResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].qualityResult").value(hasItem(DEFAULT_QUALITY_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].topResult").value(hasItem(DEFAULT_TOP_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getPucResult() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get the pucResult
        restPucResultMockMvc.perform(get("/api/puc-results/{id}", pucResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pucResult.getId().intValue()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.qualityResult").value(DEFAULT_QUALITY_RESULT.doubleValue()))
            .andExpect(jsonPath("$.topResult").value(DEFAULT_TOP_RESULT.doubleValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getPucResultsByIdFiltering() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        Long id = pucResult.getId();

        defaultPucResultShouldBeFound("id.equals=" + id);
        defaultPucResultShouldNotBeFound("id.notEquals=" + id);

        defaultPucResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPucResultShouldNotBeFound("id.greaterThan=" + id);

        defaultPucResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPucResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPucResultsByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear equals to DEFAULT_ACADEMIC_YEAR
        defaultPucResultShouldBeFound("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the pucResultList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldNotBeFound("academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllPucResultsByAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear not equals to DEFAULT_ACADEMIC_YEAR
        defaultPucResultShouldNotBeFound("academicYear.notEquals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the pucResultList where academicYear not equals to UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldBeFound("academicYear.notEquals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllPucResultsByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear in DEFAULT_ACADEMIC_YEAR or UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldBeFound("academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR);

        // Get all the pucResultList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldNotBeFound("academicYear.in=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllPucResultsByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear is not null
        defaultPucResultShouldBeFound("academicYear.specified=true");

        // Get all the pucResultList where academicYear is null
        defaultPucResultShouldNotBeFound("academicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllPucResultsByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear contains DEFAULT_ACADEMIC_YEAR
        defaultPucResultShouldBeFound("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the pucResultList where academicYear contains UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldNotBeFound("academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllPucResultsByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where academicYear does not contain DEFAULT_ACADEMIC_YEAR
        defaultPucResultShouldNotBeFound("academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the pucResultList where academicYear does not contain UPDATED_ACADEMIC_YEAR
        defaultPucResultShouldBeFound("academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult equals to DEFAULT_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.equals=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult equals to UPDATED_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.equals=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult not equals to DEFAULT_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.notEquals=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult not equals to UPDATED_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.notEquals=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsInShouldWork() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult in DEFAULT_QUALITY_RESULT or UPDATED_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.in=" + DEFAULT_QUALITY_RESULT + "," + UPDATED_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult equals to UPDATED_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.in=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult is not null
        defaultPucResultShouldBeFound("qualityResult.specified=true");

        // Get all the pucResultList where qualityResult is null
        defaultPucResultShouldNotBeFound("qualityResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult is greater than or equal to DEFAULT_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.greaterThanOrEqual=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult is greater than or equal to UPDATED_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.greaterThanOrEqual=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult is less than or equal to DEFAULT_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.lessThanOrEqual=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult is less than or equal to SMALLER_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.lessThanOrEqual=" + SMALLER_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsLessThanSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult is less than DEFAULT_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.lessThan=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult is less than UPDATED_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.lessThan=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByQualityResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where qualityResult is greater than DEFAULT_QUALITY_RESULT
        defaultPucResultShouldNotBeFound("qualityResult.greaterThan=" + DEFAULT_QUALITY_RESULT);

        // Get all the pucResultList where qualityResult is greater than SMALLER_QUALITY_RESULT
        defaultPucResultShouldBeFound("qualityResult.greaterThan=" + SMALLER_QUALITY_RESULT);
    }


    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult equals to DEFAULT_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.equals=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult equals to UPDATED_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.equals=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult not equals to DEFAULT_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.notEquals=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult not equals to UPDATED_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.notEquals=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsInShouldWork() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult in DEFAULT_TOP_RESULT or UPDATED_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.in=" + DEFAULT_TOP_RESULT + "," + UPDATED_TOP_RESULT);

        // Get all the pucResultList where topResult equals to UPDATED_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.in=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult is not null
        defaultPucResultShouldBeFound("topResult.specified=true");

        // Get all the pucResultList where topResult is null
        defaultPucResultShouldNotBeFound("topResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult is greater than or equal to DEFAULT_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.greaterThanOrEqual=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult is greater than or equal to UPDATED_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.greaterThanOrEqual=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult is less than or equal to DEFAULT_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.lessThanOrEqual=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult is less than or equal to SMALLER_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.lessThanOrEqual=" + SMALLER_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsLessThanSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult is less than DEFAULT_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.lessThan=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult is less than UPDATED_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.lessThan=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTopResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where topResult is greater than DEFAULT_TOP_RESULT
        defaultPucResultShouldNotBeFound("topResult.greaterThan=" + DEFAULT_TOP_RESULT);

        // Get all the pucResultList where topResult is greater than SMALLER_TOP_RESULT
        defaultPucResultShouldBeFound("topResult.greaterThan=" + SMALLER_TOP_RESULT);
    }


    @Test
    @Transactional
    public void getAllPucResultsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId equals to DEFAULT_TENANT_ID
        defaultPucResultShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the pucResultList where tenantId equals to UPDATED_TENANT_ID
        defaultPucResultShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId not equals to DEFAULT_TENANT_ID
        defaultPucResultShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the pucResultList where tenantId not equals to UPDATED_TENANT_ID
        defaultPucResultShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultPucResultShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the pucResultList where tenantId equals to UPDATED_TENANT_ID
        defaultPucResultShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId is not null
        defaultPucResultShouldBeFound("tenantId.specified=true");

        // Get all the pucResultList where tenantId is null
        defaultPucResultShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPucResultsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId contains DEFAULT_TENANT_ID
        defaultPucResultShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the pucResultList where tenantId contains UPDATED_TENANT_ID
        defaultPucResultShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllPucResultsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        // Get all the pucResultList where tenantId does not contain DEFAULT_TENANT_ID
        defaultPucResultShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the pucResultList where tenantId does not contain UPDATED_TENANT_ID
        defaultPucResultShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPucResultShouldBeFound(String filter) throws Exception {
        restPucResultMockMvc.perform(get("/api/puc-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pucResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].qualityResult").value(hasItem(DEFAULT_QUALITY_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].topResult").value(hasItem(DEFAULT_TOP_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restPucResultMockMvc.perform(get("/api/puc-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPucResultShouldNotBeFound(String filter) throws Exception {
        restPucResultMockMvc.perform(get("/api/puc-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPucResultMockMvc.perform(get("/api/puc-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPucResult() throws Exception {
        // Get the pucResult
        restPucResultMockMvc.perform(get("/api/puc-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePucResult() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        int databaseSizeBeforeUpdate = pucResultRepository.findAll().size();

        // Update the pucResult
        PucResult updatedPucResult = pucResultRepository.findById(pucResult.getId()).get();
        // Disconnect from session so that the updates on updatedPucResult are not directly saved in db
        em.detach(updatedPucResult);
        updatedPucResult
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .qualityResult(UPDATED_QUALITY_RESULT)
            .topResult(UPDATED_TOP_RESULT)
            .tenantId(UPDATED_TENANT_ID);
        PucResultDTO pucResultDTO = pucResultMapper.toDto(updatedPucResult);

        restPucResultMockMvc.perform(put("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isOk());

        // Validate the PucResult in the database
        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeUpdate);
        PucResult testPucResult = pucResultList.get(pucResultList.size() - 1);
        assertThat(testPucResult.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testPucResult.getQualityResult()).isEqualTo(UPDATED_QUALITY_RESULT);
        assertThat(testPucResult.getTopResult()).isEqualTo(UPDATED_TOP_RESULT);
        assertThat(testPucResult.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPucResult() throws Exception {
        int databaseSizeBeforeUpdate = pucResultRepository.findAll().size();

        // Create the PucResult
        PucResultDTO pucResultDTO = pucResultMapper.toDto(pucResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPucResultMockMvc.perform(put("/api/puc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pucResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PucResult in the database
        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePucResult() throws Exception {
        // Initialize the database
        pucResultRepository.saveAndFlush(pucResult);

        int databaseSizeBeforeDelete = pucResultRepository.findAll().size();

        // Delete the pucResult
        restPucResultMockMvc.perform(delete("/api/puc-results/{id}", pucResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PucResult> pucResultList = pucResultRepository.findAll();
        assertThat(pucResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
