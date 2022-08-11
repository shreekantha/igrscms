package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class SslcResultDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SslcResultDTO.class);
        SslcResultDTO sslcResultDTO1 = new SslcResultDTO();
        sslcResultDTO1.setId(1L);
        SslcResultDTO sslcResultDTO2 = new SslcResultDTO();
        assertThat(sslcResultDTO1).isNotEqualTo(sslcResultDTO2);
        sslcResultDTO2.setId(sslcResultDTO1.getId());
        assertThat(sslcResultDTO1).isEqualTo(sslcResultDTO2);
        sslcResultDTO2.setId(2L);
        assertThat(sslcResultDTO1).isNotEqualTo(sslcResultDTO2);
        sslcResultDTO1.setId(null);
        assertThat(sslcResultDTO1).isNotEqualTo(sslcResultDTO2);
    }
}
