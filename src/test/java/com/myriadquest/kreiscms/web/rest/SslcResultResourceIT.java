package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.SslcResult;
import com.myriadquest.kreiscms.repository.SslcResultRepository;
import com.myriadquest.kreiscms.service.SslcResultService;
import com.myriadquest.kreiscms.service.dto.SslcResultDTO;
import com.myriadquest.kreiscms.service.mapper.SslcResultMapper;
import com.myriadquest.kreiscms.service.dto.SslcResultCriteria;
import com.myriadquest.kreiscms.service.SslcResultQueryService;

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
 * Integration tests for the {@link SslcResultResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SslcResultResourceIT {

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
    private SslcResultRepository sslcResultRepository;

    @Autowired
    private SslcResultMapper sslcResultMapper;

    @Autowired
    private SslcResultService sslcResultService;

    @Autowired
    private SslcResultQueryService sslcResultQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSslcResultMockMvc;

    private SslcResult sslcResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SslcResult createEntity(EntityManager em) {
        SslcResult sslcResult = new SslcResult()
            .academicYear(DEFAULT_ACADEMIC_YEAR)
            .qualityResult(DEFAULT_QUALITY_RESULT)
            .topResult(DEFAULT_TOP_RESULT)
            .tenantId(DEFAULT_TENANT_ID);
        return sslcResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SslcResult createUpdatedEntity(EntityManager em) {
        SslcResult sslcResult = new SslcResult()
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .qualityResult(UPDATED_QUALITY_RESULT)
            .topResult(UPDATED_TOP_RESULT)
            .tenantId(UPDATED_TENANT_ID);
        return sslcResult;
    }

    @BeforeEach
    public void initTest() {
        sslcResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createSslcResult() throws Exception {
        int databaseSizeBeforeCreate = sslcResultRepository.findAll().size();
        // Create the SslcResult
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);
        restSslcResultMockMvc.perform(post("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isCreated());

        // Validate the SslcResult in the database
        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeCreate + 1);
        SslcResult testSslcResult = sslcResultList.get(sslcResultList.size() - 1);
        assertThat(testSslcResult.getAcademicYear()).isEqualTo(DEFAULT_ACADEMIC_YEAR);
        assertThat(testSslcResult.getQualityResult()).isEqualTo(DEFAULT_QUALITY_RESULT);
        assertThat(testSslcResult.getTopResult()).isEqualTo(DEFAULT_TOP_RESULT);
        assertThat(testSslcResult.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createSslcResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sslcResultRepository.findAll().size();

        // Create the SslcResult with an existing ID
        sslcResult.setId(1L);
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSslcResultMockMvc.perform(post("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SslcResult in the database
        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAcademicYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = sslcResultRepository.findAll().size();
        // set the field null
        sslcResult.setAcademicYear(null);

        // Create the SslcResult, which fails.
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);


        restSslcResultMockMvc.perform(post("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isBadRequest());

        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQualityResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = sslcResultRepository.findAll().size();
        // set the field null
        sslcResult.setQualityResult(null);

        // Create the SslcResult, which fails.
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);


        restSslcResultMockMvc.perform(post("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isBadRequest());

        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = sslcResultRepository.findAll().size();
        // set the field null
        sslcResult.setTopResult(null);

        // Create the SslcResult, which fails.
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);


        restSslcResultMockMvc.perform(post("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isBadRequest());

        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSslcResults() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList
        restSslcResultMockMvc.perform(get("/api/sslc-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sslcResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].qualityResult").value(hasItem(DEFAULT_QUALITY_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].topResult").value(hasItem(DEFAULT_TOP_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getSslcResult() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get the sslcResult
        restSslcResultMockMvc.perform(get("/api/sslc-results/{id}", sslcResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sslcResult.getId().intValue()))
            .andExpect(jsonPath("$.academicYear").value(DEFAULT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.qualityResult").value(DEFAULT_QUALITY_RESULT.doubleValue()))
            .andExpect(jsonPath("$.topResult").value(DEFAULT_TOP_RESULT.doubleValue()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getSslcResultsByIdFiltering() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        Long id = sslcResult.getId();

        defaultSslcResultShouldBeFound("id.equals=" + id);
        defaultSslcResultShouldNotBeFound("id.notEquals=" + id);

        defaultSslcResultShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSslcResultShouldNotBeFound("id.greaterThan=" + id);

        defaultSslcResultShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSslcResultShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear equals to DEFAULT_ACADEMIC_YEAR
        defaultSslcResultShouldBeFound("academicYear.equals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the sslcResultList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldNotBeFound("academicYear.equals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear not equals to DEFAULT_ACADEMIC_YEAR
        defaultSslcResultShouldNotBeFound("academicYear.notEquals=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the sslcResultList where academicYear not equals to UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldBeFound("academicYear.notEquals=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear in DEFAULT_ACADEMIC_YEAR or UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldBeFound("academicYear.in=" + DEFAULT_ACADEMIC_YEAR + "," + UPDATED_ACADEMIC_YEAR);

        // Get all the sslcResultList where academicYear equals to UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldNotBeFound("academicYear.in=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear is not null
        defaultSslcResultShouldBeFound("academicYear.specified=true");

        // Get all the sslcResultList where academicYear is null
        defaultSslcResultShouldNotBeFound("academicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear contains DEFAULT_ACADEMIC_YEAR
        defaultSslcResultShouldBeFound("academicYear.contains=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the sslcResultList where academicYear contains UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldNotBeFound("academicYear.contains=" + UPDATED_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where academicYear does not contain DEFAULT_ACADEMIC_YEAR
        defaultSslcResultShouldNotBeFound("academicYear.doesNotContain=" + DEFAULT_ACADEMIC_YEAR);

        // Get all the sslcResultList where academicYear does not contain UPDATED_ACADEMIC_YEAR
        defaultSslcResultShouldBeFound("academicYear.doesNotContain=" + UPDATED_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult equals to DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.equals=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult equals to UPDATED_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.equals=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult not equals to DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.notEquals=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult not equals to UPDATED_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.notEquals=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsInShouldWork() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult in DEFAULT_QUALITY_RESULT or UPDATED_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.in=" + DEFAULT_QUALITY_RESULT + "," + UPDATED_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult equals to UPDATED_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.in=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult is not null
        defaultSslcResultShouldBeFound("qualityResult.specified=true");

        // Get all the sslcResultList where qualityResult is null
        defaultSslcResultShouldNotBeFound("qualityResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult is greater than or equal to DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.greaterThanOrEqual=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult is greater than or equal to UPDATED_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.greaterThanOrEqual=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult is less than or equal to DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.lessThanOrEqual=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult is less than or equal to SMALLER_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.lessThanOrEqual=" + SMALLER_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsLessThanSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult is less than DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.lessThan=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult is less than UPDATED_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.lessThan=" + UPDATED_QUALITY_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByQualityResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where qualityResult is greater than DEFAULT_QUALITY_RESULT
        defaultSslcResultShouldNotBeFound("qualityResult.greaterThan=" + DEFAULT_QUALITY_RESULT);

        // Get all the sslcResultList where qualityResult is greater than SMALLER_QUALITY_RESULT
        defaultSslcResultShouldBeFound("qualityResult.greaterThan=" + SMALLER_QUALITY_RESULT);
    }


    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult equals to DEFAULT_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.equals=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult equals to UPDATED_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.equals=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult not equals to DEFAULT_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.notEquals=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult not equals to UPDATED_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.notEquals=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsInShouldWork() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult in DEFAULT_TOP_RESULT or UPDATED_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.in=" + DEFAULT_TOP_RESULT + "," + UPDATED_TOP_RESULT);

        // Get all the sslcResultList where topResult equals to UPDATED_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.in=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult is not null
        defaultSslcResultShouldBeFound("topResult.specified=true");

        // Get all the sslcResultList where topResult is null
        defaultSslcResultShouldNotBeFound("topResult.specified=false");
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult is greater than or equal to DEFAULT_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.greaterThanOrEqual=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult is greater than or equal to UPDATED_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.greaterThanOrEqual=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult is less than or equal to DEFAULT_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.lessThanOrEqual=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult is less than or equal to SMALLER_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.lessThanOrEqual=" + SMALLER_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsLessThanSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult is less than DEFAULT_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.lessThan=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult is less than UPDATED_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.lessThan=" + UPDATED_TOP_RESULT);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTopResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where topResult is greater than DEFAULT_TOP_RESULT
        defaultSslcResultShouldNotBeFound("topResult.greaterThan=" + DEFAULT_TOP_RESULT);

        // Get all the sslcResultList where topResult is greater than SMALLER_TOP_RESULT
        defaultSslcResultShouldBeFound("topResult.greaterThan=" + SMALLER_TOP_RESULT);
    }


    @Test
    @Transactional
    public void getAllSslcResultsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId equals to DEFAULT_TENANT_ID
        defaultSslcResultShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the sslcResultList where tenantId equals to UPDATED_TENANT_ID
        defaultSslcResultShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId not equals to DEFAULT_TENANT_ID
        defaultSslcResultShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the sslcResultList where tenantId not equals to UPDATED_TENANT_ID
        defaultSslcResultShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultSslcResultShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the sslcResultList where tenantId equals to UPDATED_TENANT_ID
        defaultSslcResultShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId is not null
        defaultSslcResultShouldBeFound("tenantId.specified=true");

        // Get all the sslcResultList where tenantId is null
        defaultSslcResultShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllSslcResultsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId contains DEFAULT_TENANT_ID
        defaultSslcResultShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the sslcResultList where tenantId contains UPDATED_TENANT_ID
        defaultSslcResultShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllSslcResultsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        // Get all the sslcResultList where tenantId does not contain DEFAULT_TENANT_ID
        defaultSslcResultShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the sslcResultList where tenantId does not contain UPDATED_TENANT_ID
        defaultSslcResultShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSslcResultShouldBeFound(String filter) throws Exception {
        restSslcResultMockMvc.perform(get("/api/sslc-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sslcResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].academicYear").value(hasItem(DEFAULT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].qualityResult").value(hasItem(DEFAULT_QUALITY_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].topResult").value(hasItem(DEFAULT_TOP_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restSslcResultMockMvc.perform(get("/api/sslc-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSslcResultShouldNotBeFound(String filter) throws Exception {
        restSslcResultMockMvc.perform(get("/api/sslc-results?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSslcResultMockMvc.perform(get("/api/sslc-results/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSslcResult() throws Exception {
        // Get the sslcResult
        restSslcResultMockMvc.perform(get("/api/sslc-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSslcResult() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        int databaseSizeBeforeUpdate = sslcResultRepository.findAll().size();

        // Update the sslcResult
        SslcResult updatedSslcResult = sslcResultRepository.findById(sslcResult.getId()).get();
        // Disconnect from session so that the updates on updatedSslcResult are not directly saved in db
        em.detach(updatedSslcResult);
        updatedSslcResult
            .academicYear(UPDATED_ACADEMIC_YEAR)
            .qualityResult(UPDATED_QUALITY_RESULT)
            .topResult(UPDATED_TOP_RESULT)
            .tenantId(UPDATED_TENANT_ID);
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(updatedSslcResult);

        restSslcResultMockMvc.perform(put("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isOk());

        // Validate the SslcResult in the database
        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeUpdate);
        SslcResult testSslcResult = sslcResultList.get(sslcResultList.size() - 1);
        assertThat(testSslcResult.getAcademicYear()).isEqualTo(UPDATED_ACADEMIC_YEAR);
        assertThat(testSslcResult.getQualityResult()).isEqualTo(UPDATED_QUALITY_RESULT);
        assertThat(testSslcResult.getTopResult()).isEqualTo(UPDATED_TOP_RESULT);
        assertThat(testSslcResult.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSslcResult() throws Exception {
        int databaseSizeBeforeUpdate = sslcResultRepository.findAll().size();

        // Create the SslcResult
        SslcResultDTO sslcResultDTO = sslcResultMapper.toDto(sslcResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSslcResultMockMvc.perform(put("/api/sslc-results")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sslcResultDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SslcResult in the database
        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSslcResult() throws Exception {
        // Initialize the database
        sslcResultRepository.saveAndFlush(sslcResult);

        int databaseSizeBeforeDelete = sslcResultRepository.findAll().size();

        // Delete the sslcResult
        restSslcResultMockMvc.perform(delete("/api/sslc-results/{id}", sslcResult.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SslcResult> sslcResultList = sslcResultRepository.findAll();
        assertThat(sslcResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
