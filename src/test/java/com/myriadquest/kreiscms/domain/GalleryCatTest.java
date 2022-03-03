package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class GalleryCatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalleryCat.class);
        GalleryCat galleryCat1 = new GalleryCat();
        galleryCat1.setId(1L);
        GalleryCat galleryCat2 = new GalleryCat();
        galleryCat2.setId(galleryCat1.getId());
        assertThat(galleryCat1).isEqualTo(galleryCat2);
        galleryCat2.setId(2L);
        assertThat(galleryCat1).isNotEqualTo(galleryCat2);
        galleryCat1.setId(null);
        assertThat(galleryCat1).isNotEqualTo(galleryCat2);
    }
}
