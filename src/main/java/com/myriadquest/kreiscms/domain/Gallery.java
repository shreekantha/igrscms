package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myriadquest.kreiscms.config.TenantContext;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Gallery.
 */
@Entity
@Table(name = "gallery")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    
//    @NotNull
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @Column(name = "descritpion")
    private String descritpion;

    @Column(name = "tenant_id")
    private String tenantId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "galleries", allowSetters = true)
    private GalleryCat category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Gallery imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public byte[] getImg() {
        return img;
    }

    public Gallery img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Gallery imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public Gallery descritpion(String descritpion) {
        this.descritpion = descritpion;
        return this;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Gallery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = TenantContext.getCurrentTenant();
    }

    public GalleryCat getCategory() {
        return category;
    }

    public Gallery category(GalleryCat galleryCat) {
        this.category = galleryCat;
        return this;
    }

    public void setCategory(GalleryCat galleryCat) {
        this.category = galleryCat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gallery)) {
            return false;
        }
        return id != null && id.equals(((Gallery) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gallery{" +
            "id=" + getId() +
            ", imgUrl='" + getImgUrl() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", descritpion='" + getDescritpion() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
