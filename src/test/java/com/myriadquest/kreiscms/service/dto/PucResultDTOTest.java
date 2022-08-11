package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class PucResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PucResultDTO.class);
        PucResultDTO pucResultDTO1 = new PucResultDTO();
        pucResultDTO1.setId(1L);
        PucResultDTO pucResultDTO2 = new PucResultDTO();
        assertThat(pucResultDTO1).isNotEqualTo(pucResultDTO2);
        pucResultDTO2.setId(pucResultDTO1.getId());
        assertThat(pucResultDTO1).isEqualTo(pucResultDTO2);
        pucResultDTO2.setId(2L);
        assertThat(pucResultDTO1).isNotEqualTo(pucResultDTO2);
        pucResultDTO1.setId(null);
        assertThat(pucResultDTO1).isNotEqualTo(pucResultDTO2);
    }
}
