package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.News;
import com.myriadquest.kreiscms.repository.NewsRepository;
import com.myriadquest.kreiscms.service.NewsService;
import com.myriadquest.kreiscms.service.dto.NewsDTO;
import com.myriadquest.kreiscms.service.mapper.NewsMapper;
import com.myriadquest.kreiscms.service.dto.NewsCriteria;
import com.myriadquest.kreiscms.service.NewsQueryService;

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
 * Integration tests for the {@link NewsResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NewsResourceIT {

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

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsQueryService newsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsMockMvc;

    private News news;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createEntity(EntityManager em) {
        News news = new News()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .imgUrl(DEFAULT_IMG_URL)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .tenantId(DEFAULT_TENANT_ID);
        return news;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static News createUpdatedEntity(EntityManager em) {
        News news = new News()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        return news;
    }

    @BeforeEach
    public void initTest() {
        news = createEntity(em);
    }

    @Test
    @Transactional
    public void createNews() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();
        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);
        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isCreated());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate + 1);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNews.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNews.getImgUrl()).isEqualTo(DEFAULT_IMG_URL);
        assertThat(testNews.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testNews.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testNews.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = newsRepository.findAll().size();

        // Create the News with an existing ID
        news.setId(1L);
        NewsDTO newsDTO = newsMapper.toDto(news);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setTitle(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);


        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsRepository.findAll().size();
        // set the field null
        news.setImgUrl(null);

        // Create the News, which fails.
        NewsDTO newsDTO = newsMapper.toDto(news);


        restNewsMockMvc.perform(post("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList
        restNewsMockMvc.perform(get("/api/news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", news.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(news.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imgUrl").value(DEFAULT_IMG_URL))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getNewsByIdFiltering() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        Long id = news.getId();

        defaultNewsShouldBeFound("id.equals=" + id);
        defaultNewsShouldNotBeFound("id.notEquals=" + id);

        defaultNewsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNewsShouldNotBeFound("id.greaterThan=" + id);

        defaultNewsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNewsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNewsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title equals to DEFAULT_TITLE
        defaultNewsShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title not equals to DEFAULT_TITLE
        defaultNewsShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the newsList where title not equals to UPDATED_TITLE
        defaultNewsShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNewsShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the newsList where title equals to UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title is not null
        defaultNewsShouldBeFound("title.specified=true");

        // Get all the newsList where title is null
        defaultNewsShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByTitleContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title contains DEFAULT_TITLE
        defaultNewsShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the newsList where title contains UPDATED_TITLE
        defaultNewsShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNewsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where title does not contain DEFAULT_TITLE
        defaultNewsShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the newsList where title does not contain UPDATED_TITLE
        defaultNewsShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNewsByImgUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl equals to DEFAULT_IMG_URL
        defaultNewsShouldBeFound("imgUrl.equals=" + DEFAULT_IMG_URL);

        // Get all the newsList where imgUrl equals to UPDATED_IMG_URL
        defaultNewsShouldNotBeFound("imgUrl.equals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImgUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl not equals to DEFAULT_IMG_URL
        defaultNewsShouldNotBeFound("imgUrl.notEquals=" + DEFAULT_IMG_URL);

        // Get all the newsList where imgUrl not equals to UPDATED_IMG_URL
        defaultNewsShouldBeFound("imgUrl.notEquals=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImgUrlIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl in DEFAULT_IMG_URL or UPDATED_IMG_URL
        defaultNewsShouldBeFound("imgUrl.in=" + DEFAULT_IMG_URL + "," + UPDATED_IMG_URL);

        // Get all the newsList where imgUrl equals to UPDATED_IMG_URL
        defaultNewsShouldNotBeFound("imgUrl.in=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImgUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl is not null
        defaultNewsShouldBeFound("imgUrl.specified=true");

        // Get all the newsList where imgUrl is null
        defaultNewsShouldNotBeFound("imgUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByImgUrlContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl contains DEFAULT_IMG_URL
        defaultNewsShouldBeFound("imgUrl.contains=" + DEFAULT_IMG_URL);

        // Get all the newsList where imgUrl contains UPDATED_IMG_URL
        defaultNewsShouldNotBeFound("imgUrl.contains=" + UPDATED_IMG_URL);
    }

    @Test
    @Transactional
    public void getAllNewsByImgUrlNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where imgUrl does not contain DEFAULT_IMG_URL
        defaultNewsShouldNotBeFound("imgUrl.doesNotContain=" + DEFAULT_IMG_URL);

        // Get all the newsList where imgUrl does not contain UPDATED_IMG_URL
        defaultNewsShouldBeFound("imgUrl.doesNotContain=" + UPDATED_IMG_URL);
    }


    @Test
    @Transactional
    public void getAllNewsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId equals to DEFAULT_TENANT_ID
        defaultNewsShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the newsList where tenantId equals to UPDATED_TENANT_ID
        defaultNewsShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNewsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId not equals to DEFAULT_TENANT_ID
        defaultNewsShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the newsList where tenantId not equals to UPDATED_TENANT_ID
        defaultNewsShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNewsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultNewsShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the newsList where tenantId equals to UPDATED_TENANT_ID
        defaultNewsShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNewsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId is not null
        defaultNewsShouldBeFound("tenantId.specified=true");

        // Get all the newsList where tenantId is null
        defaultNewsShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllNewsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId contains DEFAULT_TENANT_ID
        defaultNewsShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the newsList where tenantId contains UPDATED_TENANT_ID
        defaultNewsShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllNewsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        // Get all the newsList where tenantId does not contain DEFAULT_TENANT_ID
        defaultNewsShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the newsList where tenantId does not contain UPDATED_TENANT_ID
        defaultNewsShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNewsShouldBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(news.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imgUrl").value(hasItem(DEFAULT_IMG_URL)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restNewsMockMvc.perform(get("/api/news/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNewsShouldNotBeFound(String filter) throws Exception {
        restNewsMockMvc.perform(get("/api/news?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNewsMockMvc.perform(get("/api/news/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNews() throws Exception {
        // Get the news
        restNewsMockMvc.perform(get("/api/news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Update the news
        News updatedNews = newsRepository.findById(news.getId()).get();
        // Disconnect from session so that the updates on updatedNews are not directly saved in db
        em.detach(updatedNews);
        updatedNews
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .imgUrl(UPDATED_IMG_URL)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .tenantId(UPDATED_TENANT_ID);
        NewsDTO newsDTO = newsMapper.toDto(updatedNews);

        restNewsMockMvc.perform(put("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isOk());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
        News testNews = newsList.get(newsList.size() - 1);
        assertThat(testNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNews.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNews.getImgUrl()).isEqualTo(UPDATED_IMG_URL);
        assertThat(testNews.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testNews.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testNews.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNews() throws Exception {
        int databaseSizeBeforeUpdate = newsRepository.findAll().size();

        // Create the News
        NewsDTO newsDTO = newsMapper.toDto(news);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsMockMvc.perform(put("/api/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the News in the database
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNews() throws Exception {
        // Initialize the database
        newsRepository.saveAndFlush(news);

        int databaseSizeBeforeDelete = newsRepository.findAll().size();

        // Delete the news
        restNewsMockMvc.perform(delete("/api/news/{id}", news.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<News> newsList = newsRepository.findAll();
        assertThat(newsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
