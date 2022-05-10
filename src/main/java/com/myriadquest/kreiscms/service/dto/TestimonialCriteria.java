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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Testimonial} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.TestimonialResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /testimonials?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TestimonialCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter imgLink;

    private StringFilter batchYear;

    public TestimonialCriteria() {
    }

    public TestimonialCriteria(TestimonialCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.imgLink = other.imgLink == null ? null : other.imgLink.copy();
        this.batchYear = other.batchYear == null ? null : other.batchYear.copy();
    }

    @Override
    public TestimonialCriteria copy() {
        return new TestimonialCriteria(this);
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

    public StringFilter getImgLink() {
        return imgLink;
    }

    public void setImgLink(StringFilter imgLink) {
        this.imgLink = imgLink;
    }

    public StringFilter getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(StringFilter batchYear) {
        this.batchYear = batchYear;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TestimonialCriteria that = (TestimonialCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(imgLink, that.imgLink) &&
            Objects.equals(batchYear, that.batchYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        imgLink,
        batchYear
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestimonialCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (imgLink != null ? "imgLink=" + imgLink + ", " : "") +
                (batchYear != null ? "batchYear=" + batchYear + ", " : "") +
            "}";
    }

}
