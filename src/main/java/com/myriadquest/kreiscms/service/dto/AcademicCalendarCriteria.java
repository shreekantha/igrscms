package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.AcademicCalendar} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.AcademicCalendarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /academic-calendars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AcademicCalendarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter academicYear;

    private BooleanFilter active;

    private StringFilter tenantId;

    private LongFilter classTimeTableId;

    private LongFilter examTimeTableId;

    private LongFilter degreeId;

    public AcademicCalendarCriteria() {
    }

    public AcademicCalendarCriteria(AcademicCalendarCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.academicYear = other.academicYear == null ? null : other.academicYear.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.classTimeTableId = other.classTimeTableId == null ? null : other.classTimeTableId.copy();
        this.examTimeTableId = other.examTimeTableId == null ? null : other.examTimeTableId.copy();
        this.degreeId = other.degreeId == null ? null : other.degreeId.copy();
    }

    @Override
    public AcademicCalendarCriteria copy() {
        return new AcademicCalendarCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getClassTimeTableId() {
        return classTimeTableId;
    }

    public void setClassTimeTableId(LongFilter classTimeTableId) {
        this.classTimeTableId = classTimeTableId;
    }

    public LongFilter getExamTimeTableId() {
        return examTimeTableId;
    }

    public void setExamTimeTableId(LongFilter examTimeTableId) {
        this.examTimeTableId = examTimeTableId;
    }

    public LongFilter getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(LongFilter degreeId) {
        this.degreeId = degreeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AcademicCalendarCriteria that = (AcademicCalendarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(academicYear, that.academicYear) &&
            Objects.equals(active, that.active) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(classTimeTableId, that.classTimeTableId) &&
            Objects.equals(examTimeTableId, that.examTimeTableId) &&
            Objects.equals(degreeId, that.degreeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        academicYear,
        active,
        tenantId,
        classTimeTableId,
        examTimeTableId,
        degreeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicCalendarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (academicYear != null ? "academicYear=" + academicYear + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (classTimeTableId != null ? "classTimeTableId=" + classTimeTableId + ", " : "") +
                (examTimeTableId != null ? "examTimeTableId=" + examTimeTableId + ", " : "") +
                (degreeId != null ? "degreeId=" + degreeId + ", " : "") +
            "}";
    }

}
