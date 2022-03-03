package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.AboutUs;
import com.myriadquest.kreiscms.repository.AboutUsRepository;
import com.myriadquest.kreiscms.service.AboutUsService;
import com.myriadquest.kreiscms.service.dto.AboutUsDTO;
import com.myriadquest.kreiscms.service.mapper.AboutUsMapper;

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
 * Integration tests for the {@link AboutUsResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AboutUsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    @Autowired
    private AboutUsRepository aboutUsRepository;

    @Autowired
    private AboutUsMapper aboutUsMapper;

    @Autowired
    private AboutUsService aboutUsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAboutUsMockMvc;

    private AboutUs aboutUs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AboutUs createEntity(EntityManager em) {
        AboutUs aboutUs = new AboutUs()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return aboutUs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AboutUs createUpdatedEntity(EntityManager em) {
        AboutUs aboutUs = new AboutUs()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return aboutUs;
    }

    @BeforeEach
    public void initTest() {
        aboutUs = createEntity(em);
    }

    @Test
    @Transactional
    public void createAboutUs() throws Exception {
        int databaseSizeBeforeCreate = aboutUsRepository.findAll().size();
        // Create the AboutUs
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(aboutUs);
        restAboutUsMockMvc.perform(post("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isCreated());

        // Validate the AboutUs in the database
        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeCreate + 1);
        AboutUs testAboutUs = aboutUsList.get(aboutUsList.size() - 1);
        assertThat(testAboutUs.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAboutUs.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAboutUs.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testAboutUs.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testAboutUs.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAboutUsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aboutUsRepository.findAll().size();

        // Create the AboutUs with an existing ID
        aboutUs.setId(1L);
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(aboutUs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAboutUsMockMvc.perform(post("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AboutUs in the database
        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = aboutUsRepository.findAll().size();
        // set the field null
        aboutUs.setTitle(null);

        // Create the AboutUs, which fails.
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(aboutUs);


        restAboutUsMockMvc.perform(post("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isBadRequest());

        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = aboutUsRepository.findAll().size();
        // set the field null
        aboutUs.setDescription(null);

        // Create the AboutUs, which fails.
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(aboutUs);


        restAboutUsMockMvc.perform(post("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isBadRequest());

        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAboutuses() throws Exception {
        // Initialize the database
        aboutUsRepository.saveAndFlush(aboutUs);

        // Get all the aboutUsList
        restAboutUsMockMvc.perform(get("/api/aboutuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aboutUs.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }
    
    @Test
    @Transactional
    public void getAboutUs() throws Exception {
        // Initialize the database
        aboutUsRepository.saveAndFlush(aboutUs);

        // Get the aboutUs
        restAboutUsMockMvc.perform(get("/api/aboutuses/{id}", aboutUs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aboutUs.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }
    @Test
    @Transactional
    public void getNonExistingAboutUs() throws Exception {
        // Get the aboutUs
        restAboutUsMockMvc.perform(get("/api/aboutuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAboutUs() throws Exception {
        // Initialize the database
        aboutUsRepository.saveAndFlush(aboutUs);

        int databaseSizeBeforeUpdate = aboutUsRepository.findAll().size();

        // Update the aboutUs
        AboutUs updatedAboutUs = aboutUsRepository.findById(aboutUs.getId()).get();
        // Disconnect from session so that the updates on updatedAboutUs are not directly saved in db
        em.detach(updatedAboutUs);
        updatedAboutUs
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(updatedAboutUs);

        restAboutUsMockMvc.perform(put("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isOk());

        // Validate the AboutUs in the database
        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeUpdate);
        AboutUs testAboutUs = aboutUsList.get(aboutUsList.size() - 1);
        assertThat(testAboutUs.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAboutUs.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAboutUs.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testAboutUs.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testAboutUs.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAboutUs() throws Exception {
        int databaseSizeBeforeUpdate = aboutUsRepository.findAll().size();

        // Create the AboutUs
        AboutUsDTO aboutUsDTO = aboutUsMapper.toDto(aboutUs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAboutUsMockMvc.perform(put("/api/aboutuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aboutUsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AboutUs in the database
        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAboutUs() throws Exception {
        // Initialize the database
        aboutUsRepository.saveAndFlush(aboutUs);

        int databaseSizeBeforeDelete = aboutUsRepository.findAll().size();

        // Delete the aboutUs
        restAboutUsMockMvc.perform(delete("/api/aboutuses/{id}", aboutUs.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AboutUs> aboutUsList = aboutUsRepository.findAll();
        assertThat(aboutUsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
