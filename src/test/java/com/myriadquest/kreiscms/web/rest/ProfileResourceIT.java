package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.Profile;
import com.myriadquest.kreiscms.domain.User;
import com.myriadquest.kreiscms.repository.ProfileRepository;
import com.myriadquest.kreiscms.service.ProfileService;
import com.myriadquest.kreiscms.service.dto.ProfileDTO;
import com.myriadquest.kreiscms.service.mapper.ProfileMapper;

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
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfileResourceIT {

    private static final Title DEFAULT_TITLE = Title.Dr;
    private static final Title UPDATED_TITLE = Title.Mr;

    private static final Designation DEFAULT_DESIGNATION = Designation.ASST_TEACHER_KANNADA;
    private static final Designation UPDATED_DESIGNATION = Designation.ASST_TEACHER_ENGLISH;

    private static final UserType DEFAULT_USER_TYPE = UserType.Teaching;
    private static final UserType UPDATED_USER_TYPE = UserType.Non_Teaching;

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

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

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

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .title(DEFAULT_TITLE)
            .designation(DEFAULT_DESIGNATION)
            .userType(DEFAULT_USER_TYPE)
            .eduQual(DEFAULT_EDU_QUAL)
            .aboutMe(DEFAULT_ABOUT_ME)
            .hobbies(DEFAULT_HOBBIES)
            .aadhaar(DEFAULT_AADHAAR)
            .dob(DEFAULT_DOB)
            .mobile(DEFAULT_MOBILE)
            .email(DEFAULT_EMAIL)
            .imgLink(DEFAULT_IMG_LINK)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .facebookLink(DEFAULT_FACEBOOK_LINK)
            .instaLink(DEFAULT_INSTA_LINK)
            .twitterLink(DEFAULT_TWITTER_LINK)
            .linkedLink(DEFAULT_LINKED_LINK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .title(UPDATED_TITLE)
            .designation(UPDATED_DESIGNATION)
            .userType(UPDATED_USER_TYPE)
            .eduQual(UPDATED_EDU_QUAL)
            .aboutMe(UPDATED_ABOUT_ME)
            .hobbies(UPDATED_HOBBIES)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .facebookLink(UPDATED_FACEBOOK_LINK)
            .instaLink(UPDATED_INSTA_LINK)
            .twitterLink(UPDATED_TWITTER_LINK)
            .linkedLink(UPDATED_LINKED_LINK);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProfile.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testProfile.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testProfile.getEduQual()).isEqualTo(DEFAULT_EDU_QUAL);
        assertThat(testProfile.getAboutMe()).isEqualTo(DEFAULT_ABOUT_ME);
        assertThat(testProfile.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testProfile.getAadhaar()).isEqualTo(DEFAULT_AADHAAR);
        assertThat(testProfile.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testProfile.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testProfile.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testProfile.getImgLink()).isEqualTo(DEFAULT_IMG_LINK);
        assertThat(testProfile.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testProfile.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testProfile.getFacebookLink()).isEqualTo(DEFAULT_FACEBOOK_LINK);
        assertThat(testProfile.getInstaLink()).isEqualTo(DEFAULT_INSTA_LINK);
        assertThat(testProfile.getTwitterLink()).isEqualTo(DEFAULT_TWITTER_LINK);
        assertThat(testProfile.getLinkedLink()).isEqualTo(DEFAULT_LINKED_LINK);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setTitle(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setDesignation(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setUserType(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAboutMeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setAboutMe(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAadhaarIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setAadhaar(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setDob(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setMobile(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setEmail(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFacebookLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setFacebookLink(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInstaLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setInstaLink(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTwitterLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setTwitterLink(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLinkedLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setLinkedLink(null);

        // Create the Profile, which fails.
        ProfileDTO profileDTO = profileMapper.toDto(profile);


        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eduQual").value(hasItem(DEFAULT_EDU_QUAL)))
            .andExpect(jsonPath("$.[*].aboutMe").value(hasItem(DEFAULT_ABOUT_ME)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].aadhaar").value(hasItem(DEFAULT_AADHAAR)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imgLink").value(hasItem(DEFAULT_IMG_LINK)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].facebookLink").value(hasItem(DEFAULT_FACEBOOK_LINK)))
            .andExpect(jsonPath("$.[*].instaLink").value(hasItem(DEFAULT_INSTA_LINK)))
            .andExpect(jsonPath("$.[*].twitterLink").value(hasItem(DEFAULT_TWITTER_LINK)))
            .andExpect(jsonPath("$.[*].linkedLink").value(hasItem(DEFAULT_LINKED_LINK)));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.eduQual").value(DEFAULT_EDU_QUAL))
            .andExpect(jsonPath("$.aboutMe").value(DEFAULT_ABOUT_ME))
            .andExpect(jsonPath("$.hobbies").value(DEFAULT_HOBBIES))
            .andExpect(jsonPath("$.aadhaar").value(DEFAULT_AADHAAR))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imgLink").value(DEFAULT_IMG_LINK))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.facebookLink").value(DEFAULT_FACEBOOK_LINK))
            .andExpect(jsonPath("$.instaLink").value(DEFAULT_INSTA_LINK))
            .andExpect(jsonPath("$.twitterLink").value(DEFAULT_TWITTER_LINK))
            .andExpect(jsonPath("$.linkedLink").value(DEFAULT_LINKED_LINK));
    }
    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .title(UPDATED_TITLE)
            .designation(UPDATED_DESIGNATION)
            .userType(UPDATED_USER_TYPE)
            .eduQual(UPDATED_EDU_QUAL)
            .aboutMe(UPDATED_ABOUT_ME)
            .hobbies(UPDATED_HOBBIES)
            .aadhaar(UPDATED_AADHAAR)
            .dob(UPDATED_DOB)
            .mobile(UPDATED_MOBILE)
            .email(UPDATED_EMAIL)
            .imgLink(UPDATED_IMG_LINK)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .facebookLink(UPDATED_FACEBOOK_LINK)
            .instaLink(UPDATED_INSTA_LINK)
            .twitterLink(UPDATED_TWITTER_LINK)
            .linkedLink(UPDATED_LINKED_LINK);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProfile.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testProfile.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testProfile.getEduQual()).isEqualTo(UPDATED_EDU_QUAL);
        assertThat(testProfile.getAboutMe()).isEqualTo(UPDATED_ABOUT_ME);
        assertThat(testProfile.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testProfile.getAadhaar()).isEqualTo(UPDATED_AADHAAR);
        assertThat(testProfile.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testProfile.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testProfile.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testProfile.getImgLink()).isEqualTo(UPDATED_IMG_LINK);
        assertThat(testProfile.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testProfile.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testProfile.getFacebookLink()).isEqualTo(UPDATED_FACEBOOK_LINK);
        assertThat(testProfile.getInstaLink()).isEqualTo(UPDATED_INSTA_LINK);
        assertThat(testProfile.getTwitterLink()).isEqualTo(UPDATED_TWITTER_LINK);
        assertThat(testProfile.getLinkedLink()).isEqualTo(UPDATED_LINKED_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
