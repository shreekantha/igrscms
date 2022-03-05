package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassTimeTableMapperTest {

    private ClassTimeTableMapper classTimeTableMapper;

    @BeforeEach
    public void setUp() {
        classTimeTableMapper = new ClassTimeTableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classTimeTableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classTimeTableMapper.fromId(null)).isNull();
    }
}
