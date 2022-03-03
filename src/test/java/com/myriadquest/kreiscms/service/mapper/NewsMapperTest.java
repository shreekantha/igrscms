package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NewsMapperTest {

    private NewsMapper newsMapper;

    @BeforeEach
    public void setUp() {
        newsMapper = new NewsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(newsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(newsMapper.fromId(null)).isNull();
    }
}
