package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.HomeImg;
import com.myriadquest.kreiscms.repository.HomeImgRepository;
import com.myriadquest.kreiscms.service.HomeImgService;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;
import com.myriadquest.kreiscms.service.mapper.HomeImgMapper;
import com.myriadquest.kreiscms.service.dto.HomeImgCriteria;
import com.myriadquest.kreiscms.service.HomeImgQueryService;

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
 * Integration tests for the {@link HomeImgResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class HomeImgResourceIT {

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private HomeImgRepository homeImgRepository;

    @Autowired
    private HomeImgMapper homeImgMapper;

    @Autowired
    private HomeImgService homeImgService;

    @Autowired
    private HomeImgQueryService homeImgQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHomeImgMockMvc;

    private HomeImg homeImg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HomeImg createEntity(EntityManager em) {
        HomeImg homeImg = new HomeImg()
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .tenantId(DEFAULT_TENANT_ID);
        return homeImg;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HomeImg createUpdatedEntity(EntityManager em) {
        HomeImg homeImg = new HomeImg()
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .tenantId(UPDATED_TENANT_ID);
        return homeImg;
    }

    @BeforeEach
    public void initTest() {
        homeImg = createEntity(em);
    }

    @Test
    @Transactional
    public void createHomeImg() throws Exception {
        int databaseSizeBeforeCreate = homeImgRepository.findAll().size();
        // Create the HomeImg
        HomeImgDTO homeImgDTO = homeImgMapper.toDto(homeImg);
        restHomeImgMockMvc.perform(post("/api/home-imgs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homeImgDTO)))
            .andExpect(status().isCreated());

        // Validate the HomeImg in the database
        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeCreate + 1);
        HomeImg testHomeImg = homeImgList.get(homeImgList.size() - 1);
        assertThat(testHomeImg.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testHomeImg.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testHomeImg.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testHomeImg.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHomeImg.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createHomeImgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = homeImgRepository.findAll().size();

        // Create the HomeImg with an existing ID
        homeImg.setId(1L);
        HomeImgDTO homeImgDTO = homeImgMapper.toDto(homeImg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHomeImgMockMvc.perform(post("/api/home-imgs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homeImgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HomeImg in the database
        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = homeImgRepository.findAll().size();
        // set the field null
        homeImg.setTitle(null);

        // Create the HomeImg, which fails.
        HomeImgDTO homeImgDTO = homeImgMapper.toDto(homeImg);


        restHomeImgMockMvc.perform(post("/api/home-imgs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homeImgDTO)))
            .andExpect(status().isBadRequest());

        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHomeImgs() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList
        restHomeImgMockMvc.perform(get("/api/home-imgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(homeImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getHomeImg() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get the homeImg
        restHomeImgMockMvc.perform(get("/api/home-imgs/{id}", homeImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(homeImg.getId().intValue()))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getHomeImgsByIdFiltering() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        Long id = homeImg.getId();

        defaultHomeImgShouldBeFound("id.equals=" + id);
        defaultHomeImgShouldNotBeFound("id.notEquals=" + id);

        defaultHomeImgShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHomeImgShouldNotBeFound("id.greaterThan=" + id);

        defaultHomeImgShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHomeImgShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHomeImgsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title equals to DEFAULT_TITLE
        defaultHomeImgShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the homeImgList where title equals to UPDATED_TITLE
        defaultHomeImgShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title not equals to DEFAULT_TITLE
        defaultHomeImgShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the homeImgList where title not equals to UPDATED_TITLE
        defaultHomeImgShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultHomeImgShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the homeImgList where title equals to UPDATED_TITLE
        defaultHomeImgShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title is not null
        defaultHomeImgShouldBeFound("title.specified=true");

        // Get all the homeImgList where title is null
        defaultHomeImgShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllHomeImgsByTitleContainsSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title contains DEFAULT_TITLE
        defaultHomeImgShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the homeImgList where title contains UPDATED_TITLE
        defaultHomeImgShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where title does not contain DEFAULT_TITLE
        defaultHomeImgShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the homeImgList where title does not contain UPDATED_TITLE
        defaultHomeImgShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllHomeImgsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId equals to DEFAULT_TENANT_ID
        defaultHomeImgShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the homeImgList where tenantId equals to UPDATED_TENANT_ID
        defaultHomeImgShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId not equals to DEFAULT_TENANT_ID
        defaultHomeImgShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the homeImgList where tenantId not equals to UPDATED_TENANT_ID
        defaultHomeImgShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultHomeImgShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the homeImgList where tenantId equals to UPDATED_TENANT_ID
        defaultHomeImgShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId is not null
        defaultHomeImgShouldBeFound("tenantId.specified=true");

        // Get all the homeImgList where tenantId is null
        defaultHomeImgShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllHomeImgsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId contains DEFAULT_TENANT_ID
        defaultHomeImgShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the homeImgList where tenantId contains UPDATED_TENANT_ID
        defaultHomeImgShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllHomeImgsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        // Get all the homeImgList where tenantId does not contain DEFAULT_TENANT_ID
        defaultHomeImgShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the homeImgList where tenantId does not contain UPDATED_TENANT_ID
        defaultHomeImgShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHomeImgShouldBeFound(String filter) throws Exception {
        restHomeImgMockMvc.perform(get("/api/home-imgs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(homeImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restHomeImgMockMvc.perform(get("/api/home-imgs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHomeImgShouldNotBeFound(String filter) throws Exception {
        restHomeImgMockMvc.perform(get("/api/home-imgs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHomeImgMockMvc.perform(get("/api/home-imgs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingHomeImg() throws Exception {
        // Get the homeImg
        restHomeImgMockMvc.perform(get("/api/home-imgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHomeImg() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        int databaseSizeBeforeUpdate = homeImgRepository.findAll().size();

        // Update the homeImg
        HomeImg updatedHomeImg = homeImgRepository.findById(homeImg.getId()).get();
        // Disconnect from session so that the updates on updatedHomeImg are not directly saved in db
        em.detach(updatedHomeImg);
        updatedHomeImg
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .tenantId(UPDATED_TENANT_ID);
        HomeImgDTO homeImgDTO = homeImgMapper.toDto(updatedHomeImg);

        restHomeImgMockMvc.perform(put("/api/home-imgs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homeImgDTO)))
            .andExpect(status().isOk());

        // Validate the HomeImg in the database
        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeUpdate);
        HomeImg testHomeImg = homeImgList.get(homeImgList.size() - 1);
        assertThat(testHomeImg.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testHomeImg.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testHomeImg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHomeImg.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHomeImg.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingHomeImg() throws Exception {
        int databaseSizeBeforeUpdate = homeImgRepository.findAll().size();

        // Create the HomeImg
        HomeImgDTO homeImgDTO = homeImgMapper.toDto(homeImg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHomeImgMockMvc.perform(put("/api/home-imgs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(homeImgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HomeImg in the database
        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHomeImg() throws Exception {
        // Initialize the database
        homeImgRepository.saveAndFlush(homeImg);

        int databaseSizeBeforeDelete = homeImgRepository.findAll().size();

        // Delete the homeImg
        restHomeImgMockMvc.perform(delete("/api/home-imgs/{id}", homeImg.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HomeImg> homeImgList = homeImgRepository.findAll();
        assertThat(homeImgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
