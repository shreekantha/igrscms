package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.domain.User;
import com.myriadquest.kreiscms.repository.TermRepository;
import com.myriadquest.kreiscms.service.TermService;
import com.myriadquest.kreiscms.service.dto.TermDTO;
import com.myriadquest.kreiscms.service.mapper.TermMapper;

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
 * Integration tests for the {@link TermResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TermResourceIT {

    private static final Integer DEFAULT_TERM = 1;
    private static final Integer UPDATED_TERM = 2;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_NO_OF_STUDENTS = 1;
    private static final Integer UPDATED_NO_OF_STUDENTS = 2;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private TermService termService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermMockMvc;

    private Term term;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createEntity(EntityManager em) {
        Term term = new Term()
            .term(DEFAULT_TERM)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imgUrl(DEFAULT_IMG_URL)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .noOfStudents(DEFAULT_NO_OF_STUDENTS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        term.setClassTeacher(user);
        return term;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createUpdatedEntity(EntityManager em) {
        Term term = new Term()
            .term(UPDATED_TERM)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .noOfStudents(UPDATED_NO_OF_STUDENTS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        term.setClassTeacher(user);
        return term;
    }

    @BeforeEach
    public void initTest() {
        term = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerm() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();
        // Create the Term
        TermDTO termDTO = termMapper.toDto(term);
        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isCreated());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate + 1);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testTerm.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTerm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTerm.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testTerm.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testTerm.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testTerm.getNoOfStudents()).isEqualTo(DEFAULT_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void createTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();

        // Create the Term with an existing ID
        term.setId(1L);
        TermDTO termDTO = termMapper.toDto(term);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTermIsRequired() throws Exception {
        int databaseSizeBeforeTest = termRepository.findAll().size();
        // set the field null
        term.setTerm(null);

        // Create the Term, which fails.
        TermDTO termDTO = termMapper.toDto(term);


        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = termRepository.findAll().size();
        // set the field null
        term.setTitle(null);

        // Create the Term, which fails.
        TermDTO termDTO = termMapper.toDto(term);


        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = termRepository.findAll().size();
        // set the field null
        term.setImgUrl(null);

        // Create the Term, which fails.
        TermDTO termDTO = termMapper.toDto(term);


        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNoOfStudentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = termRepository.findAll().size();
        // set the field null
        term.setNoOfStudents(null);

        // Create the Term, which fails.
        TermDTO termDTO = termMapper.toDto(term);


        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTerms() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList
        restTermMockMvc.perform(get("/api/terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(term.getId().intValue())))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].noOfStudents").value(hasItem(DEFAULT_NO_OF_STUDENTS)));
    }
    
    @Test
    @Transactional
    public void getTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", term.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(term.getId().intValue()))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.noOfStudents").value(DEFAULT_NO_OF_STUDENTS));
    }
    @Test
    @Transactional
    public void getNonExistingTerm() throws Exception {
        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Update the term
        Term updatedTerm = termRepository.findById(term.getId()).get();
        // Disconnect from session so that the updates on updatedTerm are not directly saved in db
        em.detach(updatedTerm);
        updatedTerm
            .term(UPDATED_TERM)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .noOfStudents(UPDATED_NO_OF_STUDENTS);
        TermDTO termDTO = termMapper.toDto(updatedTerm);

        restTermMockMvc.perform(put("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isOk());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testTerm.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTerm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerm.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testTerm.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testTerm.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testTerm.getNoOfStudents()).isEqualTo(UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingTerm() throws Exception {
        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Create the Term
        TermDTO termDTO = termMapper.toDto(term);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermMockMvc.perform(put("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeDelete = termRepository.findAll().size();

        // Delete the term
        restTermMockMvc.perform(delete("/api/terms/{id}", term.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
