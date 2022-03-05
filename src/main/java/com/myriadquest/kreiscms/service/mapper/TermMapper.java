package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.TermDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Term} and its DTO {@link TermDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TermMapper extends EntityMapper<TermDTO, Term> {

    @Mapping(source = "classTeacher.id", target = "classTeacherId")
    @Mapping(source = "classTeacher.firstName", target = "classTeacherFirstName")
    TermDTO toDto(Term term);

    @Mapping(target = "classTimeTables", ignore = true)
    @Mapping(target = "removeClassTimeTable", ignore = true)
    @Mapping(target = "examTimeTables", ignore = true)
    @Mapping(target = "removeExamTimeTable", ignore = true)
    @Mapping(source = "classTeacherId", target = "classTeacher")
    Term toEntity(TermDTO termDTO);

    default Term fromId(Long id) {
        if (id == null) {
            return null;
        }
        Term term = new Term();
        term.setId(id);
        return term;
    }
}
