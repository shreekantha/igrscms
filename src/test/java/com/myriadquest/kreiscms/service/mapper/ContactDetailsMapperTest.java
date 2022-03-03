package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactDetailsMapperTest {

    private ContactDetailsMapper contactDetailsMapper;

    @BeforeEach
    public void setUp() {
        contactDetailsMapper = new ContactDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(contactDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(contactDetailsMapper.fromId(null)).isNull();
    }
}
