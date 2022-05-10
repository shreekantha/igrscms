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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.Institute} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.InstituteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /institutes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InstituteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter shortName;

    private StringFilter address;

    private StringFilter email;

    private StringFilter contact;

    private StringFilter logoLink;

    private StringFilter tagLine;

    private StringFilter tenantId;

    public InstituteCriteria() {
    }

    public InstituteCriteria(InstituteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.logoLink = other.logoLink == null ? null : other.logoLink.copy();
        this.tagLine = other.tagLine == null ? null : other.tagLine.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public InstituteCriteria copy() {
        return new InstituteCriteria(this);
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

    public StringFilter getShortName() {
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getContact() {
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getLogoLink() {
        return logoLink;
    }

    public void setLogoLink(StringFilter logoLink) {
        this.logoLink = logoLink;
    }

    public StringFilter getTagLine() {
        return tagLine;
    }

    public void setTagLine(StringFilter tagLine) {
        this.tagLine = tagLine;
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
        final InstituteCriteria that = (InstituteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(address, that.address) &&
            Objects.equals(email, that.email) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(logoLink, that.logoLink) &&
            Objects.equals(tagLine, that.tagLine) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        shortName,
        address,
        email,
        contact,
        logoLink,
        tagLine,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (shortName != null ? "shortName=" + shortName + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (logoLink != null ? "logoLink=" + logoLink + ", " : "") +
                (tagLine != null ? "tagLine=" + tagLine + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
