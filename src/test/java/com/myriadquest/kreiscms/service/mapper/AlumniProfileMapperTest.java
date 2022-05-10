package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AlumniProfileMapperTest {

    private AlumniProfileMapper alumniProfileMapper;

    @BeforeEach
    public void setUp() {
        alumniProfileMapper = new AlumniProfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(alumniProfileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(alumniProfileMapper.fromId(null)).isNull();
    }
}
