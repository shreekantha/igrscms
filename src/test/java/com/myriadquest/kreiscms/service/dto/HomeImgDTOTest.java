package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class HomeImgDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HomeImgDTO.class);
        HomeImgDTO homeImgDTO1 = new HomeImgDTO();
        homeImgDTO1.setId(1L);
        HomeImgDTO homeImgDTO2 = new HomeImgDTO();
        assertThat(homeImgDTO1).isNotEqualTo(homeImgDTO2);
        homeImgDTO2.setId(homeImgDTO1.getId());
        assertThat(homeImgDTO1).isEqualTo(homeImgDTO2);
        homeImgDTO2.setId(2L);
        assertThat(homeImgDTO1).isNotEqualTo(homeImgDTO2);
        homeImgDTO1.setId(null);
        assertThat(homeImgDTO1).isNotEqualTo(homeImgDTO2);
    }
}
