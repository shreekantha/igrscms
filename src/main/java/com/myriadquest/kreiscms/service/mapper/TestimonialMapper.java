package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.TestimonialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Testimonial} and its DTO {@link TestimonialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestimonialMapper extends EntityMapper<TestimonialDTO, Testimonial> {



    default Testimonial fromId(Long id) {
        if (id == null) {
            return null;
        }
        Testimonial testimonial = new Testimonial();
        testimonial.setId(id);
        return testimonial;
    }
}
