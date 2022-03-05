package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ClassTimeTableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassTimeTable.class);
        ClassTimeTable classTimeTable1 = new ClassTimeTable();
        classTimeTable1.setId(1L);
        ClassTimeTable classTimeTable2 = new ClassTimeTable();
        classTimeTable2.setId(classTimeTable1.getId());
        assertThat(classTimeTable1).isEqualTo(classTimeTable2);
        classTimeTable2.setId(2L);
        assertThat(classTimeTable1).isNotEqualTo(classTimeTable2);
        classTimeTable1.setId(null);
        assertThat(classTimeTable1).isNotEqualTo(classTimeTable2);
    }
}
