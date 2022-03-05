package com.myriadquest.kreiscms.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ClassTimeTableDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTimeTableDTO.class);
        ClassTimeTableDTO classTimeTableDTO1 = new ClassTimeTableDTO();
        classTimeTableDTO1.setId(1L);
        ClassTimeTableDTO classTimeTableDTO2 = new ClassTimeTableDTO();
        assertThat(classTimeTableDTO1).isNotEqualTo(classTimeTableDTO2);
        classTimeTableDTO2.setId(classTimeTableDTO1.getId());
        assertThat(classTimeTableDTO1).isEqualTo(classTimeTableDTO2);
        classTimeTableDTO2.setId(2L);
        assertThat(classTimeTableDTO1).isNotEqualTo(classTimeTableDTO2);
        classTimeTableDTO1.setId(null);
        assertThat(classTimeTableDTO1).isNotEqualTo(classTimeTableDTO2);
    }
}
