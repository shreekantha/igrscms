package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.VisionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vision} and its DTO {@link VisionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VisionMapper extends EntityMapper<VisionDTO, Vision> {



    default Vision fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vision vision = new Vision();
        vision.setId(id);
        return vision;
    }
}
