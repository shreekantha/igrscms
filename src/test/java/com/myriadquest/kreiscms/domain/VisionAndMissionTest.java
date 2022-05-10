package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class VisionAndMissionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisionAndMission.class);
        VisionAndMission visionAndMission1 = new VisionAndMission();
        visionAndMission1.setId(1L);
        VisionAndMission visionAndMission2 = new VisionAndMission();
        visionAndMission2.setId(visionAndMission1.getId());
        assertThat(visionAndMission1).isEqualTo(visionAndMission2);
        visionAndMission2.setId(2L);
        assertThat(visionAndMission1).isNotEqualTo(visionAndMission2);
        visionAndMission1.setId(null);
        assertThat(visionAndMission1).isNotEqualTo(visionAndMission2);
    }
}
