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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Course} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.CourseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /courses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter alias;

    private StringFilter code;

    private StringFilter tenantId;

    private LongFilter classTimeTableId;

    private LongFilter examTimeTableId;

    public CourseCriteria() {
    }

    public CourseCriteria(CourseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.alias = other.alias == null ? null : other.alias.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.classTimeTableId = other.classTimeTableId == null ? null : other.classTimeTableId.copy();
        this.examTimeTableId = other.examTimeTableId == null ? null : other.examTimeTableId.copy();
    }

    @Override
    public CourseCriteria copy() {
        return new CourseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAlias() {
        return alias;
    }

    public void setAlias(StringFilter alias) {
        this.alias = alias;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CourseCriteria that = (CourseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(alias, that.alias) &&
            Objects.equals(code, that.code) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(classTimeTableId, that.classTimeTableId) &&
            Objects.equals(examTimeTableId, that.examTimeTableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        alias,
        code,
        tenantId,
        classTimeTableId,
        examTimeTableId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (alias != null ? "alias=" + alias + ", " : "") +
                (code != null ? "code=" + code + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (classTimeTableId != null ? "classTimeTableId=" + classTimeTableId + ", " : "") +
                (examTimeTableId != null ? "examTimeTableId=" + examTimeTableId + ", " : "") +
            "}";
    }

}
