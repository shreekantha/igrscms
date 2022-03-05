package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassTimeTableConfigMapperTest {

    private ClassTimeTableConfigMapper classTimeTableConfigMapper;

    @BeforeEach
    public void setUp() {
        classTimeTableConfigMapper = new ClassTimeTableConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(classTimeTableConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classTimeTableConfigMapper.fromId(null)).isNull();
    }
}
