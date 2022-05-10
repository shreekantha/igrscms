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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Gallery} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.GalleryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /galleries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GalleryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter imgUrl;

    private StringFilter descritpion;

    private StringFilter tenantId;

    private LongFilter categoryId;

    public GalleryCriteria() {
    }

    public GalleryCriteria(GalleryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.imgUrl = other.imgUrl == null ? null : other.imgUrl.copy();
        this.descritpion = other.descritpion == null ? null : other.descritpion.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public GalleryCriteria copy() {
        return new GalleryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(StringFilter imgUrl) {
        this.imgUrl = imgUrl;
    }

    public StringFilter getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(StringFilter descritpion) {
        this.descritpion = descritpion;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GalleryCriteria that = (GalleryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(imgUrl, that.imgUrl) &&
            Objects.equals(descritpion, that.descritpion) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        imgUrl,
        descritpion,
        tenantId,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (imgUrl != null ? "imgUrl=" + imgUrl + ", " : "") +
                (descritpion != null ? "descritpion=" + descritpion + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
