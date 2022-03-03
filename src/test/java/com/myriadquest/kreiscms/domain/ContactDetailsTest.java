package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ContactDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactDetails.class);
        ContactDetails contactDetails1 = new ContactDetails();
        contactDetails1.setId(1L);
        ContactDetails contactDetails2 = new ContactDetails();
        contactDetails2.setId(contactDetails1.getId());
        assertThat(contactDetails1).isEqualTo(contactDetails2);
        contactDetails2.setId(2L);
        assertThat(contactDetails1).isNotEqualTo(contactDetails2);
        contactDetails1.setId(null);
        assertThat(contactDetails1).isNotEqualTo(contactDetails2);
    }
}
