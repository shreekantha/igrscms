package com.myriadquest.kreiscms.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import com.myriadquest.kreiscms.domain.enumeration.Title;
import com.myriadquest.kreiscms.domain.enumeration.Designation;
import com.myriadquest.kreiscms.domain.enumeration.UserType;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Title title;

    @NotNull
    private Designation designation;

    @NotNull
    private UserType userType;

    private String eduQual;

    @NotNull
    private String aboutMe;

    private String hobbies;

    @NotNull
    @Size(min = 12, max = 12)
    private String aadhaar;

    @NotNull
    private LocalDate dob;

    @NotNull
    @Size(min = 10, max = 10)
    private String mobile;

    @NotNull
    private String email;

    private String imgLink;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    @NotNull
    private String facebookLink;

    @NotNull
    private String instaLink;

    @NotNull
    private String twitterLink;

    @NotNull
    private String linkedLink;


    private Long userId;

    private String userFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEduQual() {
        return eduQual;
    }

    public void setEduQual(String eduQual) {
        this.eduQual = eduQual;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getLinkedLink() {
        return linkedLink;
    }

    public void setLinkedLink(String linkedLink) {
        this.linkedLink = linkedLink;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        return id != null && id.equals(((ProfileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", userType='" + getUserType() + "'" +
            ", eduQual='" + getEduQual() + "'" +
            ", aboutMe='" + getAboutMe() + "'" +
            ", hobbies='" + getHobbies() + "'" +
            ", aadhaar='" + getAadhaar() + "'" +
            ", dob='" + getDob() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", img='" + getImg() + "'" +
            ", facebookLink='" + getFacebookLink() + "'" +
            ", instaLink='" + getInstaLink() + "'" +
            ", twitterLink='" + getTwitterLink() + "'" +
            ", linkedLink='" + getLinkedLink() + "'" +
            ", userId=" + getUserId() +
            ", userFirstName='" + getUserFirstName() + "'" +
            "}";
    }
}
