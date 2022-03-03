package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Institute.
 */
@Entity
@Table(name = "institute")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Institute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "short_name", nullable = false)
    private String shortName;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @NotNull
    @Column(name = "logo_link", nullable = false)
    private String logoLink;

    
    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @NotNull
    @Column(name = "tag_line", nullable = false)
    private String tagLine;

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

    public Institute name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public Institute shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public Institute address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public Institute email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public Institute contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public Institute logoLink(String logoLink) {
        this.logoLink = logoLink;
        return this;
    }

    public void setLogoLink(String logoLink) {
        this.logoLink = logoLink;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Institute logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Institute logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public String getTagLine() {
        return tagLine;
    }

    public Institute tagLine(String tagLine) {
        this.tagLine = tagLine;
        return this;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Institute)) {
            return false;
        }
        return id != null && id.equals(((Institute) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Institute{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact='" + getContact() + "'" +
            ", logoLink='" + getLogoLink() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            ", tagLine='" + getTagLine() + "'" +
            "}";
    }
}
