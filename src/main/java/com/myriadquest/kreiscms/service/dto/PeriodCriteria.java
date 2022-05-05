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

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Period} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.PeriodResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /periods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PeriodCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter number;

    private StringFilter label;

    private StringFilter startTime;

    private StringFilter endTime;

    private StringFilter tenantId;

    private LongFilter classTimeTableId;

    public PeriodCriteria() {
    }

    public PeriodCriteria(PeriodCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.label = other.label == null ? null : other.label.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.classTimeTableId = other.classTimeTableId == null ? null : other.classTimeTableId.copy();
    }

    @Override
    public PeriodCriteria copy() {
        return new PeriodCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumber() {
        return number;
    }

    public void setNumber(IntegerFilter number) {
        this.number = number;
    }

    public StringFilter getLabel() {
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public StringFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(StringFilter startTime) {
        this.startTime = startTime;
    }

    public StringFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(StringFilter endTime) {
        this.endTime = endTime;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PeriodCriteria that = (PeriodCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(label, that.label) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(classTimeTableId, that.classTimeTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        number,
        label,
        startTime,
        endTime,
        tenantId,
        classTimeTableId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeriodCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (label != null ? "label=" + label + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (classTimeTableId != null ? "classTimeTableId=" + classTimeTableId + ", " : "") +
            "}";
    }

}
