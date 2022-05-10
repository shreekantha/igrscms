package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Testimonial;
import com.myriadquest.kreiscms.repository.TestimonialRepository;
import com.myriadquest.kreiscms.service.TestimonialService;
import com.myriadquest.kreiscms.service.dto.TestimonialDTO;
import com.myriadquest.kreiscms.service.mapper.TestimonialMapper;
import com.myriadquest.kreiscms.service.dto.TestimonialCriteria;
import com.myriadquest.kreiscms.service.TestimonialQueryService;

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
 * Integration tests for the {@link TestimonialResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TestimonialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    @Autowired
    private TestimonialService testimonialService;

    @Autowired
    private TestimonialQueryService testimonialQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTestimonialMockMvc;

    private Testimonial testimonial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testimonial createEntity(EntityManager em) {
        Testimonial testimonial = new Testimonial()
            .name(DEFAULT_NAME)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .imgLink(DEFAULT_IMG_LINK)
            .batchYear(DEFAULT_BATCH_YEAR)
            .note(DEFAULT_NOTE);
        return testimonial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testimonial createUpdatedEntity(EntityManager em) {
        Testimonial testimonial = new Testimonial()
            .name(UPDATED_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .imgLink(UPDATED_IMG_LINK)
            .batchYear(UPDATED_BATCH_YEAR)
            .note(UPDATED_NOTE);
        return testimonial;
    }

    @BeforeEach
    public void initTest() {
        testimonial = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestimonial() throws Exception {
        int databaseSizeBeforeCreate = testimonialRepository.findAll().size();
        // Create the Testimonial
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(testimonial);
        restTestimonialMockMvc.perform(post("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isCreated());

        // Validate the Testimonial in the database
        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeCreate + 1);
        Testimonial testTestimonial = testimonialList.get(testimonialList.size() - 1);
        assertThat(testTestimonial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTestimonial.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testTestimonial.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testTestimonial.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testTestimonial.getBatchYear()).isEqualTo(DEFAULT_BATCH_YEAR);
        assertThat(testTestimonial.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createTestimonialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testimonialRepository.findAll().size();

        // Create the Testimonial with an existing ID
        testimonial.setId(1L);
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(testimonial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestimonialMockMvc.perform(post("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Testimonial in the database
        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = testimonialRepository.findAll().size();
        // set the field null
        testimonial.setName(null);

        // Create the Testimonial, which fails.
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(testimonial);


        restTestimonialMockMvc.perform(post("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isBadRequest());

        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBatchYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = testimonialRepository.findAll().size();
        // set the field null
        testimonial.setBatchYear(null);

        // Create the Testimonial, which fails.
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(testimonial);


        restTestimonialMockMvc.perform(post("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isBadRequest());

        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTestimonials() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList
        restTestimonialMockMvc.perform(get("/api/testimonials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testimonial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].batchYear").value(hasItem(DEFAULT_BATCH_YEAR)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getTestimonial() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get the testimonial
        restTestimonialMockMvc.perform(get("/api/testimonials/{id}", testimonial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testimonial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.batchYear").value(DEFAULT_BATCH_YEAR))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }


    @Test
    @Transactional
    public void getTestimonialsByIdFiltering() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        Long id = testimonial.getId();

        defaultTestimonialShouldBeFound("id.equals=" + id);
        defaultTestimonialShouldNotBeFound("id.notEquals=" + id);

        defaultTestimonialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTestimonialShouldNotBeFound("id.greaterThan=" + id);

        defaultTestimonialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTestimonialShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTestimonialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name equals to DEFAULT_NAME
        defaultTestimonialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the testimonialList where name equals to UPDATED_NAME
        defaultTestimonialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name not equals to DEFAULT_NAME
        defaultTestimonialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the testimonialList where name not equals to UPDATED_NAME
        defaultTestimonialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTestimonialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the testimonialList where name equals to UPDATED_NAME
        defaultTestimonialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name is not null
        defaultTestimonialShouldBeFound("name.specified=true");

        // Get all the testimonialList where name is null
        defaultTestimonialShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTestimonialsByNameContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name contains DEFAULT_NAME
        defaultTestimonialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the testimonialList where name contains UPDATED_NAME
        defaultTestimonialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where name does not contain DEFAULT_NAME
        defaultTestimonialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the testimonialList where name does not contain UPDATED_NAME
        defaultTestimonialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTestimonialsByImgLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink equals to DEFAULT_IMG_LINK
        defaultTestimonialShouldBeFound("imgLink.equals=" + DEFAULT_IMG_LINK);

        // Get all the testimonialList where imgLink equals to UPDATED_IMG_LINK
        defaultTestimonialShouldNotBeFound("imgLink.equals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByImgLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink not equals to DEFAULT_IMG_LINK
        defaultTestimonialShouldNotBeFound("imgLink.notEquals=" + DEFAULT_IMG_LINK);

        // Get all the testimonialList where imgLink not equals to UPDATED_IMG_LINK
        defaultTestimonialShouldBeFound("imgLink.notEquals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByImgLinkIsInShouldWork() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink in DEFAULT_IMG_LINK or UPDATED_IMG_LINK
        defaultTestimonialShouldBeFound("imgLink.in=" + DEFAULT_IMG_LINK + "," + UPDATED_IMG_LINK);

        // Get all the testimonialList where imgLink equals to UPDATED_IMG_LINK
        defaultTestimonialShouldNotBeFound("imgLink.in=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByImgLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink is not null
        defaultTestimonialShouldBeFound("imgLink.specified=true");

        // Get all the testimonialList where imgLink is null
        defaultTestimonialShouldNotBeFound("imgLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllTestimonialsByImgLinkContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink contains DEFAULT_IMG_LINK
        defaultTestimonialShouldBeFound("imgLink.contains=" + DEFAULT_IMG_LINK);

        // Get all the testimonialList where imgLink contains UPDATED_IMG_LINK
        defaultTestimonialShouldNotBeFound("imgLink.contains=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByImgLinkNotContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where imgLink does not contain DEFAULT_IMG_LINK
        defaultTestimonialShouldNotBeFound("imgLink.doesNotContain=" + DEFAULT_IMG_LINK);

        // Get all the testimonialList where imgLink does not contain UPDATED_IMG_LINK
        defaultTestimonialShouldBeFound("imgLink.doesNotContain=" + UPDATED_IMG_LINK);
    }


    @Test
    @Transactional
    public void getAllTestimonialsByBatchYearIsEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear equals to DEFAULT_BATCH_YEAR
        defaultTestimonialShouldBeFound("batchYear.equals=" + DEFAULT_BATCH_YEAR);

        // Get all the testimonialList where batchYear equals to UPDATED_BATCH_YEAR
        defaultTestimonialShouldNotBeFound("batchYear.equals=" + UPDATED_BATCH_YEAR);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByBatchYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear not equals to DEFAULT_BATCH_YEAR
        defaultTestimonialShouldNotBeFound("batchYear.notEquals=" + DEFAULT_BATCH_YEAR);

        // Get all the testimonialList where batchYear not equals to UPDATED_BATCH_YEAR
        defaultTestimonialShouldBeFound("batchYear.notEquals=" + UPDATED_BATCH_YEAR);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByBatchYearIsInShouldWork() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear in DEFAULT_BATCH_YEAR or UPDATED_BATCH_YEAR
        defaultTestimonialShouldBeFound("batchYear.in=" + DEFAULT_BATCH_YEAR + "," + UPDATED_BATCH_YEAR);

        // Get all the testimonialList where batchYear equals to UPDATED_BATCH_YEAR
        defaultTestimonialShouldNotBeFound("batchYear.in=" + UPDATED_BATCH_YEAR);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByBatchYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear is not null
        defaultTestimonialShouldBeFound("batchYear.specified=true");

        // Get all the testimonialList where batchYear is null
        defaultTestimonialShouldNotBeFound("batchYear.specified=false");
    }
                @Test
    @Transactional
    public void getAllTestimonialsByBatchYearContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear contains DEFAULT_BATCH_YEAR
        defaultTestimonialShouldBeFound("batchYear.contains=" + DEFAULT_BATCH_YEAR);

        // Get all the testimonialList where batchYear contains UPDATED_BATCH_YEAR
        defaultTestimonialShouldNotBeFound("batchYear.contains=" + UPDATED_BATCH_YEAR);
    }

    @Test
    @Transactional
    public void getAllTestimonialsByBatchYearNotContainsSomething() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        // Get all the testimonialList where batchYear does not contain DEFAULT_BATCH_YEAR
        defaultTestimonialShouldNotBeFound("batchYear.doesNotContain=" + DEFAULT_BATCH_YEAR);

        // Get all the testimonialList where batchYear does not contain UPDATED_BATCH_YEAR
        defaultTestimonialShouldBeFound("batchYear.doesNotContain=" + UPDATED_BATCH_YEAR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTestimonialShouldBeFound(String filter) throws Exception {
        restTestimonialMockMvc.perform(get("/api/testimonials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testimonial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].batchYear").value(hasItem(DEFAULT_BATCH_YEAR)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restTestimonialMockMvc.perform(get("/api/testimonials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTestimonialShouldNotBeFound(String filter) throws Exception {
        restTestimonialMockMvc.perform(get("/api/testimonials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTestimonialMockMvc.perform(get("/api/testimonials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTestimonial() throws Exception {
        // Get the testimonial
        restTestimonialMockMvc.perform(get("/api/testimonials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestimonial() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        int databaseSizeBeforeUpdate = testimonialRepository.findAll().size();

        // Update the testimonial
        Testimonial updatedTestimonial = testimonialRepository.findById(testimonial.getId()).get();
        // Disconnect from session so that the updates on updatedTestimonial are not directly saved in db
        em.detach(updatedTestimonial);
        updatedTestimonial
            .name(UPDATED_NAME)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .imgLink(UPDATED_IMG_LINK)
            .batchYear(UPDATED_BATCH_YEAR)
            .note(UPDATED_NOTE);
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(updatedTestimonial);

        restTestimonialMockMvc.perform(put("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isOk());

        // Validate the Testimonial in the database
        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeUpdate);
        Testimonial testTestimonial = testimonialList.get(testimonialList.size() - 1);
        assertThat(testTestimonial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTestimonial.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testTestimonial.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testTestimonial.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testTestimonial.getBatchYear()).isEqualTo(UPDATED_BATCH_YEAR);
        assertThat(testTestimonial.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTestimonial() throws Exception {
        int databaseSizeBeforeUpdate = testimonialRepository.findAll().size();

        // Create the Testimonial
        TestimonialDTO testimonialDTO = testimonialMapper.toDto(testimonial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestimonialMockMvc.perform(put("/api/testimonials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(testimonialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Testimonial in the database
        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTestimonial() throws Exception {
        // Initialize the database
        testimonialRepository.saveAndFlush(testimonial);

        int databaseSizeBeforeDelete = testimonialRepository.findAll().size();

        // Delete the testimonial
        restTestimonialMockMvc.perform(delete("/api/testimonials/{id}", testimonial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Testimonial> testimonialList = testimonialRepository.findAll();
        assertThat(testimonialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
