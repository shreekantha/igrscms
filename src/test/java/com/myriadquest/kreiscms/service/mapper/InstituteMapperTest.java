package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InstituteMapperTest {

    private InstituteMapper instituteMapper;

    @BeforeEach
    public void setUp() {
        instituteMapper = new InstituteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(instituteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(instituteMapper.fromId(null)).isNull();
    }
}
