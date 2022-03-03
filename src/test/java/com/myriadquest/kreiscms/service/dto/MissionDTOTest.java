package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class MissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MissionDTO.class);
        MissionDTO missionDTO1 = new MissionDTO();
        missionDTO1.setId(1L);
        MissionDTO missionDTO2 = new MissionDTO();
        assertThat(missionDTO1).isNotEqualTo(missionDTO2);
        missionDTO2.setId(missionDTO1.getId());
        assertThat(missionDTO1).isEqualTo(missionDTO2);
        missionDTO2.setId(2L);
        assertThat(missionDTO1).isNotEqualTo(missionDTO2);
        missionDTO1.setId(null);
        assertThat(missionDTO1).isNotEqualTo(missionDTO2);
    }
}
