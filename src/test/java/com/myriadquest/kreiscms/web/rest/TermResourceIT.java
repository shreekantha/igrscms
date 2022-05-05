package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Term;
import com.myriadquest.kreiscms.domain.ClassTimeTable;
import com.myriadquest.kreiscms.domain.ExamTimeTable;
import com.myriadquest.kreiscms.domain.User;
import com.myriadquest.kreiscms.repository.TermRepository;
import com.myriadquest.kreiscms.service.TermService;
import com.myriadquest.kreiscms.service.dto.TermDTO;
import com.myriadquest.kreiscms.service.mapper.TermMapper;
import com.myriadquest.kreiscms.service.dto.TermCriteria;
import com.myriadquest.kreiscms.service.TermQueryService;

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
    private static final Integer SMALLER_TERM = 1 - 1;

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
    private static final Integer SMALLER_NO_OF_STUDENTS = 1 - 1;

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private TermMapper termMapper;

    @Autowired
    private TermService termService;

    @Autowired
    private TermQueryService termQueryService;

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
            .noOfStudents(DEFAULT_NO_OF_STUDENTS)
            .tenantId(DEFAULT_TENANT_ID);
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
            .noOfStudents(UPDATED_NO_OF_STUDENTS)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testTerm.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].noOfStudents").value(hasItem(DEFAULT_NO_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
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
            .andExpect(jsonPath("$.noOfStudents").value(DEFAULT_NO_OF_STUDENTS))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getTermsByIdFiltering() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        Long id = term.getId();

        defaultTermShouldBeFound("id.equals=" + id);
        defaultTermShouldNotBeFound("id.notEquals=" + id);

        defaultTermShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTermShouldNotBeFound("id.greaterThan=" + id);

        defaultTermShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTermShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTermsByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term equals to DEFAULT_TERM
        defaultTermShouldBeFound("term.equals=" + DEFAULT_TERM);

        // Get all the termList where term equals to UPDATED_TERM
        defaultTermShouldNotBeFound("term.equals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term not equals to DEFAULT_TERM
        defaultTermShouldNotBeFound("term.notEquals=" + DEFAULT_TERM);

        // Get all the termList where term not equals to UPDATED_TERM
        defaultTermShouldBeFound("term.notEquals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term in DEFAULT_TERM or UPDATED_TERM
        defaultTermShouldBeFound("term.in=" + DEFAULT_TERM + "," + UPDATED_TERM);

        // Get all the termList where term equals to UPDATED_TERM
        defaultTermShouldNotBeFound("term.in=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term is not null
        defaultTermShouldBeFound("term.specified=true");

        // Get all the termList where term is null
        defaultTermShouldNotBeFound("term.specified=false");
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term is greater than or equal to DEFAULT_TERM
        defaultTermShouldBeFound("term.greaterThanOrEqual=" + DEFAULT_TERM);

        // Get all the termList where term is greater than or equal to UPDATED_TERM
        defaultTermShouldNotBeFound("term.greaterThanOrEqual=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term is less than or equal to DEFAULT_TERM
        defaultTermShouldBeFound("term.lessThanOrEqual=" + DEFAULT_TERM);

        // Get all the termList where term is less than or equal to SMALLER_TERM
        defaultTermShouldNotBeFound("term.lessThanOrEqual=" + SMALLER_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsLessThanSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term is less than DEFAULT_TERM
        defaultTermShouldNotBeFound("term.lessThan=" + DEFAULT_TERM);

        // Get all the termList where term is less than UPDATED_TERM
        defaultTermShouldBeFound("term.lessThan=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    public void getAllTermsByTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where term is greater than DEFAULT_TERM
        defaultTermShouldNotBeFound("term.greaterThan=" + DEFAULT_TERM);

        // Get all the termList where term is greater than SMALLER_TERM
        defaultTermShouldBeFound("term.greaterThan=" + SMALLER_TERM);
    }


    @Test
    @Transactional
    public void getAllTermsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title equals to DEFAULT_TITLE
        defaultTermShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the termList where title equals to UPDATED_TITLE
        defaultTermShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTermsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title not equals to DEFAULT_TITLE
        defaultTermShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the termList where title not equals to UPDATED_TITLE
        defaultTermShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTermsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultTermShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the termList where title equals to UPDATED_TITLE
        defaultTermShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTermsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title is not null
        defaultTermShouldBeFound("title.specified=true");

        // Get all the termList where title is null
        defaultTermShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllTermsByTitleContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title contains DEFAULT_TITLE
        defaultTermShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the termList where title contains UPDATED_TITLE
        defaultTermShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllTermsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where title does not contain DEFAULT_TITLE
        defaultTermShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the termList where title does not contain UPDATED_TITLE
        defaultTermShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllTermsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description equals to DEFAULT_DESCRIPTION
        defaultTermShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the termList where description equals to UPDATED_DESCRIPTION
        defaultTermShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTermsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description not equals to DEFAULT_DESCRIPTION
        defaultTermShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the termList where description not equals to UPDATED_DESCRIPTION
        defaultTermShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTermsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTermShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the termList where description equals to UPDATED_DESCRIPTION
        defaultTermShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTermsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description is not null
        defaultTermShouldBeFound("description.specified=true");

        // Get all the termList where description is null
        defaultTermShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTermsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description contains DEFAULT_DESCRIPTION
        defaultTermShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the termList where description contains UPDATED_DESCRIPTION
        defaultTermShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTermsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where description does not contain DEFAULT_DESCRIPTION
        defaultTermShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the termList where description does not contain UPDATED_DESCRIPTION
        defaultTermShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTermsByImgUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl equals to DEFAULT_IMG_URL
        defaultTermShouldBeFound("imgUrl.equals=" + DEFAULT_IMG_URL);

        // Get all the termList where imgUrl equals to UPDATED_IMG_URL
        defaultTermShouldNotBeFound("imgUrl.equals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllTermsByImgUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl not equals to DEFAULT_IMG_URL
        defaultTermShouldNotBeFound("imgUrl.notEquals=" + DEFAULT_IMG_URL);

        // Get all the termList where imgUrl not equals to UPDATED_IMG_URL
        defaultTermShouldBeFound("imgUrl.notEquals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllTermsByImgUrlIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl in DEFAULT_IMG_URL or UPDATED_IMG_URL
        defaultTermShouldBeFound("imgUrl.in=" + DEFAULT_IMG_URL + "," + UPDATED_IMG_URL);

        // Get all the termList where imgUrl equals to UPDATED_IMG_URL
        defaultTermShouldNotBeFound("imgUrl.in=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllTermsByImgUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl is not null
        defaultTermShouldBeFound("imgUrl.specified=true");

        // Get all the termList where imgUrl is null
        defaultTermShouldNotBeFound("imgUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllTermsByImgUrlContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl contains DEFAULT_IMG_URL
        defaultTermShouldBeFound("imgUrl.contains=" + DEFAULT_IMG_URL);

        // Get all the termList where imgUrl contains UPDATED_IMG_URL
        defaultTermShouldNotBeFound("imgUrl.contains=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllTermsByImgUrlNotContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where imgUrl does not contain DEFAULT_IMG_URL
        defaultTermShouldNotBeFound("imgUrl.doesNotContain=" + DEFAULT_IMG_URL);

        // Get all the termList where imgUrl does not contain UPDATED_IMG_URL
        defaultTermShouldBeFound("imgUrl.doesNotContain=" + UPDATED_IMG_URL);
    }


    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents equals to DEFAULT_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.equals=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents equals to UPDATED_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.equals=" + UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents not equals to DEFAULT_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.notEquals=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents not equals to UPDATED_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.notEquals=" + UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents in DEFAULT_NO_OF_STUDENTS or UPDATED_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.in=" + DEFAULT_NO_OF_STUDENTS + "," + UPDATED_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents equals to UPDATED_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.in=" + UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents is not null
        defaultTermShouldBeFound("noOfStudents.specified=true");

        // Get all the termList where noOfStudents is null
        defaultTermShouldNotBeFound("noOfStudents.specified=false");
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents is greater than or equal to DEFAULT_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.greaterThanOrEqual=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents is greater than or equal to UPDATED_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.greaterThanOrEqual=" + UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents is less than or equal to DEFAULT_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.lessThanOrEqual=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents is less than or equal to SMALLER_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.lessThanOrEqual=" + SMALLER_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsLessThanSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents is less than DEFAULT_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.lessThan=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents is less than UPDATED_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.lessThan=" + UPDATED_NO_OF_STUDENTS);
    }

    @Test
    @Transactional
    public void getAllTermsByNoOfStudentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where noOfStudents is greater than DEFAULT_NO_OF_STUDENTS
        defaultTermShouldNotBeFound("noOfStudents.greaterThan=" + DEFAULT_NO_OF_STUDENTS);

        // Get all the termList where noOfStudents is greater than SMALLER_NO_OF_STUDENTS
        defaultTermShouldBeFound("noOfStudents.greaterThan=" + SMALLER_NO_OF_STUDENTS);
    }


    @Test
    @Transactional
    public void getAllTermsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId equals to DEFAULT_TENANT_ID
        defaultTermShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the termList where tenantId equals to UPDATED_TENANT_ID
        defaultTermShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTermsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId not equals to DEFAULT_TENANT_ID
        defaultTermShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the termList where tenantId not equals to UPDATED_TENANT_ID
        defaultTermShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTermsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultTermShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the termList where tenantId equals to UPDATED_TENANT_ID
        defaultTermShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTermsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId is not null
        defaultTermShouldBeFound("tenantId.specified=true");

        // Get all the termList where tenantId is null
        defaultTermShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllTermsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId contains DEFAULT_TENANT_ID
        defaultTermShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the termList where tenantId contains UPDATED_TENANT_ID
        defaultTermShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllTermsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList where tenantId does not contain DEFAULT_TENANT_ID
        defaultTermShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the termList where tenantId does not contain UPDATED_TENANT_ID
        defaultTermShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllTermsByClassTimeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);
        ClassTimeTable classTimeTable = ClassTimeTableResourceIT.createEntity(em);
        em.persist(classTimeTable);
        em.flush();
        term.addClassTimeTable(classTimeTable);
        termRepository.saveAndFlush(term);
        Long classTimeTableId = classTimeTable.getId();

        // Get all the termList where classTimeTable equals to classTimeTableId
        defaultTermShouldBeFound("classTimeTableId.equals=" + classTimeTableId);

        // Get all the termList where classTimeTable equals to classTimeTableId + 1
        defaultTermShouldNotBeFound("classTimeTableId.equals=" + (classTimeTableId + 1));
    }


    @Test
    @Transactional
    public void getAllTermsByExamTimeTableIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);
        ExamTimeTable examTimeTable = ExamTimeTableResourceIT.createEntity(em);
        em.persist(examTimeTable);
        em.flush();
        term.addExamTimeTable(examTimeTable);
        termRepository.saveAndFlush(term);
        Long examTimeTableId = examTimeTable.getId();

        // Get all the termList where examTimeTable equals to examTimeTableId
        defaultTermShouldBeFound("examTimeTableId.equals=" + examTimeTableId);

        // Get all the termList where examTimeTable equals to examTimeTableId + 1
        defaultTermShouldNotBeFound("examTimeTableId.equals=" + (examTimeTableId + 1));
    }


    @Test
    @Transactional
    public void getAllTermsByClassTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);
        User classTeacher = UserResourceIT.createEntity(em);
        em.persist(classTeacher);
        em.flush();
        term.setClassTeacher(classTeacher);
        termRepository.saveAndFlush(term);
        Long classTeacherId = classTeacher.getId();

        // Get all the termList where classTeacher equals to classTeacherId
        defaultTermShouldBeFound("classTeacherId.equals=" + classTeacherId);

        // Get all the termList where classTeacher equals to classTeacherId + 1
        defaultTermShouldNotBeFound("classTeacherId.equals=" + (classTeacherId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTermShouldBeFound(String filter) throws Exception {
        restTermMockMvc.perform(get("/api/terms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(term.getId().intValue())))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].noOfStudents").value(hasItem(DEFAULT_NO_OF_STUDENTS)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restTermMockMvc.perform(get("/api/terms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTermShouldNotBeFound(String filter) throws Exception {
        restTermMockMvc.perform(get("/api/terms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTermMockMvc.perform(get("/api/terms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .noOfStudents(UPDATED_NO_OF_STUDENTS)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testTerm.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
