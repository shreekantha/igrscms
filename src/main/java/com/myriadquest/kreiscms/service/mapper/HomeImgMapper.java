package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.HomeImgDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link HomeImg} and its DTO {@link HomeImgDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HomeImgMapper extends EntityMapper<HomeImgDTO, HomeImg> {



    default HomeImg fromId(Long id) {
        if (id == null) {
            return null;
        }
        HomeImg homeImg = new HomeImg();
        homeImg.setId(id);
        return homeImg;
    }
}
