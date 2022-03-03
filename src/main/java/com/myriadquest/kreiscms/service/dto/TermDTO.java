package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Term} entity.
 */
public class TermDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer term;

    @NotNull
    private String title;

    private String description;

    @NotNull
    private String imgUrl;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    @NotNull
    private Integer noOfStudents;


    private Long classTeacherId;

    private String classTeacherFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Integer getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public Long getClassTeacherId() {
        return classTeacherId;
    }

    public void setClassTeacherId(Long userId) {
        this.classTeacherId = userId;
    }

    public String getClassTeacherFirstName() {
        return classTeacherFirstName;
    }

    public void setClassTeacherFirstName(String userFirstName) {
        this.classTeacherFirstName = userFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermDTO)) {
            return false;
        }

        return id != null && id.equals(((TermDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermDTO{" +
            "id=" + getId() +
            ", term=" + getTerm() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", img='" + getImg() + "'" +
            ", noOfStudents=" + getNoOfStudents() +
            ", classTeacherId=" + getClassTeacherId() +
            ", classTeacherFirstName='" + getClassTeacherFirstName() + "'" +
            "}";
    }
}
