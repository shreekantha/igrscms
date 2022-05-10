package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Gallery} entity.
 */
public class GalleryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String imgUrl;

    
    @Lob
    private byte[] img;

    private String imgContentType;
    private String descritpion;

    private String tenantId;


    private Long categoryId;

    private String categoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long galleryCatId) {
        this.categoryId = galleryCatId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String galleryCatName) {
        this.categoryName = galleryCatName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalleryDTO)) {
            return false;
        }

        return id != null && id.equals(((GalleryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleryDTO{" +
            "id=" + getId() +
            ", imgUrl='" + getImgUrl() + "'" +
            ", img='" + getImg() + "'" +
            ", descritpion='" + getDescritpion() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            ", categoryId=" + getCategoryId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}
