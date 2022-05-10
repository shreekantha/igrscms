package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TestimonialMapperTest {

    private TestimonialMapper testimonialMapper;

    @BeforeEach
    public void setUp() {
        testimonialMapper = new TestimonialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(testimonialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(testimonialMapper.fromId(null)).isNull();
    }
}
