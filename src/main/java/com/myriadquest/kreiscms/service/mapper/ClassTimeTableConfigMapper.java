package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassTimeTableConfig} and its DTO {@link ClassTimeTableConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassTimeTableConfigMapper extends EntityMapper<ClassTimeTableConfigDTO, ClassTimeTableConfig> {



    default ClassTimeTableConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassTimeTableConfig classTimeTableConfig = new ClassTimeTableConfig();
        classTimeTableConfig.setId(id);
        return classTimeTableConfig;
    }
}
