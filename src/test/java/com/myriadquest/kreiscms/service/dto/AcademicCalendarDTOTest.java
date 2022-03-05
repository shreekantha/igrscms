package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class AcademicCalendarDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicCalendarDTO.class);
        AcademicCalendarDTO academicCalendarDTO1 = new AcademicCalendarDTO();
        academicCalendarDTO1.setId(1L);
        AcademicCalendarDTO academicCalendarDTO2 = new AcademicCalendarDTO();
        assertThat(academicCalendarDTO1).isNotEqualTo(academicCalendarDTO2);
        academicCalendarDTO2.setId(academicCalendarDTO1.getId());
        assertThat(academicCalendarDTO1).isEqualTo(academicCalendarDTO2);
        academicCalendarDTO2.setId(2L);
        assertThat(academicCalendarDTO1).isNotEqualTo(academicCalendarDTO2);
        academicCalendarDTO1.setId(null);
        assertThat(academicCalendarDTO1).isNotEqualTo(academicCalendarDTO2);
    }
}
