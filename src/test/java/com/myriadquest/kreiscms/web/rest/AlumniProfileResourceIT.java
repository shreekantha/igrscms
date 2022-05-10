package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.AlumniProfile;
import com.myriadquest.kreiscms.repository.AlumniProfileRepository;
import com.myriadquest.kreiscms.service.AlumniProfileService;
import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;
import com.myriadquest.kreiscms.service.mapper.AlumniProfileMapper;
import com.myriadquest.kreiscms.service.dto.AlumniProfileCriteria;
import com.myriadquest.kreiscms.service.AlumniProfileQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlumniProfileResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AlumniProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FATHERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHERS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHERS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CURRENT_TERM = 1;
    private static final Integer UPDATED_CURRENT_TERM = 2;
    private static final Integer SMALLER_CURRENT_TERM = 1 - 1;

    private static final String DEFAULT_JOINING_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_JOINING_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_EXIT_ACADEMIC_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_EXIT_ACADEMIC_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAAR = "AAAAAAAAAAAA";
    private static final String UPDATED_AADHAAR = "BBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AlumniProfileRepository alumniProfileRepository;

    @Autowired
    private AlumniProfileMapper alumniProfileMapper;

    @Autowired
    private AlumniProfileService alumniProfileService;

    @Autowired
    private AlumniProfileQueryService alumniProfileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlumniProfileMockMvc;

    private AlumniProfile alumniProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlumniProfile createEntity(EntityManager em) {
        AlumniProfile alumniProfile = new AlumniProfile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .fathersName(DEFAULT_FATHERS_NAME)
            .mothersName(DEFAULT_MOTHERS_NAME)
            .currentTerm(DEFAULT_CURRENT_TERM)
            .joiningAcademicYear(DEFAULT_JOINING_ACADEMIC_YEAR)
            .exitAcademicYear(DEFAULT_EXIT_ACADEMIC_YEAR)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .aadhaar(DEFAULT_AADHAAR)
            .dob(DEFAULT_DOB)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .tenantId(DEFAULT_TENANT_ID)
            .active(DEFAULT_ACTIVE);
        return alumniProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AlumniProfile createUpdatedEntity(EntityManager em) {
        AlumniProfile alumniProfile = new AlumniProfile()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .mothersName(UPDATED_MOTHERS_NAME)
            .currentTerm(UPDATED_CURRENT_TERM)
            .joiningAcademicYear(UPDATED_JOINING_ACADEMIC_YEAR)
            .exitAcademicYear(UPDATED_EXIT_ACADEMIC_YEAR)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .active(UPDATED_ACTIVE);
        return alumniProfile;
    }

    @BeforeEach
    public void initTest() {
        alumniProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlumniProfile() throws Exception {
        int databaseSizeBeforeCreate = alumniProfileRepository.findAll().size();
        // Create the AlumniProfile
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);
        restAlumniProfileMockMvc.perform(post("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the AlumniProfile in the database
        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeCreate + 1);
        AlumniProfile testAlumniProfile = alumniProfileList.get(alumniProfileList.size() - 1);
        assertThat(testAlumniProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAlumniProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAlumniProfile.getFathersName()).isEqualTo(DEFAULT_FATHERS_NAME);
        assertThat(testAlumniProfile.getMothersName()).isEqualTo(DEFAULT_MOTHERS_NAME);
        assertThat(testAlumniProfile.getCurrentTerm()).isEqualTo(DEFAULT_CURRENT_TERM);
        assertThat(testAlumniProfile.getJoiningAcademicYear()).isEqualTo(DEFAULT_JOINING_ACADEMIC_YEAR);
        assertThat(testAlumniProfile.getExitAcademicYear()).isEqualTo(DEFAULT_EXIT_ACADEMIC_YEAR);
        assertThat(testAlumniProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testAlumniProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAlumniProfile.getAadhaar()).isEqualTo(DEFAULT_AADHAAR);
        assertThat(testAlumniProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testAlumniProfile.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testAlumniProfile.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testAlumniProfile.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testAlumniProfile.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testAlumniProfile.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAlumniProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alumniProfileRepository.findAll().size();

        // Create the AlumniProfile with an existing ID
        alumniProfile.setId(1L);
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlumniProfileMockMvc.perform(post("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlumniProfile in the database
        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumniProfileRepository.findAll().size();
        // set the field null
        alumniProfile.setFirstName(null);

        // Create the AlumniProfile, which fails.
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);


        restAlumniProfileMockMvc.perform(post("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isBadRequest());

        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumniProfileRepository.findAll().size();
        // set the field null
        alumniProfile.setMobile(null);

        // Create the AlumniProfile, which fails.
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);


        restAlumniProfileMockMvc.perform(post("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isBadRequest());

        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = alumniProfileRepository.findAll().size();
        // set the field null
        alumniProfile.setDob(null);

        // Create the AlumniProfile, which fails.
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);


        restAlumniProfileMockMvc.perform(post("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isBadRequest());

        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAlumniProfiles() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumniProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME)))
            .andExpect(jsonPath("$.[*].currentTerm").value(hasItem(DEFAULT_CURRENT_TERM)))
            .andExpect(jsonPath("$.[*].joiningAcademicYear").value(hasItem(DEFAULT_JOINING_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].exitAcademicYear").value(hasItem(DEFAULT_EXIT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].aadhaar").value(hasItem(DEFAULT_AADHAAR)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAlumniProfile() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get the alumniProfile
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles/{id}", alumniProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alumniProfile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.fathersName").value(DEFAULT_FATHERS_NAME))
            .andExpect(jsonPath("$.mothersName").value(DEFAULT_MOTHERS_NAME))
            .andExpect(jsonPath("$.currentTerm").value(DEFAULT_CURRENT_TERM))
            .andExpect(jsonPath("$.joiningAcademicYear").value(DEFAULT_JOINING_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.exitAcademicYear").value(DEFAULT_EXIT_ACADEMIC_YEAR))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.aadhaar").value(DEFAULT_AADHAAR))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAlumniProfilesByIdFiltering() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        Long id = alumniProfile.getId();

        defaultAlumniProfileShouldBeFound("id.equals=" + id);
        defaultAlumniProfileShouldNotBeFound("id.notEquals=" + id);

        defaultAlumniProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlumniProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultAlumniProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlumniProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName equals to DEFAULT_FIRST_NAME
        defaultAlumniProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the alumniProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultAlumniProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName not equals to DEFAULT_FIRST_NAME
        defaultAlumniProfileShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the alumniProfileList where firstName not equals to UPDATED_FIRST_NAME
        defaultAlumniProfileShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultAlumniProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the alumniProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultAlumniProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName is not null
        defaultAlumniProfileShouldBeFound("firstName.specified=true");

        // Get all the alumniProfileList where firstName is null
        defaultAlumniProfileShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName contains DEFAULT_FIRST_NAME
        defaultAlumniProfileShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the alumniProfileList where firstName contains UPDATED_FIRST_NAME
        defaultAlumniProfileShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where firstName does not contain DEFAULT_FIRST_NAME
        defaultAlumniProfileShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the alumniProfileList where firstName does not contain UPDATED_FIRST_NAME
        defaultAlumniProfileShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName equals to DEFAULT_LAST_NAME
        defaultAlumniProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the alumniProfileList where lastName equals to UPDATED_LAST_NAME
        defaultAlumniProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName not equals to DEFAULT_LAST_NAME
        defaultAlumniProfileShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the alumniProfileList where lastName not equals to UPDATED_LAST_NAME
        defaultAlumniProfileShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultAlumniProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the alumniProfileList where lastName equals to UPDATED_LAST_NAME
        defaultAlumniProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName is not null
        defaultAlumniProfileShouldBeFound("lastName.specified=true");

        // Get all the alumniProfileList where lastName is null
        defaultAlumniProfileShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName contains DEFAULT_LAST_NAME
        defaultAlumniProfileShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the alumniProfileList where lastName contains UPDATED_LAST_NAME
        defaultAlumniProfileShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where lastName does not contain DEFAULT_LAST_NAME
        defaultAlumniProfileShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the alumniProfileList where lastName does not contain UPDATED_LAST_NAME
        defaultAlumniProfileShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName equals to DEFAULT_FATHERS_NAME
        defaultAlumniProfileShouldBeFound("fathersName.equals=" + DEFAULT_FATHERS_NAME);

        // Get all the alumniProfileList where fathersName equals to UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldNotBeFound("fathersName.equals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName not equals to DEFAULT_FATHERS_NAME
        defaultAlumniProfileShouldNotBeFound("fathersName.notEquals=" + DEFAULT_FATHERS_NAME);

        // Get all the alumniProfileList where fathersName not equals to UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldBeFound("fathersName.notEquals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName in DEFAULT_FATHERS_NAME or UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldBeFound("fathersName.in=" + DEFAULT_FATHERS_NAME + "," + UPDATED_FATHERS_NAME);

        // Get all the alumniProfileList where fathersName equals to UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldNotBeFound("fathersName.in=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName is not null
        defaultAlumniProfileShouldBeFound("fathersName.specified=true");

        // Get all the alumniProfileList where fathersName is null
        defaultAlumniProfileShouldNotBeFound("fathersName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName contains DEFAULT_FATHERS_NAME
        defaultAlumniProfileShouldBeFound("fathersName.contains=" + DEFAULT_FATHERS_NAME);

        // Get all the alumniProfileList where fathersName contains UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldNotBeFound("fathersName.contains=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByFathersNameNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where fathersName does not contain DEFAULT_FATHERS_NAME
        defaultAlumniProfileShouldNotBeFound("fathersName.doesNotContain=" + DEFAULT_FATHERS_NAME);

        // Get all the alumniProfileList where fathersName does not contain UPDATED_FATHERS_NAME
        defaultAlumniProfileShouldBeFound("fathersName.doesNotContain=" + UPDATED_FATHERS_NAME);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName equals to DEFAULT_MOTHERS_NAME
        defaultAlumniProfileShouldBeFound("mothersName.equals=" + DEFAULT_MOTHERS_NAME);

        // Get all the alumniProfileList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldNotBeFound("mothersName.equals=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName not equals to DEFAULT_MOTHERS_NAME
        defaultAlumniProfileShouldNotBeFound("mothersName.notEquals=" + DEFAULT_MOTHERS_NAME);

        // Get all the alumniProfileList where mothersName not equals to UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldBeFound("mothersName.notEquals=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName in DEFAULT_MOTHERS_NAME or UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldBeFound("mothersName.in=" + DEFAULT_MOTHERS_NAME + "," + UPDATED_MOTHERS_NAME);

        // Get all the alumniProfileList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldNotBeFound("mothersName.in=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName is not null
        defaultAlumniProfileShouldBeFound("mothersName.specified=true");

        // Get all the alumniProfileList where mothersName is null
        defaultAlumniProfileShouldNotBeFound("mothersName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName contains DEFAULT_MOTHERS_NAME
        defaultAlumniProfileShouldBeFound("mothersName.contains=" + DEFAULT_MOTHERS_NAME);

        // Get all the alumniProfileList where mothersName contains UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldNotBeFound("mothersName.contains=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMothersNameNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mothersName does not contain DEFAULT_MOTHERS_NAME
        defaultAlumniProfileShouldNotBeFound("mothersName.doesNotContain=" + DEFAULT_MOTHERS_NAME);

        // Get all the alumniProfileList where mothersName does not contain UPDATED_MOTHERS_NAME
        defaultAlumniProfileShouldBeFound("mothersName.doesNotContain=" + UPDATED_MOTHERS_NAME);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm equals to DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.equals=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm equals to UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.equals=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm not equals to DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.notEquals=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm not equals to UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.notEquals=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm in DEFAULT_CURRENT_TERM or UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.in=" + DEFAULT_CURRENT_TERM + "," + UPDATED_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm equals to UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.in=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm is not null
        defaultAlumniProfileShouldBeFound("currentTerm.specified=true");

        // Get all the alumniProfileList where currentTerm is null
        defaultAlumniProfileShouldNotBeFound("currentTerm.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm is greater than or equal to DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.greaterThanOrEqual=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm is greater than or equal to UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.greaterThanOrEqual=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm is less than or equal to DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.lessThanOrEqual=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm is less than or equal to SMALLER_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.lessThanOrEqual=" + SMALLER_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsLessThanSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm is less than DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.lessThan=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm is less than UPDATED_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.lessThan=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByCurrentTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where currentTerm is greater than DEFAULT_CURRENT_TERM
        defaultAlumniProfileShouldNotBeFound("currentTerm.greaterThan=" + DEFAULT_CURRENT_TERM);

        // Get all the alumniProfileList where currentTerm is greater than SMALLER_CURRENT_TERM
        defaultAlumniProfileShouldBeFound("currentTerm.greaterThan=" + SMALLER_CURRENT_TERM);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear equals to DEFAULT_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.equals=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the alumniProfileList where joiningAcademicYear equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.equals=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear not equals to DEFAULT_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.notEquals=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the alumniProfileList where joiningAcademicYear not equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.notEquals=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear in DEFAULT_JOINING_ACADEMIC_YEAR or UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.in=" + DEFAULT_JOINING_ACADEMIC_YEAR + "," + UPDATED_JOINING_ACADEMIC_YEAR);

        // Get all the alumniProfileList where joiningAcademicYear equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.in=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear is not null
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.specified=true");

        // Get all the alumniProfileList where joiningAcademicYear is null
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear contains DEFAULT_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.contains=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the alumniProfileList where joiningAcademicYear contains UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.contains=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByJoiningAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where joiningAcademicYear does not contain DEFAULT_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("joiningAcademicYear.doesNotContain=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the alumniProfileList where joiningAcademicYear does not contain UPDATED_JOINING_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("joiningAcademicYear.doesNotContain=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear equals to DEFAULT_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("exitAcademicYear.equals=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the alumniProfileList where exitAcademicYear equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.equals=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear not equals to DEFAULT_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.notEquals=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the alumniProfileList where exitAcademicYear not equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("exitAcademicYear.notEquals=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear in DEFAULT_EXIT_ACADEMIC_YEAR or UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("exitAcademicYear.in=" + DEFAULT_EXIT_ACADEMIC_YEAR + "," + UPDATED_EXIT_ACADEMIC_YEAR);

        // Get all the alumniProfileList where exitAcademicYear equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.in=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear is not null
        defaultAlumniProfileShouldBeFound("exitAcademicYear.specified=true");

        // Get all the alumniProfileList where exitAcademicYear is null
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear contains DEFAULT_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("exitAcademicYear.contains=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the alumniProfileList where exitAcademicYear contains UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.contains=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByExitAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where exitAcademicYear does not contain DEFAULT_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldNotBeFound("exitAcademicYear.doesNotContain=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the alumniProfileList where exitAcademicYear does not contain UPDATED_EXIT_ACADEMIC_YEAR
        defaultAlumniProfileShouldBeFound("exitAcademicYear.doesNotContain=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile equals to DEFAULT_MOBILE
        defaultAlumniProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the alumniProfileList where mobile equals to UPDATED_MOBILE
        defaultAlumniProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile not equals to DEFAULT_MOBILE
        defaultAlumniProfileShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the alumniProfileList where mobile not equals to UPDATED_MOBILE
        defaultAlumniProfileShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultAlumniProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the alumniProfileList where mobile equals to UPDATED_MOBILE
        defaultAlumniProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile is not null
        defaultAlumniProfileShouldBeFound("mobile.specified=true");

        // Get all the alumniProfileList where mobile is null
        defaultAlumniProfileShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByMobileContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile contains DEFAULT_MOBILE
        defaultAlumniProfileShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the alumniProfileList where mobile contains UPDATED_MOBILE
        defaultAlumniProfileShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where mobile does not contain DEFAULT_MOBILE
        defaultAlumniProfileShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the alumniProfileList where mobile does not contain UPDATED_MOBILE
        defaultAlumniProfileShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email equals to DEFAULT_EMAIL
        defaultAlumniProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the alumniProfileList where email equals to UPDATED_EMAIL
        defaultAlumniProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email not equals to DEFAULT_EMAIL
        defaultAlumniProfileShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the alumniProfileList where email not equals to UPDATED_EMAIL
        defaultAlumniProfileShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultAlumniProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the alumniProfileList where email equals to UPDATED_EMAIL
        defaultAlumniProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email is not null
        defaultAlumniProfileShouldBeFound("email.specified=true");

        // Get all the alumniProfileList where email is null
        defaultAlumniProfileShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email contains DEFAULT_EMAIL
        defaultAlumniProfileShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the alumniProfileList where email contains UPDATED_EMAIL
        defaultAlumniProfileShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where email does not contain DEFAULT_EMAIL
        defaultAlumniProfileShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the alumniProfileList where email does not contain UPDATED_EMAIL
        defaultAlumniProfileShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar equals to DEFAULT_AADHAAR
        defaultAlumniProfileShouldBeFound("aadhaar.equals=" + DEFAULT_AADHAAR);

        // Get all the alumniProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultAlumniProfileShouldNotBeFound("aadhaar.equals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar not equals to DEFAULT_AADHAAR
        defaultAlumniProfileShouldNotBeFound("aadhaar.notEquals=" + DEFAULT_AADHAAR);

        // Get all the alumniProfileList where aadhaar not equals to UPDATED_AADHAAR
        defaultAlumniProfileShouldBeFound("aadhaar.notEquals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar in DEFAULT_AADHAAR or UPDATED_AADHAAR
        defaultAlumniProfileShouldBeFound("aadhaar.in=" + DEFAULT_AADHAAR + "," + UPDATED_AADHAAR);

        // Get all the alumniProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultAlumniProfileShouldNotBeFound("aadhaar.in=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar is not null
        defaultAlumniProfileShouldBeFound("aadhaar.specified=true");

        // Get all the alumniProfileList where aadhaar is null
        defaultAlumniProfileShouldNotBeFound("aadhaar.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar contains DEFAULT_AADHAAR
        defaultAlumniProfileShouldBeFound("aadhaar.contains=" + DEFAULT_AADHAAR);

        // Get all the alumniProfileList where aadhaar contains UPDATED_AADHAAR
        defaultAlumniProfileShouldNotBeFound("aadhaar.contains=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByAadhaarNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where aadhaar does not contain DEFAULT_AADHAAR
        defaultAlumniProfileShouldNotBeFound("aadhaar.doesNotContain=" + DEFAULT_AADHAAR);

        // Get all the alumniProfileList where aadhaar does not contain UPDATED_AADHAAR
        defaultAlumniProfileShouldBeFound("aadhaar.doesNotContain=" + UPDATED_AADHAAR);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob equals to DEFAULT_DOB
        defaultAlumniProfileShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob equals to UPDATED_DOB
        defaultAlumniProfileShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob not equals to DEFAULT_DOB
        defaultAlumniProfileShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob not equals to UPDATED_DOB
        defaultAlumniProfileShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultAlumniProfileShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the alumniProfileList where dob equals to UPDATED_DOB
        defaultAlumniProfileShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob is not null
        defaultAlumniProfileShouldBeFound("dob.specified=true");

        // Get all the alumniProfileList where dob is null
        defaultAlumniProfileShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob is greater than or equal to DEFAULT_DOB
        defaultAlumniProfileShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob is greater than or equal to UPDATED_DOB
        defaultAlumniProfileShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob is less than or equal to DEFAULT_DOB
        defaultAlumniProfileShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob is less than or equal to SMALLER_DOB
        defaultAlumniProfileShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob is less than DEFAULT_DOB
        defaultAlumniProfileShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob is less than UPDATED_DOB
        defaultAlumniProfileShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where dob is greater than DEFAULT_DOB
        defaultAlumniProfileShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the alumniProfileList where dob is greater than SMALLER_DOB
        defaultAlumniProfileShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink equals to DEFAULT_IMG_LINK
        defaultAlumniProfileShouldBeFound("imgLink.equals=" + DEFAULT_IMG_LINK);

        // Get all the alumniProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultAlumniProfileShouldNotBeFound("imgLink.equals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink not equals to DEFAULT_IMG_LINK
        defaultAlumniProfileShouldNotBeFound("imgLink.notEquals=" + DEFAULT_IMG_LINK);

        // Get all the alumniProfileList where imgLink not equals to UPDATED_IMG_LINK
        defaultAlumniProfileShouldBeFound("imgLink.notEquals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink in DEFAULT_IMG_LINK or UPDATED_IMG_LINK
        defaultAlumniProfileShouldBeFound("imgLink.in=" + DEFAULT_IMG_LINK + "," + UPDATED_IMG_LINK);

        // Get all the alumniProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultAlumniProfileShouldNotBeFound("imgLink.in=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink is not null
        defaultAlumniProfileShouldBeFound("imgLink.specified=true");

        // Get all the alumniProfileList where imgLink is null
        defaultAlumniProfileShouldNotBeFound("imgLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink contains DEFAULT_IMG_LINK
        defaultAlumniProfileShouldBeFound("imgLink.contains=" + DEFAULT_IMG_LINK);

        // Get all the alumniProfileList where imgLink contains UPDATED_IMG_LINK
        defaultAlumniProfileShouldNotBeFound("imgLink.contains=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByImgLinkNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where imgLink does not contain DEFAULT_IMG_LINK
        defaultAlumniProfileShouldNotBeFound("imgLink.doesNotContain=" + DEFAULT_IMG_LINK);

        // Get all the alumniProfileList where imgLink does not contain UPDATED_IMG_LINK
        defaultAlumniProfileShouldBeFound("imgLink.doesNotContain=" + UPDATED_IMG_LINK);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId equals to DEFAULT_TENANT_ID
        defaultAlumniProfileShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the alumniProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultAlumniProfileShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId not equals to DEFAULT_TENANT_ID
        defaultAlumniProfileShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the alumniProfileList where tenantId not equals to UPDATED_TENANT_ID
        defaultAlumniProfileShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultAlumniProfileShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the alumniProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultAlumniProfileShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId is not null
        defaultAlumniProfileShouldBeFound("tenantId.specified=true");

        // Get all the alumniProfileList where tenantId is null
        defaultAlumniProfileShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId contains DEFAULT_TENANT_ID
        defaultAlumniProfileShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the alumniProfileList where tenantId contains UPDATED_TENANT_ID
        defaultAlumniProfileShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where tenantId does not contain DEFAULT_TENANT_ID
        defaultAlumniProfileShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the alumniProfileList where tenantId does not contain UPDATED_TENANT_ID
        defaultAlumniProfileShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllAlumniProfilesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where active equals to DEFAULT_ACTIVE
        defaultAlumniProfileShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the alumniProfileList where active equals to UPDATED_ACTIVE
        defaultAlumniProfileShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where active not equals to DEFAULT_ACTIVE
        defaultAlumniProfileShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the alumniProfileList where active not equals to UPDATED_ACTIVE
        defaultAlumniProfileShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAlumniProfileShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the alumniProfileList where active equals to UPDATED_ACTIVE
        defaultAlumniProfileShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAlumniProfilesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        // Get all the alumniProfileList where active is not null
        defaultAlumniProfileShouldBeFound("active.specified=true");

        // Get all the alumniProfileList where active is null
        defaultAlumniProfileShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlumniProfileShouldBeFound(String filter) throws Exception {
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alumniProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].fathersName").value(hasItem(DEFAULT_FATHERS_NAME)))
            .andExpect(jsonPath("$.[*].mothersName").value(hasItem(DEFAULT_MOTHERS_NAME)))
            .andExpect(jsonPath("$.[*].currentTerm").value(hasItem(DEFAULT_CURRENT_TERM)))
            .andExpect(jsonPath("$.[*].joiningAcademicYear").value(hasItem(DEFAULT_JOINING_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].exitAcademicYear").value(hasItem(DEFAULT_EXIT_ACADEMIC_YEAR)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].aadhaar").value(hasItem(DEFAULT_AADHAAR)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlumniProfileShouldNotBeFound(String filter) throws Exception {
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAlumniProfile() throws Exception {
        // Get the alumniProfile
        restAlumniProfileMockMvc.perform(get("/api/alumni-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlumniProfile() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        int databaseSizeBeforeUpdate = alumniProfileRepository.findAll().size();

        // Update the alumniProfile
        AlumniProfile updatedAlumniProfile = alumniProfileRepository.findById(alumniProfile.getId()).get();
        // Disconnect from session so that the updates on updatedAlumniProfile are not directly saved in db
        em.detach(updatedAlumniProfile);
        updatedAlumniProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .fathersName(UPDATED_FATHERS_NAME)
            .mothersName(UPDATED_MOTHERS_NAME)
            .currentTerm(UPDATED_CURRENT_TERM)
            .joiningAcademicYear(UPDATED_JOINING_ACADEMIC_YEAR)
            .exitAcademicYear(UPDATED_EXIT_ACADEMIC_YEAR)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID)
            .active(UPDATED_ACTIVE);
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(updatedAlumniProfile);

        restAlumniProfileMockMvc.perform(put("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isOk());

        // Validate the AlumniProfile in the database
        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeUpdate);
        AlumniProfile testAlumniProfile = alumniProfileList.get(alumniProfileList.size() - 1);
        assertThat(testAlumniProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAlumniProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAlumniProfile.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testAlumniProfile.getMothersName()).isEqualTo(UPDATED_MOTHERS_NAME);
        assertThat(testAlumniProfile.getCurrentTerm()).isEqualTo(UPDATED_CURRENT_TERM);
        assertThat(testAlumniProfile.getJoiningAcademicYear()).isEqualTo(UPDATED_JOINING_ACADEMIC_YEAR);
        assertThat(testAlumniProfile.getExitAcademicYear()).isEqualTo(UPDATED_EXIT_ACADEMIC_YEAR);
        assertThat(testAlumniProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testAlumniProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAlumniProfile.getAadhaar()).isEqualTo(UPDATED_AADHAAR);
        assertThat(testAlumniProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testAlumniProfile.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testAlumniProfile.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testAlumniProfile.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testAlumniProfile.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testAlumniProfile.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAlumniProfile() throws Exception {
        int databaseSizeBeforeUpdate = alumniProfileRepository.findAll().size();

        // Create the AlumniProfile
        AlumniProfileDTO alumniProfileDTO = alumniProfileMapper.toDto(alumniProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlumniProfileMockMvc.perform(put("/api/alumni-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(alumniProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AlumniProfile in the database
        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlumniProfile() throws Exception {
        // Initialize the database
        alumniProfileRepository.saveAndFlush(alumniProfile);

        int databaseSizeBeforeDelete = alumniProfileRepository.findAll().size();

        // Delete the alumniProfile
        restAlumniProfileMockMvc.perform(delete("/api/alumni-profiles/{id}", alumniProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AlumniProfile> alumniProfileList = alumniProfileRepository.findAll();
        assertThat(alumniProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
