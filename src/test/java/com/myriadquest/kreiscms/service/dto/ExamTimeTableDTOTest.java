package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ExamTimeTableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamTimeTableDTO.class);
        ExamTimeTableDTO examTimeTableDTO1 = new ExamTimeTableDTO();
        examTimeTableDTO1.setId(1L);
        ExamTimeTableDTO examTimeTableDTO2 = new ExamTimeTableDTO();
        assertThat(examTimeTableDTO1).isNotEqualTo(examTimeTableDTO2);
        examTimeTableDTO2.setId(examTimeTableDTO1.getId());
        assertThat(examTimeTableDTO1).isEqualTo(examTimeTableDTO2);
        examTimeTableDTO2.setId(2L);
        assertThat(examTimeTableDTO1).isNotEqualTo(examTimeTableDTO2);
        examTimeTableDTO1.setId(null);
        assertThat(examTimeTableDTO1).isNotEqualTo(examTimeTableDTO2);
    }
}
