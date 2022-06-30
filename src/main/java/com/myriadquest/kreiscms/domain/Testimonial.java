package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Testimonial.
 */
@Entity
@Table(name = "testimonial")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Testimonial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type", nullable = false)
    private String imgContentType;

    @Column(name = "img_link")
    private String imgLink;

    @NotNull
    @Column(name = "batch_year", nullable = false)
    private String batchYear;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "note", nullable = false)
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Testimonial name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImg() {
        return img;
    }

    public Testimonial img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Testimonial imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getImgLink() {
        return imgLink;
    }

    public Testimonial imgLink(String imgLink) {
        this.imgLink = imgLink;
        return this;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public Testimonial batchYear(String batchYear) {
        this.batchYear = batchYear;
        return this;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public String getNote() {
        return note;
    }

    public Testimonial note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Testimonial)) {
            return false;
        }
        return id != null && id.equals(((Testimonial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Testimonial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", batchYear='" + getBatchYear() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
