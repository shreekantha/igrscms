package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class GalleryCatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalleryCatDTO.class);
        GalleryCatDTO galleryCatDTO1 = new GalleryCatDTO();
        galleryCatDTO1.setId(1L);
        GalleryCatDTO galleryCatDTO2 = new GalleryCatDTO();
        assertThat(galleryCatDTO1).isNotEqualTo(galleryCatDTO2);
        galleryCatDTO2.setId(galleryCatDTO1.getId());
        assertThat(galleryCatDTO1).isEqualTo(galleryCatDTO2);
        galleryCatDTO2.setId(2L);
        assertThat(galleryCatDTO1).isNotEqualTo(galleryCatDTO2);
        galleryCatDTO1.setId(null);
        assertThat(galleryCatDTO1).isNotEqualTo(galleryCatDTO2);
    }
}
