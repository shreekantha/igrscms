package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class ExamTimeTableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamTimeTable.class);
        ExamTimeTable examTimeTable1 = new ExamTimeTable();
        examTimeTable1.setId(1L);
        ExamTimeTable examTimeTable2 = new ExamTimeTable();
        examTimeTable2.setId(examTimeTable1.getId());
        assertThat(examTimeTable1).isEqualTo(examTimeTable2);
        examTimeTable2.setId(2L);
        assertThat(examTimeTable1).isNotEqualTo(examTimeTable2);
        examTimeTable1.setId(null);
        assertThat(examTimeTable1).isNotEqualTo(examTimeTable2);
    }
}
