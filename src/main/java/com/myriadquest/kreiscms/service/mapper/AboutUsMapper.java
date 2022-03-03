package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.AboutUsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AboutUs} and its DTO {@link AboutUsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AboutUsMapper extends EntityMapper<AboutUsDTO, AboutUs> {



    default AboutUs fromId(Long id) {
        if (id == null) {
            return null;
        }
        AboutUs aboutUs = new AboutUs();
        aboutUs.setId(id);
        return aboutUs;
    }
}
