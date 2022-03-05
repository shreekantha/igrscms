package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.myriadquest.kreiscms.domain.enumeration.GraduationType;

import com.myriadquest.kreiscms.domain.enumeration.DegreeOrDeptType;

/**
 * A Degree.
 */
@Entity
@Table(name = "degree")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Degree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "alias", nullable = false, unique = true)
    private String alias;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "graduation_type", nullable = false)
    private GraduationType graduationType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private DegreeOrDeptType type;

    @NotNull
    @Column(name = "num_of_years", nullable = false)
    private Integer numOfYears;

    @Column(name = "multi_batch")
    private Boolean multiBatch;

    @OneToMany(mappedBy = "degree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "degree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<AcademicCalendar> academicCalendars = new HashSet<>();

    @OneToMany(mappedBy = "degree")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ClassTimeTable> classTimeTables = new HashSet<>();

    @OneToMany(mappedBy = "degree")
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

    public Degree name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public Degree alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public GraduationType getGraduationType() {
        return graduationType;
    }

    public Degree graduationType(GraduationType graduationType) {
        this.graduationType = graduationType;
        return this;
    }

    public void setGraduationType(GraduationType graduationType) {
        this.graduationType = graduationType;
    }

    public DegreeOrDeptType getType() {
        return type;
    }

    public Degree type(DegreeOrDeptType type) {
        this.type = type;
        return this;
    }

    public void setType(DegreeOrDeptType type) {
        this.type = type;
    }

    public Integer getNumOfYears() {
        return numOfYears;
    }

    public Degree numOfYears(Integer numOfYears) {
        this.numOfYears = numOfYears;
        return this;
    }

    public void setNumOfYears(Integer numOfYears) {
        this.numOfYears = numOfYears;
    }

    public Boolean isMultiBatch() {
        return multiBatch;
    }

    public Degree multiBatch(Boolean multiBatch) {
        this.multiBatch = multiBatch;
        return this;
    }

    public void setMultiBatch(Boolean multiBatch) {
        this.multiBatch = multiBatch;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Degree departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Degree addDepartment(Department department) {
        this.departments.add(department);
        department.setDegree(this);
        return this;
    }

    public Degree removeDepartment(Department department) {
        this.departments.remove(department);
        department.setDegree(null);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<AcademicCalendar> getAcademicCalendars() {
        return academicCalendars;
    }

    public Degree academicCalendars(Set<AcademicCalendar> academicCalendars) {
        this.academicCalendars = academicCalendars;
        return this;
    }

    public Degree addAcademicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendars.add(academicCalendar);
        academicCalendar.setDegree(this);
        return this;
    }

    public Degree removeAcademicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendars.remove(academicCalendar);
        academicCalendar.setDegree(null);
        return this;
    }

    public void setAcademicCalendars(Set<AcademicCalendar> academicCalendars) {
        this.academicCalendars = academicCalendars;
    }

    public Set<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public Degree classTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
        return this;
    }

    public Degree addClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.add(classTimeTable);
        classTimeTable.setDegree(this);
        return this;
    }

    public Degree removeClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.remove(classTimeTable);
        classTimeTable.setDegree(null);
        return this;
    }

    public void setClassTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }

    public Set<ExamTimeTable> getExamTimeTables() {
        return examTimeTables;
    }

    public Degree examTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
        return this;
    }

    public Degree addExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.add(examTimeTable);
        examTimeTable.setDegree(this);
        return this;
    }

    public Degree removeExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.remove(examTimeTable);
        examTimeTable.setDegree(null);
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
        if (!(o instanceof Degree)) {
            return false;
        }
        return id != null && id.equals(((Degree) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Degree{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", alias='" + getAlias() + "'" +
            ", graduationType='" + getGraduationType() + "'" +
            ", type='" + getType() + "'" +
            ", numOfYears=" + getNumOfYears() +
            ", multiBatch='" + isMultiBatch() + "'" +
            "}";
    }
}
