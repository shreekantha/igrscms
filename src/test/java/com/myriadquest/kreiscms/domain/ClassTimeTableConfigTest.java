package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ClassTimeTableConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTimeTableConfig.class);
        ClassTimeTableConfig classTimeTableConfig1 = new ClassTimeTableConfig();
        classTimeTableConfig1.setId(1L);
        ClassTimeTableConfig classTimeTableConfig2 = new ClassTimeTableConfig();
        classTimeTableConfig2.setId(classTimeTableConfig1.getId());
        assertThat(classTimeTableConfig1).isEqualTo(classTimeTableConfig2);
        classTimeTableConfig2.setId(2L);
        assertThat(classTimeTableConfig1).isNotEqualTo(classTimeTableConfig2);
        classTimeTableConfig1.setId(null);
        assertThat(classTimeTableConfig1).isNotEqualTo(classTimeTableConfig2);
    }
}
