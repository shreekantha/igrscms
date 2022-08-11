package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PucResultMapperTest {

    private PucResultMapper pucResultMapper;

    @BeforeEach
    public void setUp() {
        pucResultMapper = new PucResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(pucResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(pucResultMapper.fromId(null)).isNull();
    }
}
