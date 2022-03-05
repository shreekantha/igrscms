package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExamTimeTableMapperTest {

    private ExamTimeTableMapper examTimeTableMapper;

    @BeforeEach
    public void setUp() {
        examTimeTableMapper = new ExamTimeTableMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(examTimeTableMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(examTimeTableMapper.fromId(null)).isNull();
    }
}
