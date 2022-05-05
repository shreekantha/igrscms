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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Term} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.TermResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /terms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TermCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter term;

    private StringFilter title;

    private StringFilter description;

    private StringFilter imgUrl;

    private IntegerFilter noOfStudents;

    private StringFilter tenantId;

    private LongFilter classTimeTableId;

    private LongFilter examTimeTableId;

    private LongFilter classTeacherId;

    public TermCriteria() {
    }

    public TermCriteria(TermCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.term = other.term == null ? null : other.term.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.imgUrl = other.imgUrl == null ? null : other.imgUrl.copy();
        this.noOfStudents = other.noOfStudents == null ? null : other.noOfStudents.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.classTimeTableId = other.classTimeTableId == null ? null : other.classTimeTableId.copy();
        this.examTimeTableId = other.examTimeTableId == null ? null : other.examTimeTableId.copy();
        this.classTeacherId = other.classTeacherId == null ? null : other.classTeacherId.copy();
    }

    @Override
    public TermCriteria copy() {
        return new TermCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getTerm() {
        return term;
    }

    public void setTerm(IntegerFilter term) {
        this.term = term;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(StringFilter imgUrl) {
        this.imgUrl = imgUrl;
    }

    public IntegerFilter getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(IntegerFilter noOfStudents) {
        this.noOfStudents = noOfStudents;
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

    public LongFilter getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(LongFilter classTeacherId) {
        this.classTeacherId = classTeacherId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TermCriteria that = (TermCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(term, that.term) &&
            Objects.equals(title, that.title) &&
            Objects.equals(description, that.description) &&
            Objects.equals(imgUrl, that.imgUrl) &&
            Objects.equals(noOfStudents, that.noOfStudents) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(classTimeTableId, that.classTimeTableId) &&
            Objects.equals(examTimeTableId, that.examTimeTableId) &&
            Objects.equals(classTeacherId, that.classTeacherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        term,
        title,
        description,
        imgUrl,
        noOfStudents,
        tenantId,
        classTimeTableId,
        examTimeTableId,
        classTeacherId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (term != null ? "term=" + term + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (imgUrl != null ? "imgUrl=" + imgUrl + ", " : "") +
                (noOfStudents != null ? "noOfStudents=" + noOfStudents + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (classTimeTableId != null ? "classTimeTableId=" + classTimeTableId + ", " : "") +
                (examTimeTableId != null ? "examTimeTableId=" + examTimeTableId + ", " : "") +
                (classTeacherId != null ? "classTeacherId=" + classTeacherId + ", " : "") +
            "}";
    }

}
