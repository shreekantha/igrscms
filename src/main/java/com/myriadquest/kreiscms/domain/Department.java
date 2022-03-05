package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.myriadquest.kreiscms.domain.enumeration.DegreeOrDeptType;

import com.myriadquest.kreiscms.domain.enumeration.DeptSubType;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @Column(name = "intake")
    private Integer intake;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DegreeOrDeptType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sub_type", nullable = false)
    private DeptSubType subType;

    @OneToMany(mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ClassTimeTable> classTimeTables = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExamTimeTable> examTimeTables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "departments", allowSetters = true)
    private Degree degree;

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

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Department code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public Department alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getIntake() {
        return intake;
    }

    public Department intake(Integer intake) {
        this.intake = intake;
        return this;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    public DegreeOrDeptType getType() {
        return type;
    }

    public Department type(DegreeOrDeptType type) {
        this.type = type;
        return this;
    }

    public void setType(DegreeOrDeptType type) {
        this.type = type;
    }

    public DeptSubType getSubType() {
        return subType;
    }

    public Department subType(DeptSubType subType) {
        this.subType = subType;
        return this;
    }

    public void setSubType(DeptSubType subType) {
        this.subType = subType;
    }

    public Set<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public Department classTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
        return this;
    }

    public Department addClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.add(classTimeTable);
        classTimeTable.setDepartment(this);
        return this;
    }

    public Department removeClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.remove(classTimeTable);
        classTimeTable.setDepartment(null);
        return this;
    }

    public void setClassTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }

    public Set<ExamTimeTable> getExamTimeTables() {
        return examTimeTables;
    }

    public Department examTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
        return this;
    }

    public Department addExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.add(examTimeTable);
        examTimeTable.setDepartment(this);
        return this;
    }

    public Department removeExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.remove(examTimeTable);
        examTimeTable.setDepartment(null);
        return this;
    }

    public void setExamTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
    }

    public Degree getDegree() {
        return degree;
    }

    public Department degree(Degree degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", alias='" + getAlias() + "'" +
            ", intake=" + getIntake() +
            ", type='" + getType() + "'" +
            ", subType='" + getSubType() + "'" +
            "}";
    }
}
