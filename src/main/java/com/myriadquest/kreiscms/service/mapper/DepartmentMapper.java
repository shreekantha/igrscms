package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {DegreeMapper.class})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {

    @Mapping(source = "degree.id", target = "degreeId")
    @Mapping(source = "degree.name", target = "degreeName")
    DepartmentDTO toDto(Department department);

    @Mapping(target = "classTimeTables", ignore = true)
    @Mapping(target = "removeClassTimeTable", ignore = true)
    @Mapping(target = "examTimeTables", ignore = true)
    @Mapping(target = "removeExamTimeTable", ignore = true)
    @Mapping(source = "degreeId", target = "degree")
    Department toEntity(DepartmentDTO departmentDTO);

    default Department fromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
