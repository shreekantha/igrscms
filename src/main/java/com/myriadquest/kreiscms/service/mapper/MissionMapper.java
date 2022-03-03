package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.MissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mission} and its DTO {@link MissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MissionMapper extends EntityMapper<MissionDTO, Mission> {



    default Mission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mission mission = new Mission();
        mission.setId(id);
        return mission;
    }
}
