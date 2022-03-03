package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.InstituteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Institute} and its DTO {@link InstituteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstituteMapper extends EntityMapper<InstituteDTO, Institute> {



    default Institute fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institute institute = new Institute();
        institute.setId(id);
        return institute;
    }
}
