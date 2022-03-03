package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class VisionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vision.class);
        Vision vision1 = new Vision();
        vision1.setId(1L);
        Vision vision2 = new Vision();
        vision2.setId(vision1.getId());
        assertThat(vision1).isEqualTo(vision2);
        vision2.setId(2L);
        assertThat(vision1).isNotEqualTo(vision2);
        vision1.setId(null);
        assertThat(vision1).isNotEqualTo(vision2);
    }
}
