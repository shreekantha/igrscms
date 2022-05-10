package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.domain.enumeration.ExamType;

/**
 * A ExamTimeTable.
 */
@Entity
@Table(name = "exam_time_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExamTimeTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ExamType examType;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "tenant_id")
    private String tenantId;

    @ManyToOne
    @JsonIgnoreProperties(value = "examTimeTables", allowSetters = true)
    private AcademicCalendar academicCalendar;

    @ManyToOne
    @JsonIgnoreProperties(value = "examTimeTables", allowSetters = true)
    private Degree degree;

    @ManyToOne
    @JsonIgnoreProperties(value = "examTimeTables", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "examTimeTables", allowSetters = true)
    private Term term;

    @ManyToOne
    @JsonIgnoreProperties(value = "examTimeTables", allowSetters = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExamType getExamType() {
        return examType;
    }

    public ExamTimeTable examType(ExamType examType) {
        this.examType = examType;
        return this;
    }

    public void setExamType(ExamType examType) {
        this.examType = examType;
    }

    public LocalDate getDate() {
        return date;
    }

    public ExamTimeTable date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public ExamTimeTable startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public ExamTimeTable endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public ExamTimeTable tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = TenantContext.getCurrentTenant();
    }

    public AcademicCalendar getAcademicCalendar() {
        return academicCalendar;
    }

    public ExamTimeTable academicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendar = academicCalendar;
        return this;
    }

    public void setAcademicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendar = academicCalendar;
    }

    public Degree getDegree() {
        return degree;
    }

    public ExamTimeTable degree(Degree degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Department getDepartment() {
        return department;
    }

    public ExamTimeTable department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Term getTerm() {
        return term;
    }

    public ExamTimeTable term(Term term) {
        this.term = term;
        return this;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Course getCourse() {
        return course;
    }

    public ExamTimeTable course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamTimeTable)) {
            return false;
        }
        return id != null && id.equals(((ExamTimeTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExamTimeTable{" +
            "id=" + getId() +
            ", examType='" + getExamType() + "'" +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
