package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SpeakerDesk.
 */
@Entity
@Table(name = "speaker_desk")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SpeakerDesk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "note", nullable = false)
    private String note;

    @NotNull
    @Column(name = "img_link", nullable = false)
    private String imgLink;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

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

    public SpeakerDesk name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public SpeakerDesk note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImgLink() {
        return imgLink;
    }

    public SpeakerDesk imgLink(String imgLink) {
        this.imgLink = imgLink;
        return this;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public byte[] getImg() {
        return img;
    }

    public SpeakerDesk img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public SpeakerDesk imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpeakerDesk)) {
            return false;
        }
        return id != null && id.equals(((SpeakerDesk) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpeakerDesk{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", note='" + getNote() + "'" +
            ", imgLink='" + getImgLink() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            "}";
    }
}
