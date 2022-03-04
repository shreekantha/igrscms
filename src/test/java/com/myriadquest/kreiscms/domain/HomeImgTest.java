package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class HomeImgTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HomeImg.class);
        HomeImg homeImg1 = new HomeImg();
        homeImg1.setId(1L);
        HomeImg homeImg2 = new HomeImg();
        homeImg2.setId(homeImg1.getId());
        assertThat(homeImg1).isEqualTo(homeImg2);
        homeImg2.setId(2L);
        assertThat(homeImg1).isNotEqualTo(homeImg2);
        homeImg1.setId(null);
        assertThat(homeImg1).isNotEqualTo(homeImg2);
    }
}
