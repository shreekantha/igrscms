package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.PeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Period} and its DTO {@link PeriodDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeriodMapper extends EntityMapper<PeriodDTO, Period> {


    @Mapping(target = "classTimeTables", ignore = true)
    @Mapping(target = "removeClassTimeTable", ignore = true)
    Period toEntity(PeriodDTO periodDTO);

    default Period fromId(Long id) {
        if (id == null) {
            return null;
        }
        Period period = new Period();
        period.setId(id);
        return period;
    }
}
