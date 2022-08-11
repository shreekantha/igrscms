package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.PucResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PucResult} and its DTO {@link PucResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PucResultMapper extends EntityMapper<PucResultDTO, PucResult> {



    default PucResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        PucResult pucResult = new PucResult();
        pucResult.setId(id);
        return pucResult;
    }
}
