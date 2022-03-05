package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.SchemeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scheme} and its DTO {@link SchemeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchemeMapper extends EntityMapper<SchemeDTO, Scheme> {



    default Scheme fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scheme scheme = new Scheme();
        scheme.setId(id);
        return scheme;
    }
}
