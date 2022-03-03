package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AboutUsMapperTest {

    private AboutUsMapper aboutUsMapper;

    @BeforeEach
    public void setUp() {
        aboutUsMapper = new AboutUsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(aboutUsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(aboutUsMapper.fromId(null)).isNull();
    }
}
