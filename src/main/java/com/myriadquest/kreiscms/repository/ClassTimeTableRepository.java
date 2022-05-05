package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.ClassTimeTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ClassTimeTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassTimeTableRepository extends JpaRepository<ClassTimeTable, Long>, JpaSpecificationExecutor<ClassTimeTable> {

    @Query("select classTimeTable from ClassTimeTable classTimeTable where classTimeTable.faculty.login = ?#{principal.username}")
    List<ClassTimeTable> findByFacultyIsCurrentUser();
}
