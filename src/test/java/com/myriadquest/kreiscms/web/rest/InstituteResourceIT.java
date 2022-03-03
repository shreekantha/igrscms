package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Institute;
import com.myriadquest.kreiscms.repository.InstituteRepository;
import com.myriadquest.kreiscms.service.InstituteService;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;
import com.myriadquest.kreiscms.service.mapper.InstituteMapper;

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

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private InstituteMapper instituteMapper;

    @Autowired
    private InstituteService instituteService;

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
            .tagLine(DEFAULT_TAG_LINE);
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
            .tagLine(UPDATED_TAG_LINE);
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
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE)));
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
            .andExpect(jsonPath("$.tagLine").value(DEFAULT_TAG_LINE));
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
            .tagLine(UPDATED_TAG_LINE);
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
