package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.AlumniProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AlumniProfile} and its DTO {@link AlumniProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlumniProfileMapper extends EntityMapper<AlumniProfileDTO, AlumniProfile> {



    default AlumniProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        AlumniProfile alumniProfile = new AlumniProfile();
        alumniProfile.setId(id);
        return alumniProfile;
    }
}
