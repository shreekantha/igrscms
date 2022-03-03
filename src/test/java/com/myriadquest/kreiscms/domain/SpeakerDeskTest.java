package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class SpeakerDeskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeakerDesk.class);
        SpeakerDesk speakerDesk1 = new SpeakerDesk();
        speakerDesk1.setId(1L);
        SpeakerDesk speakerDesk2 = new SpeakerDesk();
        speakerDesk2.setId(speakerDesk1.getId());
        assertThat(speakerDesk1).isEqualTo(speakerDesk2);
        speakerDesk2.setId(2L);
        assertThat(speakerDesk1).isNotEqualTo(speakerDesk2);
        speakerDesk1.setId(null);
        assertThat(speakerDesk1).isNotEqualTo(speakerDesk2);
    }
}
