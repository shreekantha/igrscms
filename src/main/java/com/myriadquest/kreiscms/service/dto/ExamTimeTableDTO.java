package com.myriadquest.kreiscms.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.myriadquest.kreiscms.domain.enumeration.ExamType;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.ExamTimeTable} entity.
 */
public class ExamTimeTableDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ExamType examType;

    @NotNull
    private LocalDate date;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    private String tenantId;


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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamTimeTableDTO)) {
            return false;
        }

        return id != null && id.equals(((ExamTimeTableDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamTimeTableDTO{" +
            "id=" + getId() +
            ", examType='" + getExamType() + "'" +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", tenantId='" + getTenantId() + "'" +
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
            "}";
    }
}
