package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.myriadquest.kreiscms.domain.enumeration.Title;
import com.myriadquest.kreiscms.domain.enumeration.Designation;
import com.myriadquest.kreiscms.domain.enumeration.UserType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.UserProfile} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.UserProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserProfileCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Title
     */
    public static class TitleFilter extends Filter<Title> {

        public TitleFilter() {
        }

        public TitleFilter(TitleFilter filter) {
            super(filter);
        }

        @Override
        public TitleFilter copy() {
            return new TitleFilter(this);
        }

    }
    /**
     * Class for filtering Designation
     */
    public static class DesignationFilter extends Filter<Designation> {

        public DesignationFilter() {
        }

        public DesignationFilter(DesignationFilter filter) {
            super(filter);
        }

        @Override
        public DesignationFilter copy() {
            return new DesignationFilter(this);
        }

    }
    /**
     * Class for filtering UserType
     */
    public static class UserTypeFilter extends Filter<UserType> {

        public UserTypeFilter() {
        }

        public UserTypeFilter(UserTypeFilter filter) {
            super(filter);
        }

        @Override
        public UserTypeFilter copy() {
            return new UserTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter mobile;

    private StringFilter email;

    private DesignationFilter designation;

    private UserTypeFilter userType;

    private StringFilter eduQual;

    private StringFilter aboutMe;

    private StringFilter hobbies;

    private StringFilter aadhaar;

    private LocalDateFilter dob;

    private StringFilter imgLink;

    private StringFilter facebookLink;

    private StringFilter instaLink;

    private StringFilter twitterLink;

    private StringFilter linkedLink;

    private StringFilter tenantId;

    public UserProfileCriteria() {
    }

    public UserProfileCriteria(UserProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.mobile = other.mobile == null ? null : other.mobile.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.eduQual = other.eduQual == null ? null : other.eduQual.copy();
        this.aboutMe = other.aboutMe == null ? null : other.aboutMe.copy();
        this.hobbies = other.hobbies == null ? null : other.hobbies.copy();
        this.aadhaar = other.aadhaar == null ? null : other.aadhaar.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.imgLink = other.imgLink == null ? null : other.imgLink.copy();
        this.facebookLink = other.facebookLink == null ? null : other.facebookLink.copy();
        this.instaLink = other.instaLink == null ? null : other.instaLink.copy();
        this.twitterLink = other.twitterLink == null ? null : other.twitterLink.copy();
        this.linkedLink = other.linkedLink == null ? null : other.linkedLink.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public UserProfileCriteria copy() {
        return new UserProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TitleFilter getTitle() {
        return title;
    }

    public void setTitle(TitleFilter title) {
        this.title = title;
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

    public DesignationFilter getDesignation() {
        return designation;
    }

    public void setDesignation(DesignationFilter designation) {
        this.designation = designation;
    }

    public UserTypeFilter getUserType() {
        return userType;
    }

    public void setUserType(UserTypeFilter userType) {
        this.userType = userType;
    }

    public StringFilter getEduQual() {
        return eduQual;
    }

    public void setEduQual(StringFilter eduQual) {
        this.eduQual = eduQual;
    }

    public StringFilter getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(StringFilter aboutMe) {
        this.aboutMe = aboutMe;
    }

    public StringFilter getHobbies() {
        return hobbies;
    }

    public void setHobbies(StringFilter hobbies) {
        this.hobbies = hobbies;
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

    public StringFilter getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(StringFilter facebookLink) {
        this.facebookLink = facebookLink;
    }

    public StringFilter getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(StringFilter instaLink) {
        this.instaLink = instaLink;
    }

    public StringFilter getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(StringFilter twitterLink) {
        this.twitterLink = twitterLink;
    }

    public StringFilter getLinkedLink() {
        return linkedLink;
    }

    public void setLinkedLink(StringFilter linkedLink) {
        this.linkedLink = linkedLink;
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
        final UserProfileCriteria that = (UserProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(email, that.email) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(eduQual, that.eduQual) &&
            Objects.equals(aboutMe, that.aboutMe) &&
            Objects.equals(hobbies, that.hobbies) &&
            Objects.equals(aadhaar, that.aadhaar) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(imgLink, that.imgLink) &&
            Objects.equals(facebookLink, that.facebookLink) &&
            Objects.equals(instaLink, that.instaLink) &&
            Objects.equals(twitterLink, that.twitterLink) &&
            Objects.equals(linkedLink, that.linkedLink) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        firstName,
        lastName,
        mobile,
        email,
        designation,
        userType,
        eduQual,
        aboutMe,
        hobbies,
        aadhaar,
        dob,
        imgLink,
        facebookLink,
        instaLink,
        twitterLink,
        linkedLink,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (mobile != null ? "mobile=" + mobile + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (userType != null ? "userType=" + userType + ", " : "") +
                (eduQual != null ? "eduQual=" + eduQual + ", " : "") +
                (aboutMe != null ? "aboutMe=" + aboutMe + ", " : "") +
                (hobbies != null ? "hobbies=" + hobbies + ", " : "") +
                (aadhaar != null ? "aadhaar=" + aadhaar + ", " : "") +
                (dob != null ? "dob=" + dob + ", " : "") +
                (imgLink != null ? "imgLink=" + imgLink + ", " : "") +
                (facebookLink != null ? "facebookLink=" + facebookLink + ", " : "") +
                (instaLink != null ? "instaLink=" + instaLink + ", " : "") +
                (twitterLink != null ? "twitterLink=" + twitterLink + ", " : "") +
                (linkedLink != null ? "linkedLink=" + linkedLink + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
