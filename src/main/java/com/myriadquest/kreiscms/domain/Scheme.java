package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Scheme.
 */
@Entity
@Table(name = "scheme")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Scheme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "started_academic_year", nullable = false)
    private String startedAcademicYear;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

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

    public Scheme name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartedAcademicYear() {
        return startedAcademicYear;
    }

    public Scheme startedAcademicYear(String startedAcademicYear) {
        this.startedAcademicYear = startedAcademicYear;
        return this;
    }

    public void setStartedAcademicYear(String startedAcademicYear) {
        this.startedAcademicYear = startedAcademicYear;
    }

    public Integer getYear() {
        return year;
    }

    public Scheme year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scheme)) {
            return false;
        }
        return id != null && id.equals(((Scheme) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Scheme{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startedAcademicYear='" + getStartedAcademicYear() + "'" +
            ", year=" + getYear() +
            "}";
    }
}
