package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ClassTimeTableConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTimeTableConfigDTO.class);
        ClassTimeTableConfigDTO classTimeTableConfigDTO1 = new ClassTimeTableConfigDTO();
        classTimeTableConfigDTO1.setId(1L);
        ClassTimeTableConfigDTO classTimeTableConfigDTO2 = new ClassTimeTableConfigDTO();
        assertThat(classTimeTableConfigDTO1).isNotEqualTo(classTimeTableConfigDTO2);
        classTimeTableConfigDTO2.setId(classTimeTableConfigDTO1.getId());
        assertThat(classTimeTableConfigDTO1).isEqualTo(classTimeTableConfigDTO2);
        classTimeTableConfigDTO2.setId(2L);
        assertThat(classTimeTableConfigDTO1).isNotEqualTo(classTimeTableConfigDTO2);
        classTimeTableConfigDTO1.setId(null);
        assertThat(classTimeTableConfigDTO1).isNotEqualTo(classTimeTableConfigDTO2);
    }
}
