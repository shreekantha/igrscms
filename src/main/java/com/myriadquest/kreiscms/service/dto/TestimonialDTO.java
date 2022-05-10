package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Testimonial} entity.
 */
public class TestimonialDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    private String imgLink;

    @NotNull
    private String batchYear;

    
    @Lob
    private String note;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestimonialDTO)) {
            return false;
        }

        return id != null && id.equals(((TestimonialDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestimonialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", img='" + getImg() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", batchYear='" + getBatchYear() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
