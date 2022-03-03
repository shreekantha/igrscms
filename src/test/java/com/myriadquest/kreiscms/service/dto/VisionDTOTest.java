package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class VisionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisionDTO.class);
        VisionDTO visionDTO1 = new VisionDTO();
        visionDTO1.setId(1L);
        VisionDTO visionDTO2 = new VisionDTO();
        assertThat(visionDTO1).isNotEqualTo(visionDTO2);
        visionDTO2.setId(visionDTO1.getId());
        assertThat(visionDTO1).isEqualTo(visionDTO2);
        visionDTO2.setId(2L);
        assertThat(visionDTO1).isNotEqualTo(visionDTO2);
        visionDTO1.setId(null);
        assertThat(visionDTO1).isNotEqualTo(visionDTO2);
    }
}
