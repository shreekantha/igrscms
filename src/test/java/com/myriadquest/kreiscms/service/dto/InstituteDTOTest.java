package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class InstituteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituteDTO.class);
        InstituteDTO instituteDTO1 = new InstituteDTO();
        instituteDTO1.setId(1L);
        InstituteDTO instituteDTO2 = new InstituteDTO();
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO2.setId(instituteDTO1.getId());
        assertThat(instituteDTO1).isEqualTo(instituteDTO2);
        instituteDTO2.setId(2L);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
        instituteDTO1.setId(null);
        assertThat(instituteDTO1).isNotEqualTo(instituteDTO2);
    }
}
