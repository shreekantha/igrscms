package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StudentProfileMapperTest {

    private StudentProfileMapper studentProfileMapper;

    @BeforeEach
    public void setUp() {
        studentProfileMapper = new StudentProfileMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studentProfileMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studentProfileMapper.fromId(null)).isNull();
    }
}
