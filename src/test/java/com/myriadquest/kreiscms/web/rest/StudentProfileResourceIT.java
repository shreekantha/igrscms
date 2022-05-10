package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.StudentProfile;
import com.myriadquest.kreiscms.repository.StudentProfileRepository;
import com.myriadquest.kreiscms.service.StudentProfileService;
import com.myriadquest.kreiscms.service.dto.StudentProfileDTO;
import com.myriadquest.kreiscms.service.mapper.StudentProfileMapper;
import com.myriadquest.kreiscms.service.dto.StudentProfileCriteria;
import com.myriadquest.kreiscms.service.StudentProfileQueryService;

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
 * Integration tests for the {@link StudentProfileResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

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

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private StudentProfileMapper studentProfileMapper;

    @Autowired
    private StudentProfileService studentProfileService;

    @Autowired
    private StudentProfileQueryService studentProfileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentProfileMockMvc;

    private StudentProfile studentProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentProfile createEntity(EntityManager em) {
        StudentProfile studentProfile = new StudentProfile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
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
            .tenantId(DEFAULT_TENANT_ID)
            .active(DEFAULT_ACTIVE);
        return studentProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentProfile createUpdatedEntity(EntityManager em) {
        StudentProfile studentProfile = new StudentProfile()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
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
            .tenantId(UPDATED_TENANT_ID)
            .active(UPDATED_ACTIVE);
        return studentProfile;
    }

    @BeforeEach
    public void initTest() {
        studentProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentProfile() throws Exception {
        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();
        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);
        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate + 1);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudentProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStudentProfile.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testStudentProfile.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testStudentProfile.getFathersName()).isEqualTo(DEFAULT_FATHERS_NAME);
        assertThat(testStudentProfile.getMothersName()).isEqualTo(DEFAULT_MOTHERS_NAME);
        assertThat(testStudentProfile.getCurrentTerm()).isEqualTo(DEFAULT_CURRENT_TERM);
        assertThat(testStudentProfile.getJoiningAcademicYear()).isEqualTo(DEFAULT_JOINING_ACADEMIC_YEAR);
        assertThat(testStudentProfile.getExitAcademicYear()).isEqualTo(DEFAULT_EXIT_ACADEMIC_YEAR);
        assertThat(testStudentProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testStudentProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudentProfile.getAadhaar()).isEqualTo(DEFAULT_AADHAAR);
        assertThat(testStudentProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testStudentProfile.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testStudentProfile.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
        assertThat(testStudentProfile.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createStudentProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentProfileRepository.findAll().size();

        // Create the StudentProfile with an existing ID
        studentProfile.setId(1L);
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setFirstName(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);


        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setMobile(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);


        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentProfileRepository.findAll().size();
        // set the field null
        studentProfile.setDob(null);

        // Create the StudentProfile, which fails.
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);


        restStudentProfileMockMvc.perform(post("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isBadRequest());

        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentProfiles() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList
        restStudentProfileMockMvc.perform(get("/api/student-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
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
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get the studentProfile
        restStudentProfileMockMvc.perform(get("/api/student-profiles/{id}", studentProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentProfile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
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
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getStudentProfilesByIdFiltering() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        Long id = studentProfile.getId();

        defaultStudentProfileShouldBeFound("id.equals=" + id);
        defaultStudentProfileShouldNotBeFound("id.notEquals=" + id);

        defaultStudentProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStudentProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultStudentProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStudentProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName equals to DEFAULT_FIRST_NAME
        defaultStudentProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the studentProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultStudentProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName not equals to DEFAULT_FIRST_NAME
        defaultStudentProfileShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the studentProfileList where firstName not equals to UPDATED_FIRST_NAME
        defaultStudentProfileShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultStudentProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the studentProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultStudentProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName is not null
        defaultStudentProfileShouldBeFound("firstName.specified=true");

        // Get all the studentProfileList where firstName is null
        defaultStudentProfileShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName contains DEFAULT_FIRST_NAME
        defaultStudentProfileShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the studentProfileList where firstName contains UPDATED_FIRST_NAME
        defaultStudentProfileShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where firstName does not contain DEFAULT_FIRST_NAME
        defaultStudentProfileShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the studentProfileList where firstName does not contain UPDATED_FIRST_NAME
        defaultStudentProfileShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName equals to DEFAULT_LAST_NAME
        defaultStudentProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the studentProfileList where lastName equals to UPDATED_LAST_NAME
        defaultStudentProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName not equals to DEFAULT_LAST_NAME
        defaultStudentProfileShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the studentProfileList where lastName not equals to UPDATED_LAST_NAME
        defaultStudentProfileShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultStudentProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the studentProfileList where lastName equals to UPDATED_LAST_NAME
        defaultStudentProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName is not null
        defaultStudentProfileShouldBeFound("lastName.specified=true");

        // Get all the studentProfileList where lastName is null
        defaultStudentProfileShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName contains DEFAULT_LAST_NAME
        defaultStudentProfileShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the studentProfileList where lastName contains UPDATED_LAST_NAME
        defaultStudentProfileShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where lastName does not contain DEFAULT_LAST_NAME
        defaultStudentProfileShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the studentProfileList where lastName does not contain UPDATED_LAST_NAME
        defaultStudentProfileShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName equals to DEFAULT_FATHERS_NAME
        defaultStudentProfileShouldBeFound("fathersName.equals=" + DEFAULT_FATHERS_NAME);

        // Get all the studentProfileList where fathersName equals to UPDATED_FATHERS_NAME
        defaultStudentProfileShouldNotBeFound("fathersName.equals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName not equals to DEFAULT_FATHERS_NAME
        defaultStudentProfileShouldNotBeFound("fathersName.notEquals=" + DEFAULT_FATHERS_NAME);

        // Get all the studentProfileList where fathersName not equals to UPDATED_FATHERS_NAME
        defaultStudentProfileShouldBeFound("fathersName.notEquals=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName in DEFAULT_FATHERS_NAME or UPDATED_FATHERS_NAME
        defaultStudentProfileShouldBeFound("fathersName.in=" + DEFAULT_FATHERS_NAME + "," + UPDATED_FATHERS_NAME);

        // Get all the studentProfileList where fathersName equals to UPDATED_FATHERS_NAME
        defaultStudentProfileShouldNotBeFound("fathersName.in=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName is not null
        defaultStudentProfileShouldBeFound("fathersName.specified=true");

        // Get all the studentProfileList where fathersName is null
        defaultStudentProfileShouldNotBeFound("fathersName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName contains DEFAULT_FATHERS_NAME
        defaultStudentProfileShouldBeFound("fathersName.contains=" + DEFAULT_FATHERS_NAME);

        // Get all the studentProfileList where fathersName contains UPDATED_FATHERS_NAME
        defaultStudentProfileShouldNotBeFound("fathersName.contains=" + UPDATED_FATHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByFathersNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where fathersName does not contain DEFAULT_FATHERS_NAME
        defaultStudentProfileShouldNotBeFound("fathersName.doesNotContain=" + DEFAULT_FATHERS_NAME);

        // Get all the studentProfileList where fathersName does not contain UPDATED_FATHERS_NAME
        defaultStudentProfileShouldBeFound("fathersName.doesNotContain=" + UPDATED_FATHERS_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName equals to DEFAULT_MOTHERS_NAME
        defaultStudentProfileShouldBeFound("mothersName.equals=" + DEFAULT_MOTHERS_NAME);

        // Get all the studentProfileList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldNotBeFound("mothersName.equals=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName not equals to DEFAULT_MOTHERS_NAME
        defaultStudentProfileShouldNotBeFound("mothersName.notEquals=" + DEFAULT_MOTHERS_NAME);

        // Get all the studentProfileList where mothersName not equals to UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldBeFound("mothersName.notEquals=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName in DEFAULT_MOTHERS_NAME or UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldBeFound("mothersName.in=" + DEFAULT_MOTHERS_NAME + "," + UPDATED_MOTHERS_NAME);

        // Get all the studentProfileList where mothersName equals to UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldNotBeFound("mothersName.in=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName is not null
        defaultStudentProfileShouldBeFound("mothersName.specified=true");

        // Get all the studentProfileList where mothersName is null
        defaultStudentProfileShouldNotBeFound("mothersName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName contains DEFAULT_MOTHERS_NAME
        defaultStudentProfileShouldBeFound("mothersName.contains=" + DEFAULT_MOTHERS_NAME);

        // Get all the studentProfileList where mothersName contains UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldNotBeFound("mothersName.contains=" + UPDATED_MOTHERS_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMothersNameNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mothersName does not contain DEFAULT_MOTHERS_NAME
        defaultStudentProfileShouldNotBeFound("mothersName.doesNotContain=" + DEFAULT_MOTHERS_NAME);

        // Get all the studentProfileList where mothersName does not contain UPDATED_MOTHERS_NAME
        defaultStudentProfileShouldBeFound("mothersName.doesNotContain=" + UPDATED_MOTHERS_NAME);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm equals to DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.equals=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm equals to UPDATED_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.equals=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm not equals to DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.notEquals=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm not equals to UPDATED_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.notEquals=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm in DEFAULT_CURRENT_TERM or UPDATED_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.in=" + DEFAULT_CURRENT_TERM + "," + UPDATED_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm equals to UPDATED_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.in=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm is not null
        defaultStudentProfileShouldBeFound("currentTerm.specified=true");

        // Get all the studentProfileList where currentTerm is null
        defaultStudentProfileShouldNotBeFound("currentTerm.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm is greater than or equal to DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.greaterThanOrEqual=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm is greater than or equal to UPDATED_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.greaterThanOrEqual=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm is less than or equal to DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.lessThanOrEqual=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm is less than or equal to SMALLER_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.lessThanOrEqual=" + SMALLER_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm is less than DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.lessThan=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm is less than UPDATED_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.lessThan=" + UPDATED_CURRENT_TERM);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByCurrentTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where currentTerm is greater than DEFAULT_CURRENT_TERM
        defaultStudentProfileShouldNotBeFound("currentTerm.greaterThan=" + DEFAULT_CURRENT_TERM);

        // Get all the studentProfileList where currentTerm is greater than SMALLER_CURRENT_TERM
        defaultStudentProfileShouldBeFound("currentTerm.greaterThan=" + SMALLER_CURRENT_TERM);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear equals to DEFAULT_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("joiningAcademicYear.equals=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the studentProfileList where joiningAcademicYear equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.equals=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear not equals to DEFAULT_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.notEquals=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the studentProfileList where joiningAcademicYear not equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("joiningAcademicYear.notEquals=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear in DEFAULT_JOINING_ACADEMIC_YEAR or UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("joiningAcademicYear.in=" + DEFAULT_JOINING_ACADEMIC_YEAR + "," + UPDATED_JOINING_ACADEMIC_YEAR);

        // Get all the studentProfileList where joiningAcademicYear equals to UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.in=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear is not null
        defaultStudentProfileShouldBeFound("joiningAcademicYear.specified=true");

        // Get all the studentProfileList where joiningAcademicYear is null
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear contains DEFAULT_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("joiningAcademicYear.contains=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the studentProfileList where joiningAcademicYear contains UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.contains=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByJoiningAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where joiningAcademicYear does not contain DEFAULT_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("joiningAcademicYear.doesNotContain=" + DEFAULT_JOINING_ACADEMIC_YEAR);

        // Get all the studentProfileList where joiningAcademicYear does not contain UPDATED_JOINING_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("joiningAcademicYear.doesNotContain=" + UPDATED_JOINING_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear equals to DEFAULT_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("exitAcademicYear.equals=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the studentProfileList where exitAcademicYear equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.equals=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear not equals to DEFAULT_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.notEquals=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the studentProfileList where exitAcademicYear not equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("exitAcademicYear.notEquals=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear in DEFAULT_EXIT_ACADEMIC_YEAR or UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("exitAcademicYear.in=" + DEFAULT_EXIT_ACADEMIC_YEAR + "," + UPDATED_EXIT_ACADEMIC_YEAR);

        // Get all the studentProfileList where exitAcademicYear equals to UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.in=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear is not null
        defaultStudentProfileShouldBeFound("exitAcademicYear.specified=true");

        // Get all the studentProfileList where exitAcademicYear is null
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear contains DEFAULT_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("exitAcademicYear.contains=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the studentProfileList where exitAcademicYear contains UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.contains=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByExitAcademicYearNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where exitAcademicYear does not contain DEFAULT_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldNotBeFound("exitAcademicYear.doesNotContain=" + DEFAULT_EXIT_ACADEMIC_YEAR);

        // Get all the studentProfileList where exitAcademicYear does not contain UPDATED_EXIT_ACADEMIC_YEAR
        defaultStudentProfileShouldBeFound("exitAcademicYear.doesNotContain=" + UPDATED_EXIT_ACADEMIC_YEAR);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile equals to DEFAULT_MOBILE
        defaultStudentProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the studentProfileList where mobile equals to UPDATED_MOBILE
        defaultStudentProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile not equals to DEFAULT_MOBILE
        defaultStudentProfileShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the studentProfileList where mobile not equals to UPDATED_MOBILE
        defaultStudentProfileShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultStudentProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the studentProfileList where mobile equals to UPDATED_MOBILE
        defaultStudentProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile is not null
        defaultStudentProfileShouldBeFound("mobile.specified=true");

        // Get all the studentProfileList where mobile is null
        defaultStudentProfileShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByMobileContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile contains DEFAULT_MOBILE
        defaultStudentProfileShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the studentProfileList where mobile contains UPDATED_MOBILE
        defaultStudentProfileShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where mobile does not contain DEFAULT_MOBILE
        defaultStudentProfileShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the studentProfileList where mobile does not contain UPDATED_MOBILE
        defaultStudentProfileShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email equals to DEFAULT_EMAIL
        defaultStudentProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the studentProfileList where email equals to UPDATED_EMAIL
        defaultStudentProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email not equals to DEFAULT_EMAIL
        defaultStudentProfileShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the studentProfileList where email not equals to UPDATED_EMAIL
        defaultStudentProfileShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultStudentProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the studentProfileList where email equals to UPDATED_EMAIL
        defaultStudentProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email is not null
        defaultStudentProfileShouldBeFound("email.specified=true");

        // Get all the studentProfileList where email is null
        defaultStudentProfileShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email contains DEFAULT_EMAIL
        defaultStudentProfileShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the studentProfileList where email contains UPDATED_EMAIL
        defaultStudentProfileShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where email does not contain DEFAULT_EMAIL
        defaultStudentProfileShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the studentProfileList where email does not contain UPDATED_EMAIL
        defaultStudentProfileShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar equals to DEFAULT_AADHAAR
        defaultStudentProfileShouldBeFound("aadhaar.equals=" + DEFAULT_AADHAAR);

        // Get all the studentProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultStudentProfileShouldNotBeFound("aadhaar.equals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar not equals to DEFAULT_AADHAAR
        defaultStudentProfileShouldNotBeFound("aadhaar.notEquals=" + DEFAULT_AADHAAR);

        // Get all the studentProfileList where aadhaar not equals to UPDATED_AADHAAR
        defaultStudentProfileShouldBeFound("aadhaar.notEquals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar in DEFAULT_AADHAAR or UPDATED_AADHAAR
        defaultStudentProfileShouldBeFound("aadhaar.in=" + DEFAULT_AADHAAR + "," + UPDATED_AADHAAR);

        // Get all the studentProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultStudentProfileShouldNotBeFound("aadhaar.in=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar is not null
        defaultStudentProfileShouldBeFound("aadhaar.specified=true");

        // Get all the studentProfileList where aadhaar is null
        defaultStudentProfileShouldNotBeFound("aadhaar.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar contains DEFAULT_AADHAAR
        defaultStudentProfileShouldBeFound("aadhaar.contains=" + DEFAULT_AADHAAR);

        // Get all the studentProfileList where aadhaar contains UPDATED_AADHAAR
        defaultStudentProfileShouldNotBeFound("aadhaar.contains=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByAadhaarNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where aadhaar does not contain DEFAULT_AADHAAR
        defaultStudentProfileShouldNotBeFound("aadhaar.doesNotContain=" + DEFAULT_AADHAAR);

        // Get all the studentProfileList where aadhaar does not contain UPDATED_AADHAAR
        defaultStudentProfileShouldBeFound("aadhaar.doesNotContain=" + UPDATED_AADHAAR);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob equals to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob equals to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob not equals to DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob not equals to UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the studentProfileList where dob equals to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is not null
        defaultStudentProfileShouldBeFound("dob.specified=true");

        // Get all the studentProfileList where dob is null
        defaultStudentProfileShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is greater than or equal to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is greater than or equal to UPDATED_DOB
        defaultStudentProfileShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is less than or equal to DEFAULT_DOB
        defaultStudentProfileShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is less than or equal to SMALLER_DOB
        defaultStudentProfileShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is less than DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is less than UPDATED_DOB
        defaultStudentProfileShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where dob is greater than DEFAULT_DOB
        defaultStudentProfileShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the studentProfileList where dob is greater than SMALLER_DOB
        defaultStudentProfileShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink equals to DEFAULT_IMG_LINK
        defaultStudentProfileShouldBeFound("imgLink.equals=" + DEFAULT_IMG_LINK);

        // Get all the studentProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultStudentProfileShouldNotBeFound("imgLink.equals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink not equals to DEFAULT_IMG_LINK
        defaultStudentProfileShouldNotBeFound("imgLink.notEquals=" + DEFAULT_IMG_LINK);

        // Get all the studentProfileList where imgLink not equals to UPDATED_IMG_LINK
        defaultStudentProfileShouldBeFound("imgLink.notEquals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink in DEFAULT_IMG_LINK or UPDATED_IMG_LINK
        defaultStudentProfileShouldBeFound("imgLink.in=" + DEFAULT_IMG_LINK + "," + UPDATED_IMG_LINK);

        // Get all the studentProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultStudentProfileShouldNotBeFound("imgLink.in=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink is not null
        defaultStudentProfileShouldBeFound("imgLink.specified=true");

        // Get all the studentProfileList where imgLink is null
        defaultStudentProfileShouldNotBeFound("imgLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink contains DEFAULT_IMG_LINK
        defaultStudentProfileShouldBeFound("imgLink.contains=" + DEFAULT_IMG_LINK);

        // Get all the studentProfileList where imgLink contains UPDATED_IMG_LINK
        defaultStudentProfileShouldNotBeFound("imgLink.contains=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByImgLinkNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where imgLink does not contain DEFAULT_IMG_LINK
        defaultStudentProfileShouldNotBeFound("imgLink.doesNotContain=" + DEFAULT_IMG_LINK);

        // Get all the studentProfileList where imgLink does not contain UPDATED_IMG_LINK
        defaultStudentProfileShouldBeFound("imgLink.doesNotContain=" + UPDATED_IMG_LINK);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId equals to DEFAULT_TENANT_ID
        defaultStudentProfileShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the studentProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultStudentProfileShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId not equals to DEFAULT_TENANT_ID
        defaultStudentProfileShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the studentProfileList where tenantId not equals to UPDATED_TENANT_ID
        defaultStudentProfileShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultStudentProfileShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the studentProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultStudentProfileShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId is not null
        defaultStudentProfileShouldBeFound("tenantId.specified=true");

        // Get all the studentProfileList where tenantId is null
        defaultStudentProfileShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId contains DEFAULT_TENANT_ID
        defaultStudentProfileShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the studentProfileList where tenantId contains UPDATED_TENANT_ID
        defaultStudentProfileShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where tenantId does not contain DEFAULT_TENANT_ID
        defaultStudentProfileShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the studentProfileList where tenantId does not contain UPDATED_TENANT_ID
        defaultStudentProfileShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllStudentProfilesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where active equals to DEFAULT_ACTIVE
        defaultStudentProfileShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the studentProfileList where active equals to UPDATED_ACTIVE
        defaultStudentProfileShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where active not equals to DEFAULT_ACTIVE
        defaultStudentProfileShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the studentProfileList where active not equals to UPDATED_ACTIVE
        defaultStudentProfileShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultStudentProfileShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the studentProfileList where active equals to UPDATED_ACTIVE
        defaultStudentProfileShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllStudentProfilesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        // Get all the studentProfileList where active is not null
        defaultStudentProfileShouldBeFound("active.specified=true");

        // Get all the studentProfileList where active is null
        defaultStudentProfileShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStudentProfileShouldBeFound(String filter) throws Exception {
        restStudentProfileMockMvc.perform(get("/api/student-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
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
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restStudentProfileMockMvc.perform(get("/api/student-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStudentProfileShouldNotBeFound(String filter) throws Exception {
        restStudentProfileMockMvc.perform(get("/api/student-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStudentProfileMockMvc.perform(get("/api/student-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStudentProfile() throws Exception {
        // Get the studentProfile
        restStudentProfileMockMvc.perform(get("/api/student-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Update the studentProfile
        StudentProfile updatedStudentProfile = studentProfileRepository.findById(studentProfile.getId()).get();
        // Disconnect from session so that the updates on updatedStudentProfile are not directly saved in db
        em.detach(updatedStudentProfile);
        updatedStudentProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
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
            .tenantId(UPDATED_TENANT_ID)
            .active(UPDATED_ACTIVE);
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(updatedStudentProfile);

        restStudentProfileMockMvc.perform(put("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isOk());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
        StudentProfile testStudentProfile = studentProfileList.get(studentProfileList.size() - 1);
        assertThat(testStudentProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudentProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudentProfile.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testStudentProfile.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testStudentProfile.getFathersName()).isEqualTo(UPDATED_FATHERS_NAME);
        assertThat(testStudentProfile.getMothersName()).isEqualTo(UPDATED_MOTHERS_NAME);
        assertThat(testStudentProfile.getCurrentTerm()).isEqualTo(UPDATED_CURRENT_TERM);
        assertThat(testStudentProfile.getJoiningAcademicYear()).isEqualTo(UPDATED_JOINING_ACADEMIC_YEAR);
        assertThat(testStudentProfile.getExitAcademicYear()).isEqualTo(UPDATED_EXIT_ACADEMIC_YEAR);
        assertThat(testStudentProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testStudentProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudentProfile.getAadhaar()).isEqualTo(UPDATED_AADHAAR);
        assertThat(testStudentProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudentProfile.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testStudentProfile.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
        assertThat(testStudentProfile.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentProfile() throws Exception {
        int databaseSizeBeforeUpdate = studentProfileRepository.findAll().size();

        // Create the StudentProfile
        StudentProfileDTO studentProfileDTO = studentProfileMapper.toDto(studentProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentProfileMockMvc.perform(put("/api/student-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentProfile in the database
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentProfile() throws Exception {
        // Initialize the database
        studentProfileRepository.saveAndFlush(studentProfile);

        int databaseSizeBeforeDelete = studentProfileRepository.findAll().size();

        // Delete the studentProfile
        restStudentProfileMockMvc.perform(delete("/api/student-profiles/{id}", studentProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentProfile> studentProfileList = studentProfileRepository.findAll();
        assertThat(studentProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
