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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.PucResult} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.PucResultResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /puc-results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PucResultCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter academicYear;

    private DoubleFilter qualityResult;

    private DoubleFilter topResult;

    private StringFilter tenantId;

    public PucResultCriteria() {
    }

    public PucResultCriteria(PucResultCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.academicYear = other.academicYear == null ? null : other.academicYear.copy();
        this.qualityResult = other.qualityResult == null ? null : other.qualityResult.copy();
        this.topResult = other.topResult == null ? null : other.topResult.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public PucResultCriteria copy() {
        return new PucResultCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public DoubleFilter getQualityResult() {
        return qualityResult;
    }

    public void setQualityResult(DoubleFilter qualityResult) {
        this.qualityResult = qualityResult;
    }

    public DoubleFilter getTopResult() {
        return topResult;
    }

    public void setTopResult(DoubleFilter topResult) {
        this.topResult = topResult;
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
        final PucResultCriteria that = (PucResultCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(academicYear, that.academicYear) &&
            Objects.equals(qualityResult, that.qualityResult) &&
            Objects.equals(topResult, that.topResult) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        academicYear,
        qualityResult,
        topResult,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PucResultCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (academicYear != null ? "academicYear=" + academicYear + ", " : "") +
                (qualityResult != null ? "qualityResult=" + qualityResult + ", " : "") +
                (topResult != null ? "topResult=" + topResult + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
