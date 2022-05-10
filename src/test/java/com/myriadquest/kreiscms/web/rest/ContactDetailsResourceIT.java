package com.myriadquest.kreiscms.web.rest;

import com.myriadquest.kreiscms.IgrscmsApp;
import com.myriadquest.kreiscms.domain.ContactDetails;
import com.myriadquest.kreiscms.repository.ContactDetailsRepository;
import com.myriadquest.kreiscms.service.ContactDetailsService;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;
import com.myriadquest.kreiscms.service.mapper.ContactDetailsMapper;
import com.myriadquest.kreiscms.service.dto.ContactDetailsCriteria;
import com.myriadquest.kreiscms.service.ContactDetailsQueryService;

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

    private static final String DEFAULT_TENANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_TENANT_ID = "BBBBBBBBBB";

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private ContactDetailsMapper contactDetailsMapper;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @Autowired
    private ContactDetailsQueryService contactDetailsQueryService;

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
            .mapLink(DEFAULT_MAP_LINK)
            .tenantId(DEFAULT_TENANT_ID);
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
            .mapLink(UPDATED_MAP_LINK)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testContactDetails.getTenantId()).isEqualTo(DEFAULT_TENANT_ID);
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
            .andExpect(jsonPath("$.[*].mapLink").value(hasItem(DEFAULT_MAP_LINK.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));
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
            .andExpect(jsonPath("$.mapLink").value(DEFAULT_MAP_LINK.toString()))
            .andExpect(jsonPath("$.tenantId").value(DEFAULT_TENANT_ID));
    }


    @Test
    @Transactional
    public void getContactDetailsByIdFiltering() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        Long id = contactDetails.getId();

        defaultContactDetailsShouldBeFound("id.equals=" + id);
        defaultContactDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultContactDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultContactDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContactDetailsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address equals to DEFAULT_ADDRESS
        defaultContactDetailsShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the contactDetailsList where address equals to UPDATED_ADDRESS
        defaultContactDetailsShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address not equals to DEFAULT_ADDRESS
        defaultContactDetailsShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the contactDetailsList where address not equals to UPDATED_ADDRESS
        defaultContactDetailsShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultContactDetailsShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the contactDetailsList where address equals to UPDATED_ADDRESS
        defaultContactDetailsShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address is not null
        defaultContactDetailsShouldBeFound("address.specified=true");

        // Get all the contactDetailsList where address is null
        defaultContactDetailsShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactDetailsByAddressContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address contains DEFAULT_ADDRESS
        defaultContactDetailsShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the contactDetailsList where address contains UPDATED_ADDRESS
        defaultContactDetailsShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where address does not contain DEFAULT_ADDRESS
        defaultContactDetailsShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the contactDetailsList where address does not contain UPDATED_ADDRESS
        defaultContactDetailsShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllContactDetailsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email equals to DEFAULT_EMAIL
        defaultContactDetailsShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contactDetailsList where email equals to UPDATED_EMAIL
        defaultContactDetailsShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email not equals to DEFAULT_EMAIL
        defaultContactDetailsShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the contactDetailsList where email not equals to UPDATED_EMAIL
        defaultContactDetailsShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContactDetailsShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contactDetailsList where email equals to UPDATED_EMAIL
        defaultContactDetailsShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email is not null
        defaultContactDetailsShouldBeFound("email.specified=true");

        // Get all the contactDetailsList where email is null
        defaultContactDetailsShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactDetailsByEmailContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email contains DEFAULT_EMAIL
        defaultContactDetailsShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contactDetailsList where email contains UPDATED_EMAIL
        defaultContactDetailsShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where email does not contain DEFAULT_EMAIL
        defaultContactDetailsShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contactDetailsList where email does not contain UPDATED_EMAIL
        defaultContactDetailsShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllContactDetailsByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact equals to DEFAULT_CONTACT
        defaultContactDetailsShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the contactDetailsList where contact equals to UPDATED_CONTACT
        defaultContactDetailsShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact not equals to DEFAULT_CONTACT
        defaultContactDetailsShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the contactDetailsList where contact not equals to UPDATED_CONTACT
        defaultContactDetailsShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByContactIsInShouldWork() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultContactDetailsShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the contactDetailsList where contact equals to UPDATED_CONTACT
        defaultContactDetailsShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact is not null
        defaultContactDetailsShouldBeFound("contact.specified=true");

        // Get all the contactDetailsList where contact is null
        defaultContactDetailsShouldNotBeFound("contact.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactDetailsByContactContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact contains DEFAULT_CONTACT
        defaultContactDetailsShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the contactDetailsList where contact contains UPDATED_CONTACT
        defaultContactDetailsShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByContactNotContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where contact does not contain DEFAULT_CONTACT
        defaultContactDetailsShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the contactDetailsList where contact does not contain UPDATED_CONTACT
        defaultContactDetailsShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }


    @Test
    @Transactional
    public void getAllContactDetailsByTenantIdIsEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId equals to DEFAULT_TENANT_ID
        defaultContactDetailsShouldBeFound("tenantId.equals=" + DEFAULT_TENANT_ID);

        // Get all the contactDetailsList where tenantId equals to UPDATED_TENANT_ID
        defaultContactDetailsShouldNotBeFound("tenantId.equals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByTenantIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId not equals to DEFAULT_TENANT_ID
        defaultContactDetailsShouldNotBeFound("tenantId.notEquals=" + DEFAULT_TENANT_ID);

        // Get all the contactDetailsList where tenantId not equals to UPDATED_TENANT_ID
        defaultContactDetailsShouldBeFound("tenantId.notEquals=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByTenantIdIsInShouldWork() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId in DEFAULT_TENANT_ID or UPDATED_TENANT_ID
        defaultContactDetailsShouldBeFound("tenantId.in=" + DEFAULT_TENANT_ID + "," + UPDATED_TENANT_ID);

        // Get all the contactDetailsList where tenantId equals to UPDATED_TENANT_ID
        defaultContactDetailsShouldNotBeFound("tenantId.in=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByTenantIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId is not null
        defaultContactDetailsShouldBeFound("tenantId.specified=true");

        // Get all the contactDetailsList where tenantId is null
        defaultContactDetailsShouldNotBeFound("tenantId.specified=false");
    }
                @Test
    @Transactional
    public void getAllContactDetailsByTenantIdContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId contains DEFAULT_TENANT_ID
        defaultContactDetailsShouldBeFound("tenantId.contains=" + DEFAULT_TENANT_ID);

        // Get all the contactDetailsList where tenantId contains UPDATED_TENANT_ID
        defaultContactDetailsShouldNotBeFound("tenantId.contains=" + UPDATED_TENANT_ID);
    }

    @Test
    @Transactional
    public void getAllContactDetailsByTenantIdNotContainsSomething() throws Exception {
        // Initialize the database
        contactDetailsRepository.saveAndFlush(contactDetails);

        // Get all the contactDetailsList where tenantId does not contain DEFAULT_TENANT_ID
        defaultContactDetailsShouldNotBeFound("tenantId.doesNotContain=" + DEFAULT_TENANT_ID);

        // Get all the contactDetailsList where tenantId does not contain UPDATED_TENANT_ID
        defaultContactDetailsShouldBeFound("tenantId.doesNotContain=" + UPDATED_TENANT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactDetailsShouldBeFound(String filter) throws Exception {
        restContactDetailsMockMvc.perform(get("/api/contact-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].mapLink").value(hasItem(DEFAULT_MAP_LINK.toString())))
            .andExpect(jsonPath("$.[*].tenantId").value(hasItem(DEFAULT_TENANT_ID)));

        // Check, that the count call also returns 1
        restContactDetailsMockMvc.perform(get("/api/contact-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactDetailsShouldNotBeFound(String filter) throws Exception {
        restContactDetailsMockMvc.perform(get("/api/contact-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactDetailsMockMvc.perform(get("/api/contact-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .mapLink(UPDATED_MAP_LINK)
            .tenantId(UPDATED_TENANT_ID);
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
        assertThat(testContactDetails.getTenantId()).isEqualTo(UPDATED_TENANT_ID);
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
