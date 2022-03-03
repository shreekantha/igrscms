package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ContactDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactDetailsDTO.class);
        ContactDetailsDTO contactDetailsDTO1 = new ContactDetailsDTO();
        contactDetailsDTO1.setId(1L);
        ContactDetailsDTO contactDetailsDTO2 = new ContactDetailsDTO();
        assertThat(contactDetailsDTO1).isNotEqualTo(contactDetailsDTO2);
        contactDetailsDTO2.setId(contactDetailsDTO1.getId());
        assertThat(contactDetailsDTO1).isEqualTo(contactDetailsDTO2);
        contactDetailsDTO2.setId(2L);
        assertThat(contactDetailsDTO1).isNotEqualTo(contactDetailsDTO2);
        contactDetailsDTO1.setId(null);
        assertThat(contactDetailsDTO1).isNotEqualTo(contactDetailsDTO2);
    }
}
