package com.myriadquest.kreiscms.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.StudentProfile} entity.
 */
public class StudentProfileDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String firstName;

    private String lastName;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    private String fathersName;

    private String mothersName;

    private Integer currentTerm;

    private String joiningAcademicYear;

    private String exitAcademicYear;

    @NotNull
    @Size(min = 10, max = 10)
    private String mobile;

    private String email;

    @Size(min = 12, max = 12)
    private String aadhaar;

    @NotNull
    private LocalDate dob;

    private String imgLink;

    private String tenantId;

    private Boolean active;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public Integer getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(Integer currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getJoiningAcademicYear() {
        return joiningAcademicYear;
    }

    public void setJoiningAcademicYear(String joiningAcademicYear) {
        this.joiningAcademicYear = joiningAcademicYear;
    }

    public String getExitAcademicYear() {
        return exitAcademicYear;
    }

    public void setExitAcademicYear(String exitAcademicYear) {
        this.exitAcademicYear = exitAcademicYear;
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

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentProfileDTO)) {
            return false;
        }

        return id != null && id.equals(((StudentProfileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentProfileDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", img='" + getImg() + "'" +
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
            ", tenantId='" + getTenantId() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
