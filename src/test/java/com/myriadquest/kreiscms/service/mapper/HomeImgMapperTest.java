package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HomeImgMapperTest {

    private HomeImgMapper homeImgMapper;

    @BeforeEach
    public void setUp() {
        homeImgMapper = new HomeImgMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(homeImgMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(homeImgMapper.fromId(null)).isNull();
    }
}
