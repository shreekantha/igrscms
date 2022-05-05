package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A AcademicCalendar.
 */
@Entity
@Table(name = "academic_calendar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AcademicCalendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "tenant_id")
    private String tenantId;

    @OneToMany(mappedBy = "academicCalendar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ClassTimeTable> classTimeTables = new HashSet<>();

    @OneToMany(mappedBy = "academicCalendar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ExamTimeTable> examTimeTables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "academicCalendars", allowSetters = true)
    private Degree degree;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public AcademicCalendar startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public AcademicCalendar endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public AcademicCalendar academicYear(String academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Boolean isActive() {
        return active;
    }

    public AcademicCalendar active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTenantId() {
        return tenantId;
    }

    public AcademicCalendar tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Set<ClassTimeTable> getClassTimeTables() {
        return classTimeTables;
    }

    public AcademicCalendar classTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
        return this;
    }

    public AcademicCalendar addClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.add(classTimeTable);
        classTimeTable.setAcademicCalendar(this);
        return this;
    }

    public AcademicCalendar removeClassTimeTable(ClassTimeTable classTimeTable) {
        this.classTimeTables.remove(classTimeTable);
        classTimeTable.setAcademicCalendar(null);
        return this;
    }

    public void setClassTimeTables(Set<ClassTimeTable> classTimeTables) {
        this.classTimeTables = classTimeTables;
    }

    public Set<ExamTimeTable> getExamTimeTables() {
        return examTimeTables;
    }

    public AcademicCalendar examTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
        return this;
    }

    public AcademicCalendar addExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.add(examTimeTable);
        examTimeTable.setAcademicCalendar(this);
        return this;
    }

    public AcademicCalendar removeExamTimeTable(ExamTimeTable examTimeTable) {
        this.examTimeTables.remove(examTimeTable);
        examTimeTable.setAcademicCalendar(null);
        return this;
    }

    public void setExamTimeTables(Set<ExamTimeTable> examTimeTables) {
        this.examTimeTables = examTimeTables;
    }

    public Degree getDegree() {
        return degree;
    }

    public AcademicCalendar degree(Degree degree) {
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
        if (!(o instanceof AcademicCalendar)) {
            return false;
        }
        return id != null && id.equals(((AcademicCalendar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicCalendar{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", academicYear='" + getAcademicYear() + "'" +
            ", active='" + isActive() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
