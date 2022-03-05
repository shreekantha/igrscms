package com.myriadquest.kreiscms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.myriadquest.kreiscms.domain.enumeration.Day;

/**
 * A ClassTimeTable.
 */
@Entity
@Table(name = "class_time_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClassTimeTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private Day day;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private User faculty;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private AcademicCalendar academicCalendar;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private Degree degree;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private Term term;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = "classTimeTables", allowSetters = true)
    private Period period;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public ClassTimeTable day(Day day) {
        this.day = day;
        return this;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public User getFaculty() {
        return faculty;
    }

    public ClassTimeTable faculty(User user) {
        this.faculty = user;
        return this;
    }

    public void setFaculty(User user) {
        this.faculty = user;
    }

    public AcademicCalendar getAcademicCalendar() {
        return academicCalendar;
    }

    public ClassTimeTable academicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendar = academicCalendar;
        return this;
    }

    public void setAcademicCalendar(AcademicCalendar academicCalendar) {
        this.academicCalendar = academicCalendar;
    }

    public Degree getDegree() {
        return degree;
    }

    public ClassTimeTable degree(Degree degree) {
        this.degree = degree;
        return this;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Department getDepartment() {
        return department;
    }

    public ClassTimeTable department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Term getTerm() {
        return term;
    }

    public ClassTimeTable term(Term term) {
        this.term = term;
        return this;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Course getCourse() {
        return course;
    }

    public ClassTimeTable course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Period getPeriod() {
        return period;
    }

    public ClassTimeTable period(Period period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassTimeTable)) {
            return false;
        }
        return id != null && id.equals(((ClassTimeTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTable{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            "}";
    }
}
