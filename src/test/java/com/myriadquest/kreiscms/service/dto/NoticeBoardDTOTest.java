package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class NoticeBoardDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeBoardDTO.class);
        NoticeBoardDTO noticeBoardDTO1 = new NoticeBoardDTO();
        noticeBoardDTO1.setId(1L);
        NoticeBoardDTO noticeBoardDTO2 = new NoticeBoardDTO();
        assertThat(noticeBoardDTO1).isNotEqualTo(noticeBoardDTO2);
        noticeBoardDTO2.setId(noticeBoardDTO1.getId());
        assertThat(noticeBoardDTO1).isEqualTo(noticeBoardDTO2);
        noticeBoardDTO2.setId(2L);
        assertThat(noticeBoardDTO1).isNotEqualTo(noticeBoardDTO2);
        noticeBoardDTO1.setId(null);
        assertThat(noticeBoardDTO1).isNotEqualTo(noticeBoardDTO2);
    }
}
