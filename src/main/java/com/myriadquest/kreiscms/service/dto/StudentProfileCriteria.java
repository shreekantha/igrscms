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
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.StudentProfile} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.StudentProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /student-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter fathersName;

    private StringFilter mothersName;

    private IntegerFilter currentTerm;

    private StringFilter joiningAcademicYear;

    private StringFilter exitAcademicYear;

    private StringFilter mobile;

    private StringFilter email;

    private StringFilter aadhaar;

    private LocalDateFilter dob;

    private StringFilter imgLink;

    private StringFilter tenantId;

    private BooleanFilter active;

    public StudentProfileCriteria() {
    }

    public StudentProfileCriteria(StudentProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.fathersName = other.fathersName == null ? null : other.fathersName.copy();
        this.mothersName = other.mothersName == null ? null : other.mothersName.copy();
        this.currentTerm = other.currentTerm == null ? null : other.currentTerm.copy();
        this.joiningAcademicYear = other.joiningAcademicYear == null ? null : other.joiningAcademicYear.copy();
        this.exitAcademicYear = other.exitAcademicYear == null ? null : other.exitAcademicYear.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.aadhaar = other.aadhaar == null ? null : other.aadhaar.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.imgLink = other.imgLink == null ? null : other.imgLink.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.active = other.active == null ? null : other.active.copy();
    }

    @Override
    public StudentProfileCriteria copy() {
        return new StudentProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getFathersName() {
        return fathersName;
    }

    public void setFathersName(StringFilter fathersName) {
        this.fathersName = fathersName;
    }

    public StringFilter getMothersName() {
        return mothersName;
    }

    public void setMothersName(StringFilter mothersName) {
        this.mothersName = mothersName;
    }

    public IntegerFilter getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(IntegerFilter currentTerm) {
        this.currentTerm = currentTerm;
    }

    public StringFilter getJoiningAcademicYear() {
        return joiningAcademicYear;
    }

    public void setJoiningAcademicYear(StringFilter joiningAcademicYear) {
        this.joiningAcademicYear = joiningAcademicYear;
    }

    public StringFilter getExitAcademicYear() {
        return exitAcademicYear;
    }

    public void setExitAcademicYear(StringFilter exitAcademicYear) {
        this.exitAcademicYear = exitAcademicYear;
    }

    public StringFilter getMobile() {
        return mobile;
    }

    public void setMobile(StringFilter mobile) {
        this.mobile = mobile;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(StringFilter aadhaar) {
        this.aadhaar = aadhaar;
    }

    public LocalDateFilter getDob() {
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
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

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudentProfileCriteria that = (StudentProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(fathersName, that.fathersName) &&
            Objects.equals(mothersName, that.mothersName) &&
            Objects.equals(currentTerm, that.currentTerm) &&
            Objects.equals(joiningAcademicYear, that.joiningAcademicYear) &&
            Objects.equals(exitAcademicYear, that.exitAcademicYear) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(email, that.email) &&
            Objects.equals(aadhaar, that.aadhaar) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(imgLink, that.imgLink) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(active, that.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        firstName,
        lastName,
        fathersName,
        mothersName,
        currentTerm,
        joiningAcademicYear,
        exitAcademicYear,
        mobile,
        email,
        aadhaar,
        dob,
        imgLink,
        tenantId,
        active
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (fathersName != null ? "fathersName=" + fathersName + ", " : "") +
                (mothersName != null ? "mothersName=" + mothersName + ", " : "") +
                (currentTerm != null ? "currentTerm=" + currentTerm + ", " : "") +
                (joiningAcademicYear != null ? "joiningAcademicYear=" + joiningAcademicYear + ", " : "") +
                (exitAcademicYear != null ? "exitAcademicYear=" + exitAcademicYear + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") +
                (dob != null ? "dob=" + dob + ", " : "") +
                (imgLink != null ? "imgLink=" + imgLink + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
            "}";
    }

}
