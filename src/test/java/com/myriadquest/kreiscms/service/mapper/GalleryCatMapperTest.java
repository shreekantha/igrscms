package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GalleryCatMapperTest {

    private GalleryCatMapper galleryCatMapper;

    @BeforeEach
    public void setUp() {
        galleryCatMapper = new GalleryCatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(galleryCatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(galleryCatMapper.fromId(null)).isNull();
    }
}
