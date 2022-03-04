package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.HomeImg;
import com.myriadquest.kreiscms.repository.HomeImgRepository;
import com.myriadquest.kreiscms.service.HomeImgService;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;
import com.myriadquest.kreiscms.service.mapper.HomeImgMapper;

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

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private HomeImgRepository homeImgRepository;

    @Autowired
    private HomeImgMapper homeImgMapper;

    @Autowired
    private HomeImgService homeImgService;

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
            .active(DEFAULT_ACTIVE);
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
            .active(UPDATED_ACTIVE);
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
        assertThat(testHomeImg.isActive()).isEqualTo(DEFAULT_ACTIVE);
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
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = homeImgRepository.findAll().size();
        // set the field null
        homeImg.setActive(null);

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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
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
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
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
            .active(UPDATED_ACTIVE);
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
        assertThat(testHomeImg.isActive()).isEqualTo(UPDATED_ACTIVE);
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
