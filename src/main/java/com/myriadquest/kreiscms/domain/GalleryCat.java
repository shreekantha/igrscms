package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GalleryCat.
 */
@Entity
@Table(name = "gallery_cat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GalleryCat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "img_link", nullable = false)
    private String imgLink;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @OneToMany(mappedBy = "category")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Gallery> galleries = new HashSet<>();

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

    public GalleryCat name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public GalleryCat description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public GalleryCat imgLink(String imgLink) {
        this.imgLink = imgLink;
        return this;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public byte[] getImg() {
        return img;
    }

    public GalleryCat img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public GalleryCat imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Set<Gallery> getGalleries() {
        return galleries;
    }

    public GalleryCat galleries(Set<Gallery> galleries) {
        this.galleries = galleries;
        return this;
    }

    public GalleryCat addGallery(Gallery gallery) {
        this.galleries.add(gallery);
        gallery.setCategory(this);
        return this;
    }

    public GalleryCat removeGallery(Gallery gallery) {
        this.galleries.remove(gallery);
        gallery.setCategory(null);
        return this;
    }

    public void setGalleries(Set<Gallery> galleries) {
        this.galleries = galleries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalleryCat)) {
            return false;
        }
        return id != null && id.equals(((GalleryCat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleryCat{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
