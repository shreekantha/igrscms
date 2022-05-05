package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.myriadquest.kreiscms.domain.enumeration.ExamType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.ExamTimeTable} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.ExamTimeTableResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /exam-time-tables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamTimeTableCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ExamType
     */
    public static class ExamTypeFilter extends Filter<ExamType> {

        public ExamTypeFilter() {
        }

        public ExamTypeFilter(ExamTypeFilter filter) {
            super(filter);
        }

        @Override
        public ExamTypeFilter copy() {
            return new ExamTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ExamTypeFilter examType;

    private LocalDateFilter date;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private StringFilter tenantId;

    private LongFilter academicCalendarId;

    private LongFilter degreeId;

    private LongFilter departmentId;

    private LongFilter termId;

    private LongFilter courseId;

    public ExamTimeTableCriteria() {
    }

    public ExamTimeTableCriteria(ExamTimeTableCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.examType = other.examType == null ? null : other.examType.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.academicCalendarId = other.academicCalendarId == null ? null : other.academicCalendarId.copy();
        this.degreeId = other.degreeId == null ? null : other.degreeId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.termId = other.termId == null ? null : other.termId.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
    }

    @Override
    public ExamTimeTableCriteria copy() {
        return new ExamTimeTableCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ExamTypeFilter getExamType() {
        return examType;
    }

    public void setExamType(ExamTypeFilter examType) {
        this.examType = examType;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public InstantFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
    }

    public InstantFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(InstantFilter endTime) {
        this.endTime = endTime;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExamTimeTableCriteria that = (ExamTimeTableCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(examType, that.examType) &&
            Objects.equals(date, that.date) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(academicCalendarId, that.academicCalendarId) &&
            Objects.equals(degreeId, that.degreeId) &&
            Objects.equals(departmentId, that.departmentId) &&
            Objects.equals(termId, that.termId) &&
            Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        examType,
        date,
        startTime,
        endTime,
        tenantId,
        academicCalendarId,
        degreeId,
        departmentId,
        termId,
        courseId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamTimeTableCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (examType != null ? "examType=" + examType + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (academicCalendarId != null ? "academicCalendarId=" + academicCalendarId + ", " : "") +
                (degreeId != null ? "degreeId=" + degreeId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
                (termId != null ? "termId=" + termId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
            "}";
    }

}
