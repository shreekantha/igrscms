package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Gallery;
import com.myriadquest.kreiscms.domain.GalleryCat;
import com.myriadquest.kreiscms.repository.GalleryRepository;
import com.myriadquest.kreiscms.service.GalleryService;
import com.myriadquest.kreiscms.service.dto.GalleryDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryMapper;
import com.myriadquest.kreiscms.service.dto.GalleryCriteria;
import com.myriadquest.kreiscms.service.GalleryQueryService;

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
 * Integration tests for the {@link GalleryResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GalleryResourceIT {

    private static final String DEFAULT_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMG_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRITPION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRITPION = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private GalleryMapper galleryMapper;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private GalleryQueryService galleryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalleryMockMvc;

    private Gallery gallery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .imgUrl(DEFAULT_IMG_URL)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .descritpion(DEFAULT_DESCRITPION)
            .tenantId(DEFAULT_TENANT_ID);
        // Add required entity
        GalleryCat galleryCat;
        if (TestUtil.findAll(em, GalleryCat.class).isEmpty()) {
            galleryCat = GalleryCatResourceIT.createEntity(em);
            em.persist(galleryCat);
            em.flush();
        } else {
            galleryCat = TestUtil.findAll(em, GalleryCat.class).get(0);
        }
        gallery.setCategory(galleryCat);
        return gallery;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createUpdatedEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .descritpion(UPDATED_DESCRITPION)
            .tenantId(UPDATED_TENANT_ID);
        // Add required entity
        GalleryCat galleryCat;
        if (TestUtil.findAll(em, GalleryCat.class).isEmpty()) {
            galleryCat = GalleryCatResourceIT.createUpdatedEntity(em);
            em.persist(galleryCat);
            em.flush();
        } else {
            galleryCat = TestUtil.findAll(em, GalleryCat.class).get(0);
        }
        gallery.setCategory(galleryCat);
        return gallery;
    }

    @BeforeEach
    public void initTest() {
        gallery = createEntity(em);
    }

    @Test
    @Transactional
    public void createGallery() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();
        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate + 1);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testGallery.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testGallery.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testGallery.getDescritpion()).isEqualTo(DEFAULT_DESCRITPION);
        assertThat(testGallery.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createGalleryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // Create the Gallery with an existing ID
        gallery.setId(1L);
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImgUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleryRepository.findAll().size();
        // set the field null
        gallery.setImgUrl(null);

        // Create the Gallery, which fails.
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);


        restGalleryMockMvc.perform(post("/api/galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isBadRequest());

        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGalleries() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].descritpion").value(hasItem(DEFAULT_DESCRITPION)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gallery.getId().intValue()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.descritpion").value(DEFAULT_DESCRITPION))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getGalleriesByIdFiltering() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        Long id = gallery.getId();

        defaultGalleryShouldBeFound("id.equals=" + id);
        defaultGalleryShouldNotBeFound("id.notEquals=" + id);

        defaultGalleryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGalleryShouldNotBeFound("id.greaterThan=" + id);

        defaultGalleryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGalleryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGalleriesByImgUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl equals to DEFAULT_IMG_URL
        defaultGalleryShouldBeFound("imgUrl.equals=" + DEFAULT_IMG_URL);

        // Get all the galleryList where imgUrl equals to UPDATED_IMG_URL
        defaultGalleryShouldNotBeFound("imgUrl.equals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllGalleriesByImgUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl not equals to DEFAULT_IMG_URL
        defaultGalleryShouldNotBeFound("imgUrl.notEquals=" + DEFAULT_IMG_URL);

        // Get all the galleryList where imgUrl not equals to UPDATED_IMG_URL
        defaultGalleryShouldBeFound("imgUrl.notEquals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllGalleriesByImgUrlIsInShouldWork() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl in DEFAULT_IMG_URL or UPDATED_IMG_URL
        defaultGalleryShouldBeFound("imgUrl.in=" + DEFAULT_IMG_URL + "," + UPDATED_IMG_URL);

        // Get all the galleryList where imgUrl equals to UPDATED_IMG_URL
        defaultGalleryShouldNotBeFound("imgUrl.in=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllGalleriesByImgUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl is not null
        defaultGalleryShouldBeFound("imgUrl.specified=true");

        // Get all the galleryList where imgUrl is null
        defaultGalleryShouldNotBeFound("imgUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleriesByImgUrlContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl contains DEFAULT_IMG_URL
        defaultGalleryShouldBeFound("imgUrl.contains=" + DEFAULT_IMG_URL);

        // Get all the galleryList where imgUrl contains UPDATED_IMG_URL
        defaultGalleryShouldNotBeFound("imgUrl.contains=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllGalleriesByImgUrlNotContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where imgUrl does not contain DEFAULT_IMG_URL
        defaultGalleryShouldNotBeFound("imgUrl.doesNotContain=" + DEFAULT_IMG_URL);

        // Get all the galleryList where imgUrl does not contain UPDATED_IMG_URL
        defaultGalleryShouldBeFound("imgUrl.doesNotContain=" + UPDATED_IMG_URL);
    }


    @Test
    @Transactional
    public void getAllGalleriesByDescritpionIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion equals to DEFAULT_DESCRITPION
        defaultGalleryShouldBeFound("descritpion.equals=" + DEFAULT_DESCRITPION);

        // Get all the galleryList where descritpion equals to UPDATED_DESCRITPION
        defaultGalleryShouldNotBeFound("descritpion.equals=" + UPDATED_DESCRITPION);
    }

    @Test
    @Transactional
    public void getAllGalleriesByDescritpionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion not equals to DEFAULT_DESCRITPION
        defaultGalleryShouldNotBeFound("descritpion.notEquals=" + DEFAULT_DESCRITPION);

        // Get all the galleryList where descritpion not equals to UPDATED_DESCRITPION
        defaultGalleryShouldBeFound("descritpion.notEquals=" + UPDATED_DESCRITPION);
    }

    @Test
    @Transactional
    public void getAllGalleriesByDescritpionIsInShouldWork() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion in DEFAULT_DESCRITPION or UPDATED_DESCRITPION
        defaultGalleryShouldBeFound("descritpion.in=" + DEFAULT_DESCRITPION + "," + UPDATED_DESCRITPION);

        // Get all the galleryList where descritpion equals to UPDATED_DESCRITPION
        defaultGalleryShouldNotBeFound("descritpion.in=" + UPDATED_DESCRITPION);
    }

    @Test
    @Transactional
    public void getAllGalleriesByDescritpionIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion is not null
        defaultGalleryShouldBeFound("descritpion.specified=true");

        // Get all the galleryList where descritpion is null
        defaultGalleryShouldNotBeFound("descritpion.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleriesByDescritpionContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion contains DEFAULT_DESCRITPION
        defaultGalleryShouldBeFound("descritpion.contains=" + DEFAULT_DESCRITPION);

        // Get all the galleryList where descritpion contains UPDATED_DESCRITPION
        defaultGalleryShouldNotBeFound("descritpion.contains=" + UPDATED_DESCRITPION);
    }

    @Test
    @Transactional
    public void getAllGalleriesByDescritpionNotContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where descritpion does not contain DEFAULT_DESCRITPION
        defaultGalleryShouldNotBeFound("descritpion.doesNotContain=" + DEFAULT_DESCRITPION);

        // Get all the galleryList where descritpion does not contain UPDATED_DESCRITPION
        defaultGalleryShouldBeFound("descritpion.doesNotContain=" + UPDATED_DESCRITPION);
    }


    @Test
    @Transactional
    public void getAllGalleriesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId equals to DEFAULT_TENANT_ID
        defaultGalleryShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the galleryList where tenantId equals to UPDATED_TENANT_ID
        defaultGalleryShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId not equals to DEFAULT_TENANT_ID
        defaultGalleryShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the galleryList where tenantId not equals to UPDATED_TENANT_ID
        defaultGalleryShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultGalleryShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the galleryList where tenantId equals to UPDATED_TENANT_ID
        defaultGalleryShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId is not null
        defaultGalleryShouldBeFound("tenantId.specified=true");

        // Get all the galleryList where tenantId is null
        defaultGalleryShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleriesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId contains DEFAULT_TENANT_ID
        defaultGalleryShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the galleryList where tenantId contains UPDATED_TENANT_ID
        defaultGalleryShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleriesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList where tenantId does not contain DEFAULT_TENANT_ID
        defaultGalleryShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the galleryList where tenantId does not contain UPDATED_TENANT_ID
        defaultGalleryShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllGalleriesByCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        GalleryCat category = gallery.getCategory();
        galleryRepository.saveAndFlush(gallery);
        Long categoryId = category.getId();

        // Get all the galleryList where category equals to categoryId
        defaultGalleryShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the galleryList where category equals to categoryId + 1
        defaultGalleryShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGalleryShouldBeFound(String filter) throws Exception {
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].descritpion").value(hasItem(DEFAULT_DESCRITPION)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restGalleryMockMvc.perform(get("/api/galleries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGalleryShouldNotBeFound(String filter) throws Exception {
        restGalleryMockMvc.perform(get("/api/galleries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGalleryMockMvc.perform(get("/api/galleries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGallery() throws Exception {
        // Get the gallery
        restGalleryMockMvc.perform(get("/api/galleries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery
        Gallery updatedGallery = galleryRepository.findById(gallery.getId()).get();
        // Disconnect from session so that the updates on updatedGallery are not directly saved in db
        em.detach(updatedGallery);
        updatedGallery
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .descritpion(UPDATED_DESCRITPION)
            .tenantId(UPDATED_TENANT_ID);
        GalleryDTO galleryDTO = galleryMapper.toDto(updatedGallery);

        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testGallery.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testGallery.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testGallery.getDescritpion()).isEqualTo(UPDATED_DESCRITPION);
        assertThat(testGallery.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleryMockMvc.perform(put("/api/galleries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeDelete = galleryRepository.findAll().size();

        // Delete the gallery
        restGalleryMockMvc.perform(delete("/api/galleries/{id}", gallery.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
