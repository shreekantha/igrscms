package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class GalleryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalleryDTO.class);
        GalleryDTO galleryDTO1 = new GalleryDTO();
        galleryDTO1.setId(1L);
        GalleryDTO galleryDTO2 = new GalleryDTO();
        assertThat(galleryDTO1).isNotEqualTo(galleryDTO2);
        galleryDTO2.setId(galleryDTO1.getId());
        assertThat(galleryDTO1).isEqualTo(galleryDTO2);
        galleryDTO2.setId(2L);
        assertThat(galleryDTO1).isNotEqualTo(galleryDTO2);
        galleryDTO1.setId(null);
        assertThat(galleryDTO1).isNotEqualTo(galleryDTO2);
    }
}
