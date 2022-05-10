package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Institute;
import com.myriadquest.kreiscms.repository.InstituteRepository;
import com.myriadquest.kreiscms.service.InstituteService;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;
import com.myriadquest.kreiscms.service.mapper.InstituteMapper;
import com.myriadquest.kreiscms.service.dto.InstituteCriteria;
import com.myriadquest.kreiscms.service.InstituteQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InstituteResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InstituteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TAG_LINE = "AAAAAAAAAA";
    private static final String UPDATED_TAG_LINE = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private InstituteMapper instituteMapper;

    @Autowired
    private InstituteService instituteService;

    @Autowired
    private InstituteQueryService instituteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstituteMockMvc;

    private Institute institute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institute createEntity(EntityManager em) {
        Institute institute = new Institute()
            .name(DEFAULT_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .contact(DEFAULT_CONTACT)
            .logoLink(DEFAULT_LOGO_LINK)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .tagLine(DEFAULT_TAG_LINE)
            .tenantId(DEFAULT_TENANT_ID);
        return institute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institute createUpdatedEntity(EntityManager em) {
        Institute institute = new Institute()
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .logoLink(UPDATED_LOGO_LINK)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .tagLine(UPDATED_TAG_LINE)
            .tenantId(UPDATED_TENANT_ID);
        return institute;
    }

    @BeforeEach
    public void initTest() {
        institute = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitute() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();
        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isCreated());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate + 1);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInstitute.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testInstitute.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInstitute.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstitute.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testInstitute.getLogoLink()).isEqualTo(DEFAULT_LOGO_LINK);
        assertThat(testInstitute.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testInstitute.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testInstitute.getTagLine()).isEqualTo(DEFAULT_TAG_LINE);
        assertThat(testInstitute.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createInstituteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instituteRepository.findAll().size();

        // Create the Institute with an existing ID
        institute.setId(1L);
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setName(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setShortName(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setAddress(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setEmail(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setContact(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLogoLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setLogoLink(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTagLineIsRequired() throws Exception {
        int databaseSizeBeforeTest = instituteRepository.findAll().size();
        // set the field null
        institute.setTagLine(null);

        // Create the Institute, which fails.
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);


        restInstituteMockMvc.perform(post("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInstitutes() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList
        restInstituteMockMvc.perform(get("/api/institutes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].logoLink").value(hasItem(DEFAULT_LOGO_LINK)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", institute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(institute.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.logoLink").value(DEFAULT_LOGO_LINK))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.tagLine").value(DEFAULT_TAG_LINE))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getInstitutesByIdFiltering() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        Long id = institute.getId();

        defaultInstituteShouldBeFound("id.equals=" + id);
        defaultInstituteShouldNotBeFound("id.notEquals=" + id);

        defaultInstituteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInstituteShouldNotBeFound("id.greaterThan=" + id);

        defaultInstituteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInstituteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInstitutesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name equals to DEFAULT_NAME
        defaultInstituteShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the instituteList where name equals to UPDATED_NAME
        defaultInstituteShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name not equals to DEFAULT_NAME
        defaultInstituteShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the instituteList where name not equals to UPDATED_NAME
        defaultInstituteShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInstituteShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the instituteList where name equals to UPDATED_NAME
        defaultInstituteShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name is not null
        defaultInstituteShouldBeFound("name.specified=true");

        // Get all the instituteList where name is null
        defaultInstituteShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByNameContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name contains DEFAULT_NAME
        defaultInstituteShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the instituteList where name contains UPDATED_NAME
        defaultInstituteShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where name does not contain DEFAULT_NAME
        defaultInstituteShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the instituteList where name does not contain UPDATED_NAME
        defaultInstituteShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllInstitutesByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName equals to DEFAULT_SHORT_NAME
        defaultInstituteShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the instituteList where shortName equals to UPDATED_SHORT_NAME
        defaultInstituteShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName not equals to DEFAULT_SHORT_NAME
        defaultInstituteShouldNotBeFound("shortName.notEquals=" + DEFAULT_SHORT_NAME);

        // Get all the instituteList where shortName not equals to UPDATED_SHORT_NAME
        defaultInstituteShouldBeFound("shortName.notEquals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultInstituteShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the instituteList where shortName equals to UPDATED_SHORT_NAME
        defaultInstituteShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName is not null
        defaultInstituteShouldBeFound("shortName.specified=true");

        // Get all the instituteList where shortName is null
        defaultInstituteShouldNotBeFound("shortName.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByShortNameContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName contains DEFAULT_SHORT_NAME
        defaultInstituteShouldBeFound("shortName.contains=" + DEFAULT_SHORT_NAME);

        // Get all the instituteList where shortName contains UPDATED_SHORT_NAME
        defaultInstituteShouldNotBeFound("shortName.contains=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllInstitutesByShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where shortName does not contain DEFAULT_SHORT_NAME
        defaultInstituteShouldNotBeFound("shortName.doesNotContain=" + DEFAULT_SHORT_NAME);

        // Get all the instituteList where shortName does not contain UPDATED_SHORT_NAME
        defaultInstituteShouldBeFound("shortName.doesNotContain=" + UPDATED_SHORT_NAME);
    }


    @Test
    @Transactional
    public void getAllInstitutesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address equals to DEFAULT_ADDRESS
        defaultInstituteShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the instituteList where address equals to UPDATED_ADDRESS
        defaultInstituteShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInstitutesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address not equals to DEFAULT_ADDRESS
        defaultInstituteShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the instituteList where address not equals to UPDATED_ADDRESS
        defaultInstituteShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInstitutesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultInstituteShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the instituteList where address equals to UPDATED_ADDRESS
        defaultInstituteShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInstitutesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address is not null
        defaultInstituteShouldBeFound("address.specified=true");

        // Get all the instituteList where address is null
        defaultInstituteShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByAddressContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address contains DEFAULT_ADDRESS
        defaultInstituteShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the instituteList where address contains UPDATED_ADDRESS
        defaultInstituteShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllInstitutesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where address does not contain DEFAULT_ADDRESS
        defaultInstituteShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the instituteList where address does not contain UPDATED_ADDRESS
        defaultInstituteShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllInstitutesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email equals to DEFAULT_EMAIL
        defaultInstituteShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the instituteList where email equals to UPDATED_EMAIL
        defaultInstituteShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstitutesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email not equals to DEFAULT_EMAIL
        defaultInstituteShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the instituteList where email not equals to UPDATED_EMAIL
        defaultInstituteShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstitutesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultInstituteShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the instituteList where email equals to UPDATED_EMAIL
        defaultInstituteShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstitutesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email is not null
        defaultInstituteShouldBeFound("email.specified=true");

        // Get all the instituteList where email is null
        defaultInstituteShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByEmailContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email contains DEFAULT_EMAIL
        defaultInstituteShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the instituteList where email contains UPDATED_EMAIL
        defaultInstituteShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstitutesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where email does not contain DEFAULT_EMAIL
        defaultInstituteShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the instituteList where email does not contain UPDATED_EMAIL
        defaultInstituteShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllInstitutesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact equals to DEFAULT_CONTACT
        defaultInstituteShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the instituteList where contact equals to UPDATED_CONTACT
        defaultInstituteShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllInstitutesByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact not equals to DEFAULT_CONTACT
        defaultInstituteShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the instituteList where contact not equals to UPDATED_CONTACT
        defaultInstituteShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllInstitutesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultInstituteShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the instituteList where contact equals to UPDATED_CONTACT
        defaultInstituteShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllInstitutesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact is not null
        defaultInstituteShouldBeFound("contact.specified=true");

        // Get all the instituteList where contact is null
        defaultInstituteShouldNotBeFound("contact.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByContactContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact contains DEFAULT_CONTACT
        defaultInstituteShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the instituteList where contact contains UPDATED_CONTACT
        defaultInstituteShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllInstitutesByContactNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where contact does not contain DEFAULT_CONTACT
        defaultInstituteShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the instituteList where contact does not contain UPDATED_CONTACT
        defaultInstituteShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }


    @Test
    @Transactional
    public void getAllInstitutesByLogoLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink equals to DEFAULT_LOGO_LINK
        defaultInstituteShouldBeFound("logoLink.equals=" + DEFAULT_LOGO_LINK);

        // Get all the instituteList where logoLink equals to UPDATED_LOGO_LINK
        defaultInstituteShouldNotBeFound("logoLink.equals=" + UPDATED_LOGO_LINK);
    }

    @Test
    @Transactional
    public void getAllInstitutesByLogoLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink not equals to DEFAULT_LOGO_LINK
        defaultInstituteShouldNotBeFound("logoLink.notEquals=" + DEFAULT_LOGO_LINK);

        // Get all the instituteList where logoLink not equals to UPDATED_LOGO_LINK
        defaultInstituteShouldBeFound("logoLink.notEquals=" + UPDATED_LOGO_LINK);
    }

    @Test
    @Transactional
    public void getAllInstitutesByLogoLinkIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink in DEFAULT_LOGO_LINK or UPDATED_LOGO_LINK
        defaultInstituteShouldBeFound("logoLink.in=" + DEFAULT_LOGO_LINK + "," + UPDATED_LOGO_LINK);

        // Get all the instituteList where logoLink equals to UPDATED_LOGO_LINK
        defaultInstituteShouldNotBeFound("logoLink.in=" + UPDATED_LOGO_LINK);
    }

    @Test
    @Transactional
    public void getAllInstitutesByLogoLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink is not null
        defaultInstituteShouldBeFound("logoLink.specified=true");

        // Get all the instituteList where logoLink is null
        defaultInstituteShouldNotBeFound("logoLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByLogoLinkContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink contains DEFAULT_LOGO_LINK
        defaultInstituteShouldBeFound("logoLink.contains=" + DEFAULT_LOGO_LINK);

        // Get all the instituteList where logoLink contains UPDATED_LOGO_LINK
        defaultInstituteShouldNotBeFound("logoLink.contains=" + UPDATED_LOGO_LINK);
    }

    @Test
    @Transactional
    public void getAllInstitutesByLogoLinkNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where logoLink does not contain DEFAULT_LOGO_LINK
        defaultInstituteShouldNotBeFound("logoLink.doesNotContain=" + DEFAULT_LOGO_LINK);

        // Get all the instituteList where logoLink does not contain UPDATED_LOGO_LINK
        defaultInstituteShouldBeFound("logoLink.doesNotContain=" + UPDATED_LOGO_LINK);
    }


    @Test
    @Transactional
    public void getAllInstitutesByTagLineIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine equals to DEFAULT_TAG_LINE
        defaultInstituteShouldBeFound("tagLine.equals=" + DEFAULT_TAG_LINE);

        // Get all the instituteList where tagLine equals to UPDATED_TAG_LINE
        defaultInstituteShouldNotBeFound("tagLine.equals=" + UPDATED_TAG_LINE);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTagLineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine not equals to DEFAULT_TAG_LINE
        defaultInstituteShouldNotBeFound("tagLine.notEquals=" + DEFAULT_TAG_LINE);

        // Get all the instituteList where tagLine not equals to UPDATED_TAG_LINE
        defaultInstituteShouldBeFound("tagLine.notEquals=" + UPDATED_TAG_LINE);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTagLineIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine in DEFAULT_TAG_LINE or UPDATED_TAG_LINE
        defaultInstituteShouldBeFound("tagLine.in=" + DEFAULT_TAG_LINE + "," + UPDATED_TAG_LINE);

        // Get all the instituteList where tagLine equals to UPDATED_TAG_LINE
        defaultInstituteShouldNotBeFound("tagLine.in=" + UPDATED_TAG_LINE);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTagLineIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine is not null
        defaultInstituteShouldBeFound("tagLine.specified=true");

        // Get all the instituteList where tagLine is null
        defaultInstituteShouldNotBeFound("tagLine.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByTagLineContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine contains DEFAULT_TAG_LINE
        defaultInstituteShouldBeFound("tagLine.contains=" + DEFAULT_TAG_LINE);

        // Get all the instituteList where tagLine contains UPDATED_TAG_LINE
        defaultInstituteShouldNotBeFound("tagLine.contains=" + UPDATED_TAG_LINE);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTagLineNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tagLine does not contain DEFAULT_TAG_LINE
        defaultInstituteShouldNotBeFound("tagLine.doesNotContain=" + DEFAULT_TAG_LINE);

        // Get all the instituteList where tagLine does not contain UPDATED_TAG_LINE
        defaultInstituteShouldBeFound("tagLine.doesNotContain=" + UPDATED_TAG_LINE);
    }


    @Test
    @Transactional
    public void getAllInstitutesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId equals to DEFAULT_TENANT_ID
        defaultInstituteShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the instituteList where tenantId equals to UPDATED_TENANT_ID
        defaultInstituteShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId not equals to DEFAULT_TENANT_ID
        defaultInstituteShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the instituteList where tenantId not equals to UPDATED_TENANT_ID
        defaultInstituteShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultInstituteShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the instituteList where tenantId equals to UPDATED_TENANT_ID
        defaultInstituteShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId is not null
        defaultInstituteShouldBeFound("tenantId.specified=true");

        // Get all the instituteList where tenantId is null
        defaultInstituteShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstitutesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId contains DEFAULT_TENANT_ID
        defaultInstituteShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the instituteList where tenantId contains UPDATED_TENANT_ID
        defaultInstituteShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllInstitutesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        // Get all the instituteList where tenantId does not contain DEFAULT_TENANT_ID
        defaultInstituteShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the instituteList where tenantId does not contain UPDATED_TENANT_ID
        defaultInstituteShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstituteShouldBeFound(String filter) throws Exception {
        restInstituteMockMvc.perform(get("/api/institutes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institute.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].logoLink").value(hasItem(DEFAULT_LOGO_LINK)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restInstituteMockMvc.perform(get("/api/institutes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstituteShouldNotBeFound(String filter) throws Exception {
        restInstituteMockMvc.perform(get("/api/institutes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstituteMockMvc.perform(get("/api/institutes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInstitute() throws Exception {
        // Get the institute
        restInstituteMockMvc.perform(get("/api/institutes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Update the institute
        Institute updatedInstitute = instituteRepository.findById(institute.getId()).get();
        // Disconnect from session so that the updates on updatedInstitute are not directly saved in db
        em.detach(updatedInstitute);
        updatedInstitute
            .name(UPDATED_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .logoLink(UPDATED_LOGO_LINK)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .tagLine(UPDATED_TAG_LINE)
            .tenantId(UPDATED_TENANT_ID);
        InstituteDTO instituteDTO = instituteMapper.toDto(updatedInstitute);

        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isOk());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
        Institute testInstitute = instituteList.get(instituteList.size() - 1);
        assertThat(testInstitute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInstitute.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testInstitute.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInstitute.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstitute.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testInstitute.getLogoLink()).isEqualTo(UPDATED_LOGO_LINK);
        assertThat(testInstitute.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testInstitute.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testInstitute.getTagLine()).isEqualTo(UPDATED_TAG_LINE);
        assertThat(testInstitute.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitute() throws Exception {
        int databaseSizeBeforeUpdate = instituteRepository.findAll().size();

        // Create the Institute
        InstituteDTO instituteDTO = instituteMapper.toDto(institute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstituteMockMvc.perform(put("/api/institutes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instituteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Institute in the database
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstitute() throws Exception {
        // Initialize the database
        instituteRepository.saveAndFlush(institute);

        int databaseSizeBeforeDelete = instituteRepository.findAll().size();

        // Delete the institute
        restInstituteMockMvc.perform(delete("/api/institutes/{id}", institute.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Institute> instituteList = instituteRepository.findAll();
        assertThat(instituteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
