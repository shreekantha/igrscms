package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class VisionAndMissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisionAndMissionDTO.class);
        VisionAndMissionDTO visionAndMissionDTO1 = new VisionAndMissionDTO();
        visionAndMissionDTO1.setId(1L);
        VisionAndMissionDTO visionAndMissionDTO2 = new VisionAndMissionDTO();
        assertThat(visionAndMissionDTO1).isNotEqualTo(visionAndMissionDTO2);
        visionAndMissionDTO2.setId(visionAndMissionDTO1.getId());
        assertThat(visionAndMissionDTO1).isEqualTo(visionAndMissionDTO2);
        visionAndMissionDTO2.setId(2L);
        assertThat(visionAndMissionDTO1).isNotEqualTo(visionAndMissionDTO2);
        visionAndMissionDTO1.setId(null);
        assertThat(visionAndMissionDTO1).isNotEqualTo(visionAndMissionDTO2);
    }
}
