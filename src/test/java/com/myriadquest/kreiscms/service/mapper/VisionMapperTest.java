package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VisionMapperTest {

    private VisionMapper visionMapper;

    @BeforeEach
    public void setUp() {
        visionMapper = new VisionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(visionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(visionMapper.fromId(null)).isNull();
    }
}
