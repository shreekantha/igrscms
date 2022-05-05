package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.myriadquest.kreiscms.domain.enumeration.Day;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.ClassTimeTable} entity.
 */
public class ClassTimeTableDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Day day;

    private String tenantId;


    private Long facultyId;

    private String facultyFirstName;

    private Long academicCalendarId;

    private String academicCalendarAcademicYear;

    private Long degreeId;

    private String degreeName;

    private Long departmentId;

    private String departmentName;

    private Long termId;

    private String termTitle;

    private Long courseId;

    private String courseName;

    private Long periodId;

    private String periodLabel;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long userId) {
        this.facultyId = userId;
    }

    public String getFacultyFirstName() {
        return facultyFirstName;
    }

    public void setFacultyFirstName(String userFirstName) {
        this.facultyFirstName = userFirstName;
    }

    public Long getAcademicCalendarId() {
        return academicCalendarId;
    }

    public void setAcademicCalendarId(Long academicCalendarId) {
        this.academicCalendarId = academicCalendarId;
    }

    public String getAcademicCalendarAcademicYear() {
        return academicCalendarAcademicYear;
    }

    public void setAcademicCalendarAcademicYear(String academicCalendarAcademicYear) {
        this.academicCalendarAcademicYear = academicCalendarAcademicYear;
    }

    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public String getPeriodLabel() {
        return periodLabel;
    }

    public void setPeriodLabel(String periodLabel) {
        this.periodLabel = periodLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassTimeTableDTO)) {
            return false;
        }

        return id != null && id.equals(((ClassTimeTableDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTableDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", facultyId=" + getFacultyId() +
            ", facultyFirstName='" + getFacultyFirstName() + "'" +
            ", academicCalendarId=" + getAcademicCalendarId() +
            ", academicCalendarAcademicYear='" + getAcademicCalendarAcademicYear() + "'" +
            ", degreeId=" + getDegreeId() +
            ", degreeName='" + getDegreeName() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", departmentName='" + getDepartmentName() + "'" +
            ", termId=" + getTermId() +
            ", termTitle='" + getTermTitle() + "'" +
            ", courseId=" + getCourseId() +
            ", courseName='" + getCourseName() + "'" +
            ", periodId=" + getPeriodId() +
            ", periodLabel='" + getPeriodLabel() + "'" +
            "}";
    }
}
