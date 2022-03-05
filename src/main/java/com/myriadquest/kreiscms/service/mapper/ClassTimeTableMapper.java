package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.ClassTimeTableDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassTimeTable} and its DTO {@link ClassTimeTableDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AcademicCalendarMapper.class, DegreeMapper.class, DepartmentMapper.class, TermMapper.class, CourseMapper.class, PeriodMapper.class})
public interface ClassTimeTableMapper extends EntityMapper<ClassTimeTableDTO, ClassTimeTable> {

    @Mapping(source = "faculty.id", target = "facultyId")
    @Mapping(source = "faculty.firstName", target = "facultyFirstName")
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
    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "period.label", target = "periodLabel")
    ClassTimeTableDTO toDto(ClassTimeTable classTimeTable);

    @Mapping(source = "facultyId", target = "faculty")
    @Mapping(source = "academicCalendarId", target = "academicCalendar")
    @Mapping(source = "degreeId", target = "degree")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "termId", target = "term")
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "periodId", target = "period")
    ClassTimeTable toEntity(ClassTimeTableDTO classTimeTableDTO);

    default ClassTimeTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassTimeTable classTimeTable = new ClassTimeTable();
        classTimeTable.setId(id);
        return classTimeTable;
    }
}
