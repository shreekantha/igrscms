package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Term.
 */
@Entity
@Table(name = "term")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Term implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "term", nullable = false)
    private Integer term;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    
    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

    @NotNull
    @Column(name = "no_of_students", nullable = false)
    private Integer noOfStudents;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User classTeacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTerm() {
        return term;
    }

    public Term term(Integer term) {
        this.term = term;
        return this;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public Term title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Term description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Term imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public byte[] getImg() {
        return img;
    }

    public Term img(byte[] img) {
        this.img = img;
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return imgContentType;
    }

    public Term imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public Integer getNoOfStudents() {
        return noOfStudents;
    }

    public Term noOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
        return this;
    }

    public void setNoOfStudents(Integer noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public User getClassTeacher() {
        return classTeacher;
    }

    public Term classTeacher(User user) {
        this.classTeacher = user;
        return this;
    }

    public void setClassTeacher(User user) {
        this.classTeacher = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Term)) {
            return false;
        }
        return id != null && id.equals(((Term) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Term{" +
            "id=" + getId() +
            ", term=" + getTerm() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", noOfStudents=" + getNoOfStudents() +
            "}";
    }
}
