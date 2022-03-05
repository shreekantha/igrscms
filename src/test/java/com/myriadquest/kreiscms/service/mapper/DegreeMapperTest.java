package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DegreeMapperTest {

    private DegreeMapper degreeMapper;

    @BeforeEach
    public void setUp() {
        degreeMapper = new DegreeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(degreeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(degreeMapper.fromId(null)).isNull();
    }
}
