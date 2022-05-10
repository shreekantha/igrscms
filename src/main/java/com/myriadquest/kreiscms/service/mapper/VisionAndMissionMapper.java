package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.VisionAndMissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link VisionAndMission} and its DTO {@link VisionAndMissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VisionAndMissionMapper extends EntityMapper<VisionAndMissionDTO, VisionAndMission> {



    default VisionAndMission fromId(Long id) {
        if (id == null) {
            return null;
        }
        VisionAndMission visionAndMission = new VisionAndMission();
        visionAndMission.setId(id);
        return visionAndMission;
    }
}
