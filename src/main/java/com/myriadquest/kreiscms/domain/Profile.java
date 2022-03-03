package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.myriadquest.kreiscms.domain.enumeration.Title;

import com.myriadquest.kreiscms.domain.enumeration.Designation;

import com.myriadquest.kreiscms.domain.enumeration.UserType;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "title", nullable = false)
    private Title title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "designation", nullable = false)
    private Designation designation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "edu_qual")
    private String eduQual;

    @NotNull
    @Column(name = "about_me", nullable = false)
    private String aboutMe;

    @Column(name = "hobbies")
    private String hobbies;

    @NotNull
    @Size(min = 12, max = 12)
    @Column(name = "aadhaar", length = 12, nullable = false)
    private String aadhaar;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "mobile", length = 10, nullable = false)
    private String mobile;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "img_link")
    private String imgLink;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @NotNull
    @Column(name = "facebook_link", nullable = false)
    private String facebookLink;

    @NotNull
    @Column(name = "insta_link", nullable = false)
    private String instaLink;

    @NotNull
    @Column(name = "twitter_link", nullable = false)
    private String twitterLink;

    @NotNull
    @Column(name = "linked_link", nullable = false)
    private String linkedLink;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public Profile title(Title title) {
        this.title = title;
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Designation getDesignation() {
        return designation;
    }

    public Profile designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public UserType getUserType() {
        return userType;
    }

    public Profile userType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEduQual() {
        return eduQual;
    }

    public Profile eduQual(String eduQual) {
        this.eduQual = eduQual;
        return this;
    }

    public void setEduQual(String eduQual) {
        this.eduQual = eduQual;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Profile aboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
        return this;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getHobbies() {
        return hobbies;
    }

    public Profile hobbies(String hobbies) {
        this.hobbies = hobbies;
        return this;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public Profile aadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
        return this;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Profile dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public Profile mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgLink() {
        return imgLink;
    }

    public Profile imgLink(String imgLink) {
        this.imgLink = imgLink;
        return this;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public byte[] getImg() {
        return img;
    }

    public Profile img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Profile imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public Profile facebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
        return this;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public Profile instaLink(String instaLink) {
        this.instaLink = instaLink;
        return this;
    }

    public void setInstaLink(String instaLink) {
        this.instaLink = instaLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public Profile twitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
        return this;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getLinkedLink() {
        return linkedLink;
    }

    public Profile linkedLink(String linkedLink) {
        this.linkedLink = linkedLink;
        return this;
    }

    public void setLinkedLink(String linkedLink) {
        this.linkedLink = linkedLink;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
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
            ", imgContentType='" + getImgContentType() + "'" +
            ", facebookLink='" + getFacebookLink() + "'" +
            ", instaLink='" + getInstaLink() + "'" +
            ", twitterLink='" + getTwitterLink() + "'" +
            ", linkedLink='" + getLinkedLink() + "'" +
            "}";
    }
}
