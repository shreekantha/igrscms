package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.GalleryCat;
import com.myriadquest.kreiscms.repository.GalleryCatRepository;
import com.myriadquest.kreiscms.service.GalleryCatService;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryCatMapper;

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
 * Integration tests for the {@link GalleryCatResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GalleryCatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    @Autowired
    private GalleryCatRepository galleryCatRepository;

    @Autowired
    private GalleryCatMapper galleryCatMapper;

    @Autowired
    private GalleryCatService galleryCatService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalleryCatMockMvc;

    private GalleryCat galleryCat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GalleryCat createEntity(EntityManager em) {
        GalleryCat galleryCat = new GalleryCat()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE);
        return galleryCat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GalleryCat createUpdatedEntity(EntityManager em) {
        GalleryCat galleryCat = new GalleryCat()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        return galleryCat;
    }

    @BeforeEach
    public void initTest() {
        galleryCat = createEntity(em);
    }

    @Test
    @Transactional
    public void createGalleryCat() throws Exception {
        int databaseSizeBeforeCreate = galleryCatRepository.findAll().size();
        // Create the GalleryCat
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(galleryCat);
        restGalleryCatMockMvc.perform(post("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isCreated());

        // Validate the GalleryCat in the database
        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeCreate + 1);
        GalleryCat testGalleryCat = galleryCatList.get(galleryCatList.size() - 1);
        assertThat(testGalleryCat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGalleryCat.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGalleryCat.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testGalleryCat.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testGalleryCat.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createGalleryCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galleryCatRepository.findAll().size();

        // Create the GalleryCat with an existing ID
        galleryCat.setId(1L);
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(galleryCat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryCatMockMvc.perform(post("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GalleryCat in the database
        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleryCatRepository.findAll().size();
        // set the field null
        galleryCat.setName(null);

        // Create the GalleryCat, which fails.
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(galleryCat);


        restGalleryCatMockMvc.perform(post("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isBadRequest());

        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleryCatRepository.findAll().size();
        // set the field null
        galleryCat.setImgLink(null);

        // Create the GalleryCat, which fails.
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(galleryCat);


        restGalleryCatMockMvc.perform(post("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isBadRequest());

        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGalleryCats() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList
        restGalleryCatMockMvc.perform(get("/api/gallery-cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galleryCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))));
    }
    
    @Test
    @Transactional
    public void getGalleryCat() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get the galleryCat
        restGalleryCatMockMvc.perform(get("/api/gallery-cats/{id}", galleryCat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galleryCat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)));
    }
    @Test
    @Transactional
    public void getNonExistingGalleryCat() throws Exception {
        // Get the galleryCat
        restGalleryCatMockMvc.perform(get("/api/gallery-cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGalleryCat() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        int databaseSizeBeforeUpdate = galleryCatRepository.findAll().size();

        // Update the galleryCat
        GalleryCat updatedGalleryCat = galleryCatRepository.findById(galleryCat.getId()).get();
        // Disconnect from session so that the updates on updatedGalleryCat are not directly saved in db
        em.detach(updatedGalleryCat);
        updatedGalleryCat
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE);
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(updatedGalleryCat);

        restGalleryCatMockMvc.perform(put("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isOk());

        // Validate the GalleryCat in the database
        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeUpdate);
        GalleryCat testGalleryCat = galleryCatList.get(galleryCatList.size() - 1);
        assertThat(testGalleryCat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGalleryCat.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGalleryCat.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testGalleryCat.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testGalleryCat.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGalleryCat() throws Exception {
        int databaseSizeBeforeUpdate = galleryCatRepository.findAll().size();

        // Create the GalleryCat
        GalleryCatDTO galleryCatDTO = galleryCatMapper.toDto(galleryCat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleryCatMockMvc.perform(put("/api/gallery-cats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryCatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GalleryCat in the database
        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGalleryCat() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        int databaseSizeBeforeDelete = galleryCatRepository.findAll().size();

        // Delete the galleryCat
        restGalleryCatMockMvc.perform(delete("/api/gallery-cats/{id}", galleryCat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GalleryCat> galleryCatList = galleryCatRepository.findAll();
        assertThat(galleryCatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
