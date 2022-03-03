package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MissionMapperTest {

    private MissionMapper missionMapper;

    @BeforeEach
    public void setUp() {
        missionMapper = new MissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(missionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(missionMapper.fromId(null)).isNull();
    }
}
