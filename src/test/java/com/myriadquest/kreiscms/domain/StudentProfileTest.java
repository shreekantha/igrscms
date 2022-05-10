package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class StudentProfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentProfile.class);
        StudentProfile studentProfile1 = new StudentProfile();
        studentProfile1.setId(1L);
        StudentProfile studentProfile2 = new StudentProfile();
        studentProfile2.setId(studentProfile1.getId());
        assertThat(studentProfile1).isEqualTo(studentProfile2);
        studentProfile2.setId(2L);
        assertThat(studentProfile1).isNotEqualTo(studentProfile2);
        studentProfile1.setId(null);
        assertThat(studentProfile1).isNotEqualTo(studentProfile2);
    }
}
