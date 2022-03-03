package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ContactDetails;
import com.myriadquest.kreiscms.repository.ContactDetailsRepository;
import com.myriadquest.kreiscms.service.ContactDetailsService;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;
import com.myriadquest.kreiscms.service.mapper.ContactDetailsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContactDetailsResource} REST controller.
 */
@SpringBootTest(classes = IgrscmsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactDetailsResourceIT {

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_LINK = "AAAAAAAAAA";
    private static final String UPDATED_MAP_LINK = "BBBBBBBBBB";

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ContactDetailsMapper contactDetailsMapper;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactDetailsMockMvc;

    private ContactDetails contactDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactDetails createEntity(EntityManager em) {
        ContactDetails contactDetails = new ContactDetails()
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .contact(DEFAULT_CONTACT)
            .mapLink(DEFAULT_MAP_LINK);
        return contactDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactDetails createUpdatedEntity(EntityManager em) {
        ContactDetails contactDetails = new ContactDetails()
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .mapLink(UPDATED_MAP_LINK);
        return contactDetails;
    }

    @BeforeEach
    public void initTest() {
        contactDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactDetails() throws Exception {
        int databaseSizeBeforeCreate = contactDetailsRepository.findAll().size();
        // Create the ContactDetails
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(contactDetails);
        restContactDetailsMockMvc.perform(post("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ContactDetails testContactDetails = contactDetailsList.get(contactDetailsList.size() - 1);
        assertThat(testContactDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testContactDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactDetails.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testContactDetails.getMapLink()).isEqualTo(DEFAULT_MAP_LINK);
    }

    @Test
    @Transactional
    public void createContactDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactDetailsRepository.findAll().size();

        // Create the ContactDetails with an existing ID
        contactDetails.setId(1L);
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(contactDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactDetailsMockMvc.perform(post("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDetailsRepository.findAll().size();
        // set the field null
        contactDetails.setAddress(null);

        // Create the ContactDetails, which fails.
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(contactDetails);


        restContactDetailsMockMvc.perform(post("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactDetailsRepository.findAll().size();
        // set the field null
        contactDetails.setContact(null);

        // Create the ContactDetails, which fails.
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(contactDetails);


        restContactDetailsMockMvc.perform(post("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList
        restContactDetailsMockMvc.perform(get("/api/contact-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].mapLink").value(hasItem(DEFAULT_MAP_LINK)));
    }
    
    @Test
    @Transactional
    public void getContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contact-details/{id}", contactDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactDetails.getId().intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.mapLink").value(DEFAULT_MAP_LINK));
    }
    @Test
    @Transactional
    public void getNonExistingContactDetails() throws Exception {
        // Get the contactDetails
        restContactDetailsMockMvc.perform(get("/api/contact-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        int databaseSizeBeforeUpdate = contactDetailsRepository.findAll().size();

        // Update the contactDetails
        ContactDetails updatedContactDetails = contactDetailsRepository.findById(contactDetails.getId()).get();
        // Disconnect from session so that the updates on updatedContactDetails are not directly saved in db
        em.detach(updatedContactDetails);
        updatedContactDetails
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .contact(UPDATED_CONTACT)
            .mapLink(UPDATED_MAP_LINK);
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(updatedContactDetails);

        restContactDetailsMockMvc.perform(put("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeUpdate);
        ContactDetails testContactDetails = contactDetailsList.get(contactDetailsList.size() - 1);
        assertThat(testContactDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testContactDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactDetails.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testContactDetails.getMapLink()).isEqualTo(UPDATED_MAP_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingContactDetails() throws Exception {
        int databaseSizeBeforeUpdate = contactDetailsRepository.findAll().size();

        // Create the ContactDetails
        ContactDetailsDTO contactDetailsDTO = contactDetailsMapper.toDto(contactDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactDetailsMockMvc.perform(put("/api/contact-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contactDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactDetails in the database
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactDetails() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        int databaseSizeBeforeDelete = contactDetailsRepository.findAll().size();

        // Delete the contactDetails
        restContactDetailsMockMvc.perform(delete("/api/contact-details/{id}", contactDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactDetails> contactDetailsList = contactDetailsRepository.findAll();
        assertThat(contactDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
