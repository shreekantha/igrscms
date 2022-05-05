package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.myriadquest.kreiscms.domain.enumeration.Day;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.ClassTimeTable} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.ClassTimeTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /class-time-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassTimeTableCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Day
     */
    public static class DayFilter extends Filter<Day> {

        public DayFilter() {
        }

        public DayFilter(DayFilter filter) {
            super(filter);
        }

        @Override
        public DayFilter copy() {
            return new DayFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DayFilter day;

    private StringFilter tenantId;

    private LongFilter facultyId;

    private LongFilter academicCalendarId;

    private LongFilter degreeId;

    private LongFilter departmentId;

    private LongFilter termId;

    private LongFilter courseId;

    private LongFilter periodId;

    public ClassTimeTableCriteria() {
    }

    public ClassTimeTableCriteria(ClassTimeTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.facultyId = other.facultyId == null ? null : other.facultyId.copy();
        this.academicCalendarId = other.academicCalendarId == null ? null : other.academicCalendarId.copy();
        this.degreeId = other.degreeId == null ? null : other.degreeId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.termId = other.termId == null ? null : other.termId.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
        this.periodId = other.periodId == null ? null : other.periodId.copy();
    }

    @Override
    public ClassTimeTableCriteria copy() {
        return new ClassTimeTableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DayFilter getDay() {
        return day;
    }

    public void setDay(DayFilter day) {
        this.day = day;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(LongFilter facultyId) {
        this.facultyId = facultyId;
    }

    public LongFilter getAcademicCalendarId() {
        return academicCalendarId;
    }

    public void setAcademicCalendarId(LongFilter academicCalendarId) {
        this.academicCalendarId = academicCalendarId;
    }

    public LongFilter getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(LongFilter degreeId) {
        this.degreeId = degreeId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getTermId() {
        return termId;
    }

    public void setTermId(LongFilter termId) {
        this.termId = termId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    public LongFilter getPeriodId() {
        return periodId;
    }

    public void setPeriodId(LongFilter periodId) {
        this.periodId = periodId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassTimeTableCriteria that = (ClassTimeTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(facultyId, that.facultyId) &&
            Objects.equals(academicCalendarId, that.academicCalendarId) &&
            Objects.equals(degreeId, that.degreeId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(termId, that.termId) &&
            Objects.equals(courseId, that.courseId) &&
            Objects.equals(periodId, that.periodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        day,
        tenantId,
        facultyId,
        academicCalendarId,
        degreeId,
        departmentId,
        termId,
        courseId,
        periodId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (day != null ? "day=" + day + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (facultyId != null ? "facultyId=" + facultyId + ", " : "") +
                (academicCalendarId != null ? "academicCalendarId=" + academicCalendarId + ", " : "") +
                (degreeId != null ? "degreeId=" + degreeId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (termId != null ? "termId=" + termId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
                (periodId != null ? "periodId=" + periodId + ", " : "") +
            "}";
    }

}
