package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.myriadquest.kreiscms.config.TenantContext;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "tenant_id")
    private String tenantId;

    @OneToMany(mappedBy = "term")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ClassTimeTable> classTimeTables = new HashSet<>();

    @OneToMany(mappedBy = "term")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExamTimeTable> examTimeTables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "terms", allowSetters = true)
    private UserProfile classTeacher;

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

    public String getTenantId() {
        return tenantId;
    }

    public Term tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = TenantContext.getCurrentTenant();
    }

    public Set<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public Term classTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
        return this;
    }

    public Term addClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.add(classTimeTable);
        classTimeTable.setTerm(this);
        return this;
    }

    public Term removeClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.remove(classTimeTable);
        classTimeTable.setTerm(null);
        return this;
    }

    public void setClassTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }

    public Set<ExamTimeTable> getExamTimeTables() {
        return examTimeTables;
    }

    public Term examTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
        return this;
    }

    public Term addExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.add(examTimeTable);
        examTimeTable.setTerm(this);
        return this;
    }

    public Term removeExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.remove(examTimeTable);
        examTimeTable.setTerm(null);
        return this;
    }

    public void setExamTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
    }

    public UserProfile getClassTeacher() {
        return classTeacher;
    }

    public Term classTeacher(UserProfile user) {
        this.classTeacher = user;
        return this;
    }

    public void setClassTeacher(UserProfile user) {
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
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
