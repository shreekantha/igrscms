package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class NoticeBoardTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeBoard.class);
        NoticeBoard noticeBoard1 = new NoticeBoard();
        noticeBoard1.setId(1L);
        NoticeBoard noticeBoard2 = new NoticeBoard();
        noticeBoard2.setId(noticeBoard1.getId());
        assertThat(noticeBoard1).isEqualTo(noticeBoard2);
        noticeBoard2.setId(2L);
        assertThat(noticeBoard1).isNotEqualTo(noticeBoard2);
        noticeBoard1.setId(null);
        assertThat(noticeBoard1).isNotEqualTo(noticeBoard2);
    }
}
