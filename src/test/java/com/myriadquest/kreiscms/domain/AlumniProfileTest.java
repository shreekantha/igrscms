package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class AlumniProfileTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlumniProfile.class);
        AlumniProfile alumniProfile1 = new AlumniProfile();
        alumniProfile1.setId(1L);
        AlumniProfile alumniProfile2 = new AlumniProfile();
        alumniProfile2.setId(alumniProfile1.getId());
        assertThat(alumniProfile1).isEqualTo(alumniProfile2);
        alumniProfile2.setId(2L);
        assertThat(alumniProfile1).isNotEqualTo(alumniProfile2);
        alumniProfile1.setId(null);
        assertThat(alumniProfile1).isNotEqualTo(alumniProfile2);
    }
}
