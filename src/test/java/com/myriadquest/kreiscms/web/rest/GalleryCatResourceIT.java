package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.GalleryCat;
import com.myriadquest.kreiscms.domain.Gallery;
import com.myriadquest.kreiscms.repository.GalleryCatRepository;
import com.myriadquest.kreiscms.service.GalleryCatService;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;
import com.myriadquest.kreiscms.service.mapper.GalleryCatMapper;
import com.myriadquest.kreiscms.service.dto.GalleryCatCriteria;
import com.myriadquest.kreiscms.service.GalleryCatQueryService;

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

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private GalleryCatRepository galleryCatRepository;

    @Autowired
    private GalleryCatMapper galleryCatMapper;

    @Autowired
    private GalleryCatService galleryCatService;

    @Autowired
    private GalleryCatQueryService galleryCatQueryService;

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
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .tenantId(DEFAULT_TENANT_ID);
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
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testGalleryCat.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
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
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getGalleryCatsByIdFiltering() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        Long id = galleryCat.getId();

        defaultGalleryCatShouldBeFound("id.equals=" + id);
        defaultGalleryCatShouldNotBeFound("id.notEquals=" + id);

        defaultGalleryCatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGalleryCatShouldNotBeFound("id.greaterThan=" + id);

        defaultGalleryCatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGalleryCatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGalleryCatsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name equals to DEFAULT_NAME
        defaultGalleryCatShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the galleryCatList where name equals to UPDATED_NAME
        defaultGalleryCatShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name not equals to DEFAULT_NAME
        defaultGalleryCatShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the galleryCatList where name not equals to UPDATED_NAME
        defaultGalleryCatShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGalleryCatShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the galleryCatList where name equals to UPDATED_NAME
        defaultGalleryCatShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name is not null
        defaultGalleryCatShouldBeFound("name.specified=true");

        // Get all the galleryCatList where name is null
        defaultGalleryCatShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleryCatsByNameContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name contains DEFAULT_NAME
        defaultGalleryCatShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the galleryCatList where name contains UPDATED_NAME
        defaultGalleryCatShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where name does not contain DEFAULT_NAME
        defaultGalleryCatShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the galleryCatList where name does not contain UPDATED_NAME
        defaultGalleryCatShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description equals to DEFAULT_DESCRIPTION
        defaultGalleryCatShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the galleryCatList where description equals to UPDATED_DESCRIPTION
        defaultGalleryCatShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description not equals to DEFAULT_DESCRIPTION
        defaultGalleryCatShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the galleryCatList where description not equals to UPDATED_DESCRIPTION
        defaultGalleryCatShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGalleryCatShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the galleryCatList where description equals to UPDATED_DESCRIPTION
        defaultGalleryCatShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description is not null
        defaultGalleryCatShouldBeFound("description.specified=true");

        // Get all the galleryCatList where description is null
        defaultGalleryCatShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description contains DEFAULT_DESCRIPTION
        defaultGalleryCatShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the galleryCatList where description contains UPDATED_DESCRIPTION
        defaultGalleryCatShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where description does not contain DEFAULT_DESCRIPTION
        defaultGalleryCatShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the galleryCatList where description does not contain UPDATED_DESCRIPTION
        defaultGalleryCatShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink equals to DEFAULT_IMG_LINK
        defaultGalleryCatShouldBeFound("imgLink.equals=" + DEFAULT_IMG_LINK);

        // Get all the galleryCatList where imgLink equals to UPDATED_IMG_LINK
        defaultGalleryCatShouldNotBeFound("imgLink.equals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink not equals to DEFAULT_IMG_LINK
        defaultGalleryCatShouldNotBeFound("imgLink.notEquals=" + DEFAULT_IMG_LINK);

        // Get all the galleryCatList where imgLink not equals to UPDATED_IMG_LINK
        defaultGalleryCatShouldBeFound("imgLink.notEquals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkIsInShouldWork() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink in DEFAULT_IMG_LINK or UPDATED_IMG_LINK
        defaultGalleryCatShouldBeFound("imgLink.in=" + DEFAULT_IMG_LINK + "," + UPDATED_IMG_LINK);

        // Get all the galleryCatList where imgLink equals to UPDATED_IMG_LINK
        defaultGalleryCatShouldNotBeFound("imgLink.in=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink is not null
        defaultGalleryCatShouldBeFound("imgLink.specified=true");

        // Get all the galleryCatList where imgLink is null
        defaultGalleryCatShouldNotBeFound("imgLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink contains DEFAULT_IMG_LINK
        defaultGalleryCatShouldBeFound("imgLink.contains=" + DEFAULT_IMG_LINK);

        // Get all the galleryCatList where imgLink contains UPDATED_IMG_LINK
        defaultGalleryCatShouldNotBeFound("imgLink.contains=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByImgLinkNotContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where imgLink does not contain DEFAULT_IMG_LINK
        defaultGalleryCatShouldNotBeFound("imgLink.doesNotContain=" + DEFAULT_IMG_LINK);

        // Get all the galleryCatList where imgLink does not contain UPDATED_IMG_LINK
        defaultGalleryCatShouldBeFound("imgLink.doesNotContain=" + UPDATED_IMG_LINK);
    }


    @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId equals to DEFAULT_TENANT_ID
        defaultGalleryCatShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the galleryCatList where tenantId equals to UPDATED_TENANT_ID
        defaultGalleryCatShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId not equals to DEFAULT_TENANT_ID
        defaultGalleryCatShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the galleryCatList where tenantId not equals to UPDATED_TENANT_ID
        defaultGalleryCatShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultGalleryCatShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the galleryCatList where tenantId equals to UPDATED_TENANT_ID
        defaultGalleryCatShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId is not null
        defaultGalleryCatShouldBeFound("tenantId.specified=true");

        // Get all the galleryCatList where tenantId is null
        defaultGalleryCatShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId contains DEFAULT_TENANT_ID
        defaultGalleryCatShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the galleryCatList where tenantId contains UPDATED_TENANT_ID
        defaultGalleryCatShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllGalleryCatsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);

        // Get all the galleryCatList where tenantId does not contain DEFAULT_TENANT_ID
        defaultGalleryCatShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the galleryCatList where tenantId does not contain UPDATED_TENANT_ID
        defaultGalleryCatShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }


    @Test
    @Transactional
    public void getAllGalleryCatsByGalleryIsEqualToSomething() throws Exception {
        // Initialize the database
        galleryCatRepository.saveAndFlush(galleryCat);
        Gallery gallery = GalleryResourceIT.createEntity(em);
        em.persist(gallery);
        em.flush();
        galleryCat.addGallery(gallery);
        galleryCatRepository.saveAndFlush(galleryCat);
        Long galleryId = gallery.getId();

        // Get all the galleryCatList where gallery equals to galleryId
        defaultGalleryCatShouldBeFound("galleryId.equals=" + galleryId);

        // Get all the galleryCatList where gallery equals to galleryId + 1
        defaultGalleryCatShouldNotBeFound("galleryId.equals=" + (galleryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGalleryCatShouldBeFound(String filter) throws Exception {
        restGalleryCatMockMvc.perform(get("/api/gallery-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galleryCat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restGalleryCatMockMvc.perform(get("/api/gallery-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGalleryCatShouldNotBeFound(String filter) throws Exception {
        restGalleryCatMockMvc.perform(get("/api/gallery-cats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGalleryCatMockMvc.perform(get("/api/gallery-cats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testGalleryCat.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
