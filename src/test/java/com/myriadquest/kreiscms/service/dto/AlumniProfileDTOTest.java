package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class AlumniProfileDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlumniProfileDTO.class);
        AlumniProfileDTO alumniProfileDTO1 = new AlumniProfileDTO();
        alumniProfileDTO1.setId(1L);
        AlumniProfileDTO alumniProfileDTO2 = new AlumniProfileDTO();
        assertThat(alumniProfileDTO1).isNotEqualTo(alumniProfileDTO2);
        alumniProfileDTO2.setId(alumniProfileDTO1.getId());
        assertThat(alumniProfileDTO1).isEqualTo(alumniProfileDTO2);
        alumniProfileDTO2.setId(2L);
        assertThat(alumniProfileDTO1).isNotEqualTo(alumniProfileDTO2);
        alumniProfileDTO1.setId(null);
        assertThat(alumniProfileDTO1).isNotEqualTo(alumniProfileDTO2);
    }
}
