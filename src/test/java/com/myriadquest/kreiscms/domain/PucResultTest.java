package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class PucResultTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PucResult.class);
        PucResult pucResult1 = new PucResult();
        pucResult1.setId(1L);
        PucResult pucResult2 = new PucResult();
        pucResult2.setId(pucResult1.getId());
        assertThat(pucResult1).isEqualTo(pucResult2);
        pucResult2.setId(2L);
        assertThat(pucResult1).isNotEqualTo(pucResult2);
        pucResult1.setId(null);
        assertThat(pucResult1).isNotEqualTo(pucResult2);
    }
}
