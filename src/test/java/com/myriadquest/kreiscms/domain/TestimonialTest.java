package com.myriadquest.kreiscms.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.myriadquest.kreiscms.web.rest.TestUtil;

public class TestimonialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Testimonial.class);
        Testimonial testimonial1 = new Testimonial();
        testimonial1.setId(1L);
        Testimonial testimonial2 = new Testimonial();
        testimonial2.setId(testimonial1.getId());
        assertThat(testimonial1).isEqualTo(testimonial2);
        testimonial2.setId(2L);
        assertThat(testimonial1).isNotEqualTo(testimonial2);
        testimonial1.setId(null);
        assertThat(testimonial1).isNotEqualTo(testimonial2);
    }
}
