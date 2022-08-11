package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.SslcResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SslcResult} and its DTO {@link SslcResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SslcResultMapper extends EntityMapper<SslcResultDTO, SslcResult> {



    default SslcResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        SslcResult sslcResult = new SslcResult();
        sslcResult.setId(id);
        return sslcResult;
    }
}
