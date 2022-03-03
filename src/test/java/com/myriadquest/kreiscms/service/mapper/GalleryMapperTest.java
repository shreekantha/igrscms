package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GalleryMapperTest {

    private GalleryMapper galleryMapper;

    @BeforeEach
    public void setUp() {
        galleryMapper = new GalleryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(galleryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(galleryMapper.fromId(null)).isNull();
    }
}
