package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class GalleryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gallery.class);
        Gallery gallery1 = new Gallery();
        gallery1.setId(1L);
        Gallery gallery2 = new Gallery();
        gallery2.setId(gallery1.getId());
        assertThat(gallery1).isEqualTo(gallery2);
        gallery2.setId(2L);
        assertThat(gallery1).isNotEqualTo(gallery2);
        gallery1.setId(null);
        assertThat(gallery1).isNotEqualTo(gallery2);
    }
}
