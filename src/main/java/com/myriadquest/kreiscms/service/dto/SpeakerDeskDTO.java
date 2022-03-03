package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.SpeakerDesk} entity.
 */
public class SpeakerDeskDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String note;

    @NotNull
    private String imgLink;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpeakerDeskDTO)) {
            return false;
        }

        return id != null && id.equals(((SpeakerDeskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpeakerDeskDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", img='" + getImg() + "'" +
            "}";
    }
}
