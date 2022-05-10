package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.StudentProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentProfile} and its DTO {@link StudentProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentProfileMapper extends EntityMapper<StudentProfileDTO, StudentProfile> {



    default StudentProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setId(id);
        return studentProfile;
    }
}
