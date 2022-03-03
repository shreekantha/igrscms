package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class SpeakerDeskDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeakerDeskDTO.class);
        SpeakerDeskDTO speakerDeskDTO1 = new SpeakerDeskDTO();
        speakerDeskDTO1.setId(1L);
        SpeakerDeskDTO speakerDeskDTO2 = new SpeakerDeskDTO();
        assertThat(speakerDeskDTO1).isNotEqualTo(speakerDeskDTO2);
        speakerDeskDTO2.setId(speakerDeskDTO1.getId());
        assertThat(speakerDeskDTO1).isEqualTo(speakerDeskDTO2);
        speakerDeskDTO2.setId(2L);
        assertThat(speakerDeskDTO1).isNotEqualTo(speakerDeskDTO2);
        speakerDeskDTO1.setId(null);
        assertThat(speakerDeskDTO1).isNotEqualTo(speakerDeskDTO2);
    }
}
