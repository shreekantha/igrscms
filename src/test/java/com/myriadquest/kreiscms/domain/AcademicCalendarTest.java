package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class AcademicCalendarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicCalendar.class);
        AcademicCalendar academicCalendar1 = new AcademicCalendar();
        academicCalendar1.setId(1L);
        AcademicCalendar academicCalendar2 = new AcademicCalendar();
        academicCalendar2.setId(academicCalendar1.getId());
        assertThat(academicCalendar1).isEqualTo(academicCalendar2);
        academicCalendar2.setId(2L);
        assertThat(academicCalendar1).isNotEqualTo(academicCalendar2);
        academicCalendar1.setId(null);
        assertThat(academicCalendar1).isNotEqualTo(academicCalendar2);
    }
}
