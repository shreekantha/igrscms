package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class StudentProfileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentProfileDTO.class);
        StudentProfileDTO studentProfileDTO1 = new StudentProfileDTO();
        studentProfileDTO1.setId(1L);
        StudentProfileDTO studentProfileDTO2 = new StudentProfileDTO();
        assertThat(studentProfileDTO1).isNotEqualTo(studentProfileDTO2);
        studentProfileDTO2.setId(studentProfileDTO1.getId());
        assertThat(studentProfileDTO1).isEqualTo(studentProfileDTO2);
        studentProfileDTO2.setId(2L);
        assertThat(studentProfileDTO1).isNotEqualTo(studentProfileDTO2);
        studentProfileDTO1.setId(null);
        assertThat(studentProfileDTO1).isNotEqualTo(studentProfileDTO2);
    }
}
