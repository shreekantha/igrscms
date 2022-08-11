package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class SslcResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SslcResult.class);
        SslcResult sslcResult1 = new SslcResult();
        sslcResult1.setId(1L);
        SslcResult sslcResult2 = new SslcResult();
        sslcResult2.setId(sslcResult1.getId());
        assertThat(sslcResult1).isEqualTo(sslcResult2);
        sslcResult2.setId(2L);
        assertThat(sslcResult1).isNotEqualTo(sslcResult2);
        sslcResult1.setId(null);
        assertThat(sslcResult1).isNotEqualTo(sslcResult2);
    }
}
