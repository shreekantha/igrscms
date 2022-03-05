package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.DegreeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Degree} and its DTO {@link DegreeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DegreeMapper extends EntityMapper<DegreeDTO, Degree> {


    @Mapping(target = "departments", ignore = true)
    @Mapping(target = "removeDepartment", ignore = true)
    @Mapping(target = "academicCalendars", ignore = true)
    @Mapping(target = "removeAcademicCalendar", ignore = true)
    @Mapping(target = "classTimeTables", ignore = true)
    @Mapping(target = "removeClassTimeTable", ignore = true)
    @Mapping(target = "examTimeTables", ignore = true)
    @Mapping(target = "removeExamTimeTable", ignore = true)
    Degree toEntity(DegreeDTO degreeDTO);

    default Degree fromId(Long id) {
        if (id == null) {
            return null;
        }
        Degree degree = new Degree();
        degree.setId(id);
        return degree;
    }
}
