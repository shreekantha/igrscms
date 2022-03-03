package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.SpeakerDeskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SpeakerDesk} and its DTO {@link SpeakerDeskDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpeakerDeskMapper extends EntityMapper<SpeakerDeskDTO, SpeakerDesk> {



    default SpeakerDesk fromId(Long id) {
        if (id == null) {
            return null;
        }
        SpeakerDesk speakerDesk = new SpeakerDesk();
        speakerDesk.setId(id);
        return speakerDesk;
    }
}
