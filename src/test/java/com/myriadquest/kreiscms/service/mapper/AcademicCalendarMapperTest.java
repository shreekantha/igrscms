package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AcademicCalendarMapperTest {

    private AcademicCalendarMapper academicCalendarMapper;

    @BeforeEach
    public void setUp() {
        academicCalendarMapper = new AcademicCalendarMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(academicCalendarMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(academicCalendarMapper.fromId(null)).isNull();
    }
}
