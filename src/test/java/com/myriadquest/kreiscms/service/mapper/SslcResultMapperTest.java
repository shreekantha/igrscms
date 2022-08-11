package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SslcResultMapperTest {

    private SslcResultMapper sslcResultMapper;

    @BeforeEach
    public void setUp() {
        sslcResultMapper = new SslcResultMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sslcResultMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sslcResultMapper.fromId(null)).isNull();
    }
}
