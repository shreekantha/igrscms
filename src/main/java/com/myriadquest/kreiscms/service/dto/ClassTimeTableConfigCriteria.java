package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.myriadquest.kreiscms.domain.enumeration.TimeTableGenType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.ClassTimeTableConfig} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.ClassTimeTableConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /class-time-table-configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassTimeTableConfigCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TimeTableGenType
     */
    public static class TimeTableGenTypeFilter extends Filter<TimeTableGenType> {

        public TimeTableGenTypeFilter() {
        }

        public TimeTableGenTypeFilter(TimeTableGenTypeFilter filter) {
            super(filter);
        }

        @Override
        public TimeTableGenTypeFilter copy() {
            return new TimeTableGenTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TimeTableGenTypeFilter timeTableGenType;

    private StringFilter tenantId;

    public ClassTimeTableConfigCriteria() {
    }

    public ClassTimeTableConfigCriteria(ClassTimeTableConfigCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.timeTableGenType = other.timeTableGenType == null ? null : other.timeTableGenType.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public ClassTimeTableConfigCriteria copy() {
        return new ClassTimeTableConfigCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TimeTableGenTypeFilter getTimeTableGenType() {
        return timeTableGenType;
    }

    public void setTimeTableGenType(TimeTableGenTypeFilter timeTableGenType) {
        this.timeTableGenType = timeTableGenType;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassTimeTableConfigCriteria that = (ClassTimeTableConfigCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(timeTableGenType, that.timeTableGenType) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        timeTableGenType,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTableConfigCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (timeTableGenType != null ? "timeTableGenType=" + timeTableGenType + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
