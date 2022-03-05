package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.AcademicCalendarDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcademicCalendar} and its DTO {@link AcademicCalendarDTO}.
 */
@Mapper(componentModel = "spring", uses = {DegreeMapper.class})
public interface AcademicCalendarMapper extends EntityMapper<AcademicCalendarDTO, AcademicCalendar> {

    @Mapping(source = "degree.id", target = "degreeId")
    @Mapping(source = "degree.name", target = "degreeName")
    AcademicCalendarDTO toDto(AcademicCalendar academicCalendar);

    @Mapping(target = "classTimeTables", ignore = true)
    @Mapping(target = "removeClassTimeTable", ignore = true)
    @Mapping(target = "examTimeTables", ignore = true)
    @Mapping(target = "removeExamTimeTable", ignore = true)
    @Mapping(source = "degreeId", target = "degree")
    AcademicCalendar toEntity(AcademicCalendarDTO academicCalendarDTO);

    default AcademicCalendar fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicCalendar academicCalendar = new AcademicCalendar();
        academicCalendar.setId(id);
        return academicCalendar;
    }
}
