package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.ExamTimeTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExamTimeTable} and its DTO {@link ExamTimeTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {AcademicCalendarMapper.class, DegreeMapper.class, DepartmentMapper.class, TermMapper.class, CourseMapper.class})
public interface ExamTimeTableMapper extends EntityMapper<ExamTimeTableDTO, ExamTimeTable> {

    @Mapping(source = "academicCalendar.id", target = "academicCalendarId")
    @Mapping(source = "academicCalendar.academicYear", target = "academicCalendarAcademicYear")
    @Mapping(source = "degree.id", target = "degreeId")
    @Mapping(source = "degree.name", target = "degreeName")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "term.id", target = "termId")
    @Mapping(source = "term.title", target = "termTitle")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    ExamTimeTableDTO toDto(ExamTimeTable examTimeTable);

    @Mapping(source = "academicCalendarId", target = "academicCalendar")
    @Mapping(source = "degreeId", target = "degree")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "termId", target = "term")
    @Mapping(source = "courseId", target = "course")
    ExamTimeTable toEntity(ExamTimeTableDTO examTimeTableDTO);

    default ExamTimeTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamTimeTable examTimeTable = new ExamTimeTable();
        examTimeTable.setId(id);
        return examTimeTable;
    }
}
