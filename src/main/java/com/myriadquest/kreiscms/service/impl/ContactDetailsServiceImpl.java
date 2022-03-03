package com.myriadquest.kreiscms.service.impl;

import com.myriadquest.kreiscms.service.ContactDetailsService;
import com.myriadquest.kreiscms.domain.ContactDetails;
import com.myriadquest.kreiscms.repository.ContactDetailsRepository;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;
import com.myriadquest.kreiscms.service.mapper.ContactDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContactDetails}.
 */
@Service
@Transactional
public class ContactDetailsServiceImpl implements ContactDetailsService {

    private final Logger log = LoggerFactory.getLogger(ContactDetailsServiceImpl.class);

    private final ContactDetailsRepository contactDetailsRepository;

    private final ContactDetailsMapper contactDetailsMapper;

    public ContactDetailsServiceImpl(ContactDetailsRepository contactDetailsRepository, ContactDetailsMapper contactDetailsMapper) {
        this.contactDetailsRepository = contactDetailsRepository;
        this.contactDetailsMapper = contactDetailsMapper;
    }

    @Override
    public ContactDetailsDTO save(ContactDetailsDTO contactDetailsDTO) {
        log.debug("Request to save ContactDetails : {}", contactDetailsDTO);
        ContactDetails contactDetails = contactDetailsMapper.toEntity(contactDetailsDTO);
        contactDetails = contactDetailsRepository.save(contactDetails);
        return contactDetailsMapper.toDto(contactDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactDetails");
        return contactDetailsRepository.findAll(pageable)
            .map(contactDetailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContactDetailsDTO> findOne(Long id) {
        log.debug("Request to get ContactDetails : {}", id);
        return contactDetailsRepository.findById(id)
            .map(contactDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactDetails : {}", id);
        contactDetailsRepository.deleteById(id);
    }
}
