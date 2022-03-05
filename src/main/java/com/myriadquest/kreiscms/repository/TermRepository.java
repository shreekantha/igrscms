package com.myriadquest.kreiscms.repository;

import com.myriadquest.kreiscms.domain.Term;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Term entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("select term from Term term where term.classTeacher.login = ?#{principal.username}")
    List<Term> findByClassTeacherIsCurrentUser();
}
