package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AlumniProfile.
 */
@Entity
@Table(name = "alumni_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlumniProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "fathers_name")
    private String fathersName;

    @Column(name = "mothers_name")
    private String mothersName;

    @Column(name = "current_term")
    private Integer currentTerm;

    @Column(name = "joining_academic_year")
    private String joiningAcademicYear;

    @Column(name = "exit_academic_year")
    private String exitAcademicYear;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "mobile", length = 10, nullable = false)
    private String mobile;

    @Column(name = "email")
    private String email;

    @Size(min = 12, max = 12)
    @Column(name = "aadhaar", length = 12)
    private String aadhaar;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "img_link")
    private String imgLink;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public AlumniProfile firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public AlumniProfile lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public AlumniProfile fathersName(String fathersName) {
        this.fathersName = fathersName;
        return this;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public AlumniProfile mothersName(String mothersName) {
        this.mothersName = mothersName;
        return this;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public Integer getCurrentTerm() {
        return currentTerm;
    }

    public AlumniProfile currentTerm(Integer currentTerm) {
        this.currentTerm = currentTerm;
        return this;
    }

    public void setCurrentTerm(Integer currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getJoiningAcademicYear() {
        return joiningAcademicYear;
    }

    public AlumniProfile joiningAcademicYear(String joiningAcademicYear) {
        this.joiningAcademicYear = joiningAcademicYear;
        return this;
    }

    public void setJoiningAcademicYear(String joiningAcademicYear) {
        this.joiningAcademicYear = joiningAcademicYear;
    }

    public String getExitAcademicYear() {
        return exitAcademicYear;
    }

    public AlumniProfile exitAcademicYear(String exitAcademicYear) {
        this.exitAcademicYear = exitAcademicYear;
        return this;
    }

    public void setExitAcademicYear(String exitAcademicYear) {
        this.exitAcademicYear = exitAcademicYear;
    }

    public String getMobile() {
        return mobile;
    }

    public AlumniProfile mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public AlumniProfile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public AlumniProfile aadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
        return this;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public LocalDate getDob() {
        return dob;
    }

    public AlumniProfile dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getImgLink() {
        return imgLink;
    }

    public AlumniProfile imgLink(String imgLink) {
        this.imgLink = imgLink;
        return this;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public byte[] getImg() {
        return img;
    }

    public AlumniProfile img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public AlumniProfile imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public AlumniProfile tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean isActive() {
        return active;
    }

    public AlumniProfile active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlumniProfile)) {
            return false;
        }
        return id != null && id.equals(((AlumniProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlumniProfile{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fathersName='" + getFathersName() + "'" +
            ", mothersName='" + getMothersName() + "'" +
            ", currentTerm=" + getCurrentTerm() +
            ", joiningAcademicYear='" + getJoiningAcademicYear() + "'" +
            ", exitAcademicYear='" + getExitAcademicYear() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", aadhaar='" + getAadhaar() + "'" +
            ", dob='" + getDob() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
