package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.UserProfile;
import com.myriadquest.kreiscms.repository.UserProfileRepository;
import com.myriadquest.kreiscms.service.UserProfileService;
import com.myriadquest.kreiscms.service.dto.UserProfileDTO;
import com.myriadquest.kreiscms.service.mapper.UserProfileMapper;
import com.myriadquest.kreiscms.service.dto.UserProfileCriteria;
import com.myriadquest.kreiscms.service.UserProfileQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.myriadquest.kreiscms.domain.enumeration.Title;
import com.myriadquest.kreiscms.domain.enumeration.Designation;
import com.myriadquest.kreiscms.domain.enumeration.UserType;
/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserProfileResourceIT {

    private static final Title DEFAULT_TITLE = Title.Dr;
    private static final Title UPDATED_TITLE = Title.Mr;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Designation DEFAULT_DESIGNATION = Designation.PRINCIPAL;
    private static final Designation UPDATED_DESIGNATION = Designation.ASST_TEACHER_KANNADA;

    private static final UserType DEFAULT_USER_TYPE = UserType.Principal;
    private static final UserType UPDATED_USER_TYPE = UserType.Teaching;

    private static final String DEFAULT_EDU_QUAL = "AAAAAAAAAA";
    private static final String UPDATED_EDU_QUAL = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_ME = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_ME = "BBBBBBBBBB";

    private static final String DEFAULT_HOBBIES = "AAAAAAAAAA";
    private static final String UPDATED_HOBBIES = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAAR = "AAAAAAAAAAAA";
    private static final String UPDATED_AADHAAR = "BBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_IMG_LINK = "AAAAAAAAAA";
    private static final String UPDATED_IMG_LINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FACEBOOK_LINK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_INSTA_LINK = "AAAAAAAAAA";
    private static final String UPDATED_INSTA_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_LINK = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileQueryService userProfileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .designation(DEFAULT_DESIGNATION)
            .userType(DEFAULT_USER_TYPE)
            .eduQual(DEFAULT_EDU_QUAL)
            .aboutMe(DEFAULT_ABOUT_ME)
            .hobbies(DEFAULT_HOBBIES)
            .aadhaar(DEFAULT_AADHAAR)
            .dob(DEFAULT_DOB)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .facebookLink(DEFAULT_FACEBOOK_LINK)
            .instaLink(DEFAULT_INSTA_LINK)
            .twitterLink(DEFAULT_TWITTER_LINK)
            .linkedLink(DEFAULT_LINKED_LINK)
            .tenantId(DEFAULT_TENANT_ID);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .designation(UPDATED_DESIGNATION)
            .userType(UPDATED_USER_TYPE)
            .eduQual(UPDATED_EDU_QUAL)
            .aboutMe(UPDATED_ABOUT_ME)
            .hobbies(UPDATED_HOBBIES)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .facebookLink(UPDATED_FACEBOOK_LINK)
            .instaLink(UPDATED_INSTA_LINK)
            .twitterLink(UPDATED_TWITTER_LINK)
            .linkedLink(UPDATED_LINKED_LINK)
            .tenantId(UPDATED_TENANT_ID);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();
        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testUserProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserProfile.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testUserProfile.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testUserProfile.getEduQual()).isEqualTo(DEFAULT_EDU_QUAL);
        assertThat(testUserProfile.getAboutMe()).isEqualTo(DEFAULT_ABOUT_ME);
        assertThat(testUserProfile.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testUserProfile.getAadhaar()).isEqualTo(DEFAULT_AADHAAR);
        assertThat(testUserProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserProfile.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testUserProfile.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testUserProfile.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testUserProfile.getFacebookLink()).isEqualTo(DEFAULT_FACEBOOK_LINK);
        assertThat(testUserProfile.getInstaLink()).isEqualTo(DEFAULT_INSTA_LINK);
        assertThat(testUserProfile.getTwitterLink()).isEqualTo(DEFAULT_TWITTER_LINK);
        assertThat(testUserProfile.getLinkedLink()).isEqualTo(DEFAULT_LINKED_LINK);
        assertThat(testUserProfile.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setTitle(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setFirstName(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setMobile(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setEmail(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setDesignation(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setUserType(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setDob(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);


        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eduQual").value(hasItem(DEFAULT_EDU_QUAL)))
            .andExpect(jsonPath("$.[*].aboutMe").value(hasItem(DEFAULT_ABOUT_ME)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].aadhaar").value(hasItem(DEFAULT_AADHAAR)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].facebookLink").value(hasItem(DEFAULT_FACEBOOK_LINK)))
            .andExpect(jsonPath("$.[*].instaLink").value(hasItem(DEFAULT_INSTA_LINK)))
            .andExpect(jsonPath("$.[*].twitterLink").value(hasItem(DEFAULT_TWITTER_LINK)))
            .andExpect(jsonPath("$.[*].linkedLink").value(hasItem(DEFAULT_LINKED_LINK)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
    }
    
    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.eduQual").value(DEFAULT_EDU_QUAL))
            .andExpect(jsonPath("$.aboutMe").value(DEFAULT_ABOUT_ME))
            .andExpect(jsonPath("$.hobbies").value(DEFAULT_HOBBIES))
            .andExpect(jsonPath("$.aadhaar").value(DEFAULT_AADHAAR))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.facebookLink").value(DEFAULT_FACEBOOK_LINK))
            .andExpect(jsonPath("$.instaLink").value(DEFAULT_INSTA_LINK))
            .andExpect(jsonPath("$.twitterLink").value(DEFAULT_TWITTER_LINK))
            .andExpect(jsonPath("$.linkedLink").value(DEFAULT_LINKED_LINK))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getUserProfilesByIdFiltering() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        Long id = userProfile.getId();

        defaultUserProfileShouldBeFound("id.equals=" + id);
        defaultUserProfileShouldNotBeFound("id.notEquals=" + id);

        defaultUserProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultUserProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where title equals to DEFAULT_TITLE
        defaultUserProfileShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the userProfileList where title equals to UPDATED_TITLE
        defaultUserProfileShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where title not equals to DEFAULT_TITLE
        defaultUserProfileShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the userProfileList where title not equals to UPDATED_TITLE
        defaultUserProfileShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultUserProfileShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the userProfileList where title equals to UPDATED_TITLE
        defaultUserProfileShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where title is not null
        defaultUserProfileShouldBeFound("title.specified=true");

        // Get all the userProfileList where title is null
        defaultUserProfileShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName equals to DEFAULT_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName not equals to DEFAULT_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName not equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the userProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName is not null
        defaultUserProfileShouldBeFound("firstName.specified=true");

        // Get all the userProfileList where firstName is null
        defaultUserProfileShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName contains DEFAULT_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName contains UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName does not contain DEFAULT_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName does not contain UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName equals to DEFAULT_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName equals to UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName not equals to DEFAULT_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName not equals to UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the userProfileList where lastName equals to UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName is not null
        defaultUserProfileShouldBeFound("lastName.specified=true");

        // Get all the userProfileList where lastName is null
        defaultUserProfileShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName contains DEFAULT_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName contains UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName does not contain DEFAULT_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName does not contain UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile equals to DEFAULT_MOBILE
        defaultUserProfileShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the userProfileList where mobile equals to UPDATED_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile not equals to DEFAULT_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.notEquals=" + DEFAULT_MOBILE);

        // Get all the userProfileList where mobile not equals to UPDATED_MOBILE
        defaultUserProfileShouldBeFound("mobile.notEquals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultUserProfileShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the userProfileList where mobile equals to UPDATED_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile is not null
        defaultUserProfileShouldBeFound("mobile.specified=true");

        // Get all the userProfileList where mobile is null
        defaultUserProfileShouldNotBeFound("mobile.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByMobileContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile contains DEFAULT_MOBILE
        defaultUserProfileShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the userProfileList where mobile contains UPDATED_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where mobile does not contain DEFAULT_MOBILE
        defaultUserProfileShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the userProfileList where mobile does not contain UPDATED_MOBILE
        defaultUserProfileShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email equals to DEFAULT_EMAIL
        defaultUserProfileShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the userProfileList where email equals to UPDATED_EMAIL
        defaultUserProfileShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email not equals to DEFAULT_EMAIL
        defaultUserProfileShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the userProfileList where email not equals to UPDATED_EMAIL
        defaultUserProfileShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUserProfileShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the userProfileList where email equals to UPDATED_EMAIL
        defaultUserProfileShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email is not null
        defaultUserProfileShouldBeFound("email.specified=true");

        // Get all the userProfileList where email is null
        defaultUserProfileShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByEmailContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email contains DEFAULT_EMAIL
        defaultUserProfileShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the userProfileList where email contains UPDATED_EMAIL
        defaultUserProfileShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where email does not contain DEFAULT_EMAIL
        defaultUserProfileShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the userProfileList where email does not contain UPDATED_EMAIL
        defaultUserProfileShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where designation equals to DEFAULT_DESIGNATION
        defaultUserProfileShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the userProfileList where designation equals to UPDATED_DESIGNATION
        defaultUserProfileShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where designation not equals to DEFAULT_DESIGNATION
        defaultUserProfileShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the userProfileList where designation not equals to UPDATED_DESIGNATION
        defaultUserProfileShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultUserProfileShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the userProfileList where designation equals to UPDATED_DESIGNATION
        defaultUserProfileShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where designation is not null
        defaultUserProfileShouldBeFound("designation.specified=true");

        // Get all the userProfileList where designation is null
        defaultUserProfileShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType equals to DEFAULT_USER_TYPE
        defaultUserProfileShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType equals to UPDATED_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType not equals to DEFAULT_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType not equals to UPDATED_USER_TYPE
        defaultUserProfileShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultUserProfileShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the userProfileList where userType equals to UPDATED_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType is not null
        defaultUserProfileShouldBeFound("userType.specified=true");

        // Get all the userProfileList where userType is null
        defaultUserProfileShouldNotBeFound("userType.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEduQualIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual equals to DEFAULT_EDU_QUAL
        defaultUserProfileShouldBeFound("eduQual.equals=" + DEFAULT_EDU_QUAL);

        // Get all the userProfileList where eduQual equals to UPDATED_EDU_QUAL
        defaultUserProfileShouldNotBeFound("eduQual.equals=" + UPDATED_EDU_QUAL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEduQualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual not equals to DEFAULT_EDU_QUAL
        defaultUserProfileShouldNotBeFound("eduQual.notEquals=" + DEFAULT_EDU_QUAL);

        // Get all the userProfileList where eduQual not equals to UPDATED_EDU_QUAL
        defaultUserProfileShouldBeFound("eduQual.notEquals=" + UPDATED_EDU_QUAL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEduQualIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual in DEFAULT_EDU_QUAL or UPDATED_EDU_QUAL
        defaultUserProfileShouldBeFound("eduQual.in=" + DEFAULT_EDU_QUAL + "," + UPDATED_EDU_QUAL);

        // Get all the userProfileList where eduQual equals to UPDATED_EDU_QUAL
        defaultUserProfileShouldNotBeFound("eduQual.in=" + UPDATED_EDU_QUAL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEduQualIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual is not null
        defaultUserProfileShouldBeFound("eduQual.specified=true");

        // Get all the userProfileList where eduQual is null
        defaultUserProfileShouldNotBeFound("eduQual.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByEduQualContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual contains DEFAULT_EDU_QUAL
        defaultUserProfileShouldBeFound("eduQual.contains=" + DEFAULT_EDU_QUAL);

        // Get all the userProfileList where eduQual contains UPDATED_EDU_QUAL
        defaultUserProfileShouldNotBeFound("eduQual.contains=" + UPDATED_EDU_QUAL);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByEduQualNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where eduQual does not contain DEFAULT_EDU_QUAL
        defaultUserProfileShouldNotBeFound("eduQual.doesNotContain=" + DEFAULT_EDU_QUAL);

        // Get all the userProfileList where eduQual does not contain UPDATED_EDU_QUAL
        defaultUserProfileShouldBeFound("eduQual.doesNotContain=" + UPDATED_EDU_QUAL);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByAboutMeIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe equals to DEFAULT_ABOUT_ME
        defaultUserProfileShouldBeFound("aboutMe.equals=" + DEFAULT_ABOUT_ME);

        // Get all the userProfileList where aboutMe equals to UPDATED_ABOUT_ME
        defaultUserProfileShouldNotBeFound("aboutMe.equals=" + UPDATED_ABOUT_ME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAboutMeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe not equals to DEFAULT_ABOUT_ME
        defaultUserProfileShouldNotBeFound("aboutMe.notEquals=" + DEFAULT_ABOUT_ME);

        // Get all the userProfileList where aboutMe not equals to UPDATED_ABOUT_ME
        defaultUserProfileShouldBeFound("aboutMe.notEquals=" + UPDATED_ABOUT_ME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAboutMeIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe in DEFAULT_ABOUT_ME or UPDATED_ABOUT_ME
        defaultUserProfileShouldBeFound("aboutMe.in=" + DEFAULT_ABOUT_ME + "," + UPDATED_ABOUT_ME);

        // Get all the userProfileList where aboutMe equals to UPDATED_ABOUT_ME
        defaultUserProfileShouldNotBeFound("aboutMe.in=" + UPDATED_ABOUT_ME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAboutMeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe is not null
        defaultUserProfileShouldBeFound("aboutMe.specified=true");

        // Get all the userProfileList where aboutMe is null
        defaultUserProfileShouldNotBeFound("aboutMe.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByAboutMeContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe contains DEFAULT_ABOUT_ME
        defaultUserProfileShouldBeFound("aboutMe.contains=" + DEFAULT_ABOUT_ME);

        // Get all the userProfileList where aboutMe contains UPDATED_ABOUT_ME
        defaultUserProfileShouldNotBeFound("aboutMe.contains=" + UPDATED_ABOUT_ME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAboutMeNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aboutMe does not contain DEFAULT_ABOUT_ME
        defaultUserProfileShouldNotBeFound("aboutMe.doesNotContain=" + DEFAULT_ABOUT_ME);

        // Get all the userProfileList where aboutMe does not contain UPDATED_ABOUT_ME
        defaultUserProfileShouldBeFound("aboutMe.doesNotContain=" + UPDATED_ABOUT_ME);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByHobbiesIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies equals to DEFAULT_HOBBIES
        defaultUserProfileShouldBeFound("hobbies.equals=" + DEFAULT_HOBBIES);

        // Get all the userProfileList where hobbies equals to UPDATED_HOBBIES
        defaultUserProfileShouldNotBeFound("hobbies.equals=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByHobbiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies not equals to DEFAULT_HOBBIES
        defaultUserProfileShouldNotBeFound("hobbies.notEquals=" + DEFAULT_HOBBIES);

        // Get all the userProfileList where hobbies not equals to UPDATED_HOBBIES
        defaultUserProfileShouldBeFound("hobbies.notEquals=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByHobbiesIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies in DEFAULT_HOBBIES or UPDATED_HOBBIES
        defaultUserProfileShouldBeFound("hobbies.in=" + DEFAULT_HOBBIES + "," + UPDATED_HOBBIES);

        // Get all the userProfileList where hobbies equals to UPDATED_HOBBIES
        defaultUserProfileShouldNotBeFound("hobbies.in=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByHobbiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies is not null
        defaultUserProfileShouldBeFound("hobbies.specified=true");

        // Get all the userProfileList where hobbies is null
        defaultUserProfileShouldNotBeFound("hobbies.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByHobbiesContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies contains DEFAULT_HOBBIES
        defaultUserProfileShouldBeFound("hobbies.contains=" + DEFAULT_HOBBIES);

        // Get all the userProfileList where hobbies contains UPDATED_HOBBIES
        defaultUserProfileShouldNotBeFound("hobbies.contains=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByHobbiesNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where hobbies does not contain DEFAULT_HOBBIES
        defaultUserProfileShouldNotBeFound("hobbies.doesNotContain=" + DEFAULT_HOBBIES);

        // Get all the userProfileList where hobbies does not contain UPDATED_HOBBIES
        defaultUserProfileShouldBeFound("hobbies.doesNotContain=" + UPDATED_HOBBIES);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByAadhaarIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar equals to DEFAULT_AADHAAR
        defaultUserProfileShouldBeFound("aadhaar.equals=" + DEFAULT_AADHAAR);

        // Get all the userProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultUserProfileShouldNotBeFound("aadhaar.equals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAadhaarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar not equals to DEFAULT_AADHAAR
        defaultUserProfileShouldNotBeFound("aadhaar.notEquals=" + DEFAULT_AADHAAR);

        // Get all the userProfileList where aadhaar not equals to UPDATED_AADHAAR
        defaultUserProfileShouldBeFound("aadhaar.notEquals=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAadhaarIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar in DEFAULT_AADHAAR or UPDATED_AADHAAR
        defaultUserProfileShouldBeFound("aadhaar.in=" + DEFAULT_AADHAAR + "," + UPDATED_AADHAAR);

        // Get all the userProfileList where aadhaar equals to UPDATED_AADHAAR
        defaultUserProfileShouldNotBeFound("aadhaar.in=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAadhaarIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar is not null
        defaultUserProfileShouldBeFound("aadhaar.specified=true");

        // Get all the userProfileList where aadhaar is null
        defaultUserProfileShouldNotBeFound("aadhaar.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByAadhaarContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar contains DEFAULT_AADHAAR
        defaultUserProfileShouldBeFound("aadhaar.contains=" + DEFAULT_AADHAAR);

        // Get all the userProfileList where aadhaar contains UPDATED_AADHAAR
        defaultUserProfileShouldNotBeFound("aadhaar.contains=" + UPDATED_AADHAAR);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByAadhaarNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where aadhaar does not contain DEFAULT_AADHAAR
        defaultUserProfileShouldNotBeFound("aadhaar.doesNotContain=" + DEFAULT_AADHAAR);

        // Get all the userProfileList where aadhaar does not contain UPDATED_AADHAAR
        defaultUserProfileShouldBeFound("aadhaar.doesNotContain=" + UPDATED_AADHAAR);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob equals to DEFAULT_DOB
        defaultUserProfileShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the userProfileList where dob equals to UPDATED_DOB
        defaultUserProfileShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob not equals to DEFAULT_DOB
        defaultUserProfileShouldNotBeFound("dob.notEquals=" + DEFAULT_DOB);

        // Get all the userProfileList where dob not equals to UPDATED_DOB
        defaultUserProfileShouldBeFound("dob.notEquals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultUserProfileShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the userProfileList where dob equals to UPDATED_DOB
        defaultUserProfileShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob is not null
        defaultUserProfileShouldBeFound("dob.specified=true");

        // Get all the userProfileList where dob is null
        defaultUserProfileShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob is greater than or equal to DEFAULT_DOB
        defaultUserProfileShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the userProfileList where dob is greater than or equal to UPDATED_DOB
        defaultUserProfileShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob is less than or equal to DEFAULT_DOB
        defaultUserProfileShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the userProfileList where dob is less than or equal to SMALLER_DOB
        defaultUserProfileShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob is less than DEFAULT_DOB
        defaultUserProfileShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the userProfileList where dob is less than UPDATED_DOB
        defaultUserProfileShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where dob is greater than DEFAULT_DOB
        defaultUserProfileShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the userProfileList where dob is greater than SMALLER_DOB
        defaultUserProfileShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByImgLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink equals to DEFAULT_IMG_LINK
        defaultUserProfileShouldBeFound("imgLink.equals=" + DEFAULT_IMG_LINK);

        // Get all the userProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultUserProfileShouldNotBeFound("imgLink.equals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByImgLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink not equals to DEFAULT_IMG_LINK
        defaultUserProfileShouldNotBeFound("imgLink.notEquals=" + DEFAULT_IMG_LINK);

        // Get all the userProfileList where imgLink not equals to UPDATED_IMG_LINK
        defaultUserProfileShouldBeFound("imgLink.notEquals=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByImgLinkIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink in DEFAULT_IMG_LINK or UPDATED_IMG_LINK
        defaultUserProfileShouldBeFound("imgLink.in=" + DEFAULT_IMG_LINK + "," + UPDATED_IMG_LINK);

        // Get all the userProfileList where imgLink equals to UPDATED_IMG_LINK
        defaultUserProfileShouldNotBeFound("imgLink.in=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByImgLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink is not null
        defaultUserProfileShouldBeFound("imgLink.specified=true");

        // Get all the userProfileList where imgLink is null
        defaultUserProfileShouldNotBeFound("imgLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByImgLinkContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink contains DEFAULT_IMG_LINK
        defaultUserProfileShouldBeFound("imgLink.contains=" + DEFAULT_IMG_LINK);

        // Get all the userProfileList where imgLink contains UPDATED_IMG_LINK
        defaultUserProfileShouldNotBeFound("imgLink.contains=" + UPDATED_IMG_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByImgLinkNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where imgLink does not contain DEFAULT_IMG_LINK
        defaultUserProfileShouldNotBeFound("imgLink.doesNotContain=" + DEFAULT_IMG_LINK);

        // Get all the userProfileList where imgLink does not contain UPDATED_IMG_LINK
        defaultUserProfileShouldBeFound("imgLink.doesNotContain=" + UPDATED_IMG_LINK);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink equals to DEFAULT_FACEBOOK_LINK
        defaultUserProfileShouldBeFound("facebookLink.equals=" + DEFAULT_FACEBOOK_LINK);

        // Get all the userProfileList where facebookLink equals to UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldNotBeFound("facebookLink.equals=" + UPDATED_FACEBOOK_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink not equals to DEFAULT_FACEBOOK_LINK
        defaultUserProfileShouldNotBeFound("facebookLink.notEquals=" + DEFAULT_FACEBOOK_LINK);

        // Get all the userProfileList where facebookLink not equals to UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldBeFound("facebookLink.notEquals=" + UPDATED_FACEBOOK_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink in DEFAULT_FACEBOOK_LINK or UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldBeFound("facebookLink.in=" + DEFAULT_FACEBOOK_LINK + "," + UPDATED_FACEBOOK_LINK);

        // Get all the userProfileList where facebookLink equals to UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldNotBeFound("facebookLink.in=" + UPDATED_FACEBOOK_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink is not null
        defaultUserProfileShouldBeFound("facebookLink.specified=true");

        // Get all the userProfileList where facebookLink is null
        defaultUserProfileShouldNotBeFound("facebookLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink contains DEFAULT_FACEBOOK_LINK
        defaultUserProfileShouldBeFound("facebookLink.contains=" + DEFAULT_FACEBOOK_LINK);

        // Get all the userProfileList where facebookLink contains UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldNotBeFound("facebookLink.contains=" + UPDATED_FACEBOOK_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFacebookLinkNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where facebookLink does not contain DEFAULT_FACEBOOK_LINK
        defaultUserProfileShouldNotBeFound("facebookLink.doesNotContain=" + DEFAULT_FACEBOOK_LINK);

        // Get all the userProfileList where facebookLink does not contain UPDATED_FACEBOOK_LINK
        defaultUserProfileShouldBeFound("facebookLink.doesNotContain=" + UPDATED_FACEBOOK_LINK);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink equals to DEFAULT_INSTA_LINK
        defaultUserProfileShouldBeFound("instaLink.equals=" + DEFAULT_INSTA_LINK);

        // Get all the userProfileList where instaLink equals to UPDATED_INSTA_LINK
        defaultUserProfileShouldNotBeFound("instaLink.equals=" + UPDATED_INSTA_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink not equals to DEFAULT_INSTA_LINK
        defaultUserProfileShouldNotBeFound("instaLink.notEquals=" + DEFAULT_INSTA_LINK);

        // Get all the userProfileList where instaLink not equals to UPDATED_INSTA_LINK
        defaultUserProfileShouldBeFound("instaLink.notEquals=" + UPDATED_INSTA_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink in DEFAULT_INSTA_LINK or UPDATED_INSTA_LINK
        defaultUserProfileShouldBeFound("instaLink.in=" + DEFAULT_INSTA_LINK + "," + UPDATED_INSTA_LINK);

        // Get all the userProfileList where instaLink equals to UPDATED_INSTA_LINK
        defaultUserProfileShouldNotBeFound("instaLink.in=" + UPDATED_INSTA_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink is not null
        defaultUserProfileShouldBeFound("instaLink.specified=true");

        // Get all the userProfileList where instaLink is null
        defaultUserProfileShouldNotBeFound("instaLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink contains DEFAULT_INSTA_LINK
        defaultUserProfileShouldBeFound("instaLink.contains=" + DEFAULT_INSTA_LINK);

        // Get all the userProfileList where instaLink contains UPDATED_INSTA_LINK
        defaultUserProfileShouldNotBeFound("instaLink.contains=" + UPDATED_INSTA_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByInstaLinkNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where instaLink does not contain DEFAULT_INSTA_LINK
        defaultUserProfileShouldNotBeFound("instaLink.doesNotContain=" + DEFAULT_INSTA_LINK);

        // Get all the userProfileList where instaLink does not contain UPDATED_INSTA_LINK
        defaultUserProfileShouldBeFound("instaLink.doesNotContain=" + UPDATED_INSTA_LINK);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink equals to DEFAULT_TWITTER_LINK
        defaultUserProfileShouldBeFound("twitterLink.equals=" + DEFAULT_TWITTER_LINK);

        // Get all the userProfileList where twitterLink equals to UPDATED_TWITTER_LINK
        defaultUserProfileShouldNotBeFound("twitterLink.equals=" + UPDATED_TWITTER_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink not equals to DEFAULT_TWITTER_LINK
        defaultUserProfileShouldNotBeFound("twitterLink.notEquals=" + DEFAULT_TWITTER_LINK);

        // Get all the userProfileList where twitterLink not equals to UPDATED_TWITTER_LINK
        defaultUserProfileShouldBeFound("twitterLink.notEquals=" + UPDATED_TWITTER_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink in DEFAULT_TWITTER_LINK or UPDATED_TWITTER_LINK
        defaultUserProfileShouldBeFound("twitterLink.in=" + DEFAULT_TWITTER_LINK + "," + UPDATED_TWITTER_LINK);

        // Get all the userProfileList where twitterLink equals to UPDATED_TWITTER_LINK
        defaultUserProfileShouldNotBeFound("twitterLink.in=" + UPDATED_TWITTER_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink is not null
        defaultUserProfileShouldBeFound("twitterLink.specified=true");

        // Get all the userProfileList where twitterLink is null
        defaultUserProfileShouldNotBeFound("twitterLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink contains DEFAULT_TWITTER_LINK
        defaultUserProfileShouldBeFound("twitterLink.contains=" + DEFAULT_TWITTER_LINK);

        // Get all the userProfileList where twitterLink contains UPDATED_TWITTER_LINK
        defaultUserProfileShouldNotBeFound("twitterLink.contains=" + UPDATED_TWITTER_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTwitterLinkNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where twitterLink does not contain DEFAULT_TWITTER_LINK
        defaultUserProfileShouldNotBeFound("twitterLink.doesNotContain=" + DEFAULT_TWITTER_LINK);

        // Get all the userProfileList where twitterLink does not contain UPDATED_TWITTER_LINK
        defaultUserProfileShouldBeFound("twitterLink.doesNotContain=" + UPDATED_TWITTER_LINK);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink equals to DEFAULT_LINKED_LINK
        defaultUserProfileShouldBeFound("linkedLink.equals=" + DEFAULT_LINKED_LINK);

        // Get all the userProfileList where linkedLink equals to UPDATED_LINKED_LINK
        defaultUserProfileShouldNotBeFound("linkedLink.equals=" + UPDATED_LINKED_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink not equals to DEFAULT_LINKED_LINK
        defaultUserProfileShouldNotBeFound("linkedLink.notEquals=" + DEFAULT_LINKED_LINK);

        // Get all the userProfileList where linkedLink not equals to UPDATED_LINKED_LINK
        defaultUserProfileShouldBeFound("linkedLink.notEquals=" + UPDATED_LINKED_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink in DEFAULT_LINKED_LINK or UPDATED_LINKED_LINK
        defaultUserProfileShouldBeFound("linkedLink.in=" + DEFAULT_LINKED_LINK + "," + UPDATED_LINKED_LINK);

        // Get all the userProfileList where linkedLink equals to UPDATED_LINKED_LINK
        defaultUserProfileShouldNotBeFound("linkedLink.in=" + UPDATED_LINKED_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink is not null
        defaultUserProfileShouldBeFound("linkedLink.specified=true");

        // Get all the userProfileList where linkedLink is null
        defaultUserProfileShouldNotBeFound("linkedLink.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink contains DEFAULT_LINKED_LINK
        defaultUserProfileShouldBeFound("linkedLink.contains=" + DEFAULT_LINKED_LINK);

        // Get all the userProfileList where linkedLink contains UPDATED_LINKED_LINK
        defaultUserProfileShouldNotBeFound("linkedLink.contains=" + UPDATED_LINKED_LINK);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLinkedLinkNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where linkedLink does not contain DEFAULT_LINKED_LINK
        defaultUserProfileShouldNotBeFound("linkedLink.doesNotContain=" + DEFAULT_LINKED_LINK);

        // Get all the userProfileList where linkedLink does not contain UPDATED_LINKED_LINK
        defaultUserProfileShouldBeFound("linkedLink.doesNotContain=" + UPDATED_LINKED_LINK);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId equals to DEFAULT_TENANT_ID
        defaultUserProfileShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the userProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultUserProfileShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId not equals to DEFAULT_TENANT_ID
        defaultUserProfileShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the userProfileList where tenantId not equals to UPDATED_TENANT_ID
        defaultUserProfileShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultUserProfileShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the userProfileList where tenantId equals to UPDATED_TENANT_ID
        defaultUserProfileShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId is not null
        defaultUserProfileShouldBeFound("tenantId.specified=true");

        // Get all the userProfileList where tenantId is null
        defaultUserProfileShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId contains DEFAULT_TENANT_ID
        defaultUserProfileShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the userProfileList where tenantId contains UPDATED_TENANT_ID
        defaultUserProfileShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where tenantId does not contain DEFAULT_TENANT_ID
        defaultUserProfileShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the userProfileList where tenantId does not contain UPDATED_TENANT_ID
        defaultUserProfileShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserProfileShouldBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eduQual").value(hasItem(DEFAULT_EDU_QUAL)))
            .andExpect(jsonPath("$.[*].aboutMe").value(hasItem(DEFAULT_ABOUT_ME)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].aadhaar").value(hasItem(DEFAULT_AADHAAR)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].facebookLink").value(hasItem(DEFAULT_FACEBOOK_LINK)))
            .andExpect(jsonPath("$.[*].instaLink").value(hasItem(DEFAULT_INSTA_LINK)))
            .andExpect(jsonPath("$.[*].twitterLink").value(hasItem(DEFAULT_TWITTER_LINK)))
            .andExpect(jsonPath("$.[*].linkedLink").value(hasItem(DEFAULT_LINKED_LINK)))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserProfileShouldNotBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .designation(UPDATED_DESIGNATION)
            .userType(UPDATED_USER_TYPE)
            .eduQual(UPDATED_EDU_QUAL)
            .aboutMe(UPDATED_ABOUT_ME)
            .hobbies(UPDATED_HOBBIES)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .facebookLink(UPDATED_FACEBOOK_LINK)
            .instaLink(UPDATED_INSTA_LINK)
            .twitterLink(UPDATED_TWITTER_LINK)
            .linkedLink(UPDATED_LINKED_LINK)
            .tenantId(UPDATED_TENANT_ID);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testUserProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserProfile.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testUserProfile.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testUserProfile.getEduQual()).isEqualTo(UPDATED_EDU_QUAL);
        assertThat(testUserProfile.getAboutMe()).isEqualTo(UPDATED_ABOUT_ME);
        assertThat(testUserProfile.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testUserProfile.getAadhaar()).isEqualTo(UPDATED_AADHAAR);
        assertThat(testUserProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserProfile.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testUserProfile.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testUserProfile.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testUserProfile.getFacebookLink()).isEqualTo(UPDATED_FACEBOOK_LINK);
        assertThat(testUserProfile.getInstaLink()).isEqualTo(UPDATED_INSTA_LINK);
        assertThat(testUserProfile.getTwitterLink()).isEqualTo(UPDATED_TWITTER_LINK);
        assertThat(testUserProfile.getLinkedLink()).isEqualTo(UPDATED_LINKED_LINK);
        assertThat(testUserProfile.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
