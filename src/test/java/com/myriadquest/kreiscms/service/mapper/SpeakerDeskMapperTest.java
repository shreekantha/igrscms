package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SpeakerDeskMapperTest {

    private SpeakerDeskMapper speakerDeskMapper;

    @BeforeEach
    public void setUp() {
        speakerDeskMapper = new SpeakerDeskMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(speakerDeskMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(speakerDeskMapper.fromId(null)).isNull();
    }
}
