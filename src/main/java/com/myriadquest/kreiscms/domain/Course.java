package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.myriadquest.kreiscms.config.TenantContext;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "tenant_id")
    private String tenantId;

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ClassTimeTable> classTimeTables = new HashSet<>();

    @OneToMany(mappedBy = "course")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExamTimeTable> examTimeTables = new HashSet<>();

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

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public Course alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCode() {
        return code;
    }

    public Course code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Course tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = TenantContext.getCurrentTenant();
    }

    public Set<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public Course classTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
        return this;
    }

    public Course addClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.add(classTimeTable);
        classTimeTable.setCourse(this);
        return this;
    }

    public Course removeClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.remove(classTimeTable);
        classTimeTable.setCourse(null);
        return this;
    }

    public void setClassTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }

    public Set<ExamTimeTable> getExamTimeTables() {
        return examTimeTables;
    }

    public Course examTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
        return this;
    }

    public Course addExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.add(examTimeTable);
        examTimeTable.setCourse(this);
        return this;
    }

    public Course removeExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.remove(examTimeTable);
        examTimeTable.setCourse(null);
        return this;
    }

    public void setExamTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", alias='" + getAlias() + "'" +
            ", code='" + getCode() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
