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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.GalleryCat} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.GalleryCatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gallery-cats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GalleryCatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter imgLink;

    private StringFilter tenantId;

    private LongFilter galleryId;

    public GalleryCatCriteria() {
    }

    public GalleryCatCriteria(GalleryCatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.imgLink = other.imgLink == null ? null : other.imgLink.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.galleryId = other.galleryId == null ? null : other.galleryId.copy();
    }

    @Override
    public GalleryCatCriteria copy() {
        return new GalleryCatCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getImgLink() {
        return imgLink;
    }

    public void setImgLink(StringFilter imgLink) {
        this.imgLink = imgLink;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(LongFilter galleryId) {
        this.galleryId = galleryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GalleryCatCriteria that = (GalleryCatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(imgLink, that.imgLink) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(galleryId, that.galleryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        imgLink,
        tenantId,
        galleryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleryCatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (imgLink != null ? "imgLink=" + imgLink + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (galleryId != null ? "galleryId=" + galleryId + ", " : "") +
            "}";
    }

}
