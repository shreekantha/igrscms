package com.myriadquest.kreiscms.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NoticeBoardMapperTest {

    private NoticeBoardMapper noticeBoardMapper;

    @BeforeEach
    public void setUp() {
        noticeBoardMapper = new NoticeBoardMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(noticeBoardMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(noticeBoardMapper.fromId(null)).isNull();
    }
}
