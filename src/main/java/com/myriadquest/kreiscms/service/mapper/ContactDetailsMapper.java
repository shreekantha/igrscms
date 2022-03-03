package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.ContactDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactDetails} and its DTO {@link ContactDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactDetailsMapper extends EntityMapper<ContactDetailsDTO, ContactDetails> {



    default ContactDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setId(id);
        return contactDetails;
    }
}
