package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VisionAndMissionMapperTest {

    private VisionAndMissionMapper visionAndMissionMapper;

    @BeforeEach
    public void setUp() {
        visionAndMissionMapper = new VisionAndMissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(visionAndMissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(visionAndMissionMapper.fromId(null)).isNull();
    }
}
