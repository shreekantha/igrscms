package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Scheme} entity.
 */
public class SchemeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String startedAcademicYear;

    @NotNull
    private Integer year;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartedAcademicYear() {
        return startedAcademicYear;
    }

    public void setStartedAcademicYear(String startedAcademicYear) {
        this.startedAcademicYear = startedAcademicYear;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemeDTO)) {
            return false;
        }

        return id != null && id.equals(((SchemeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startedAcademicYear='" + getStartedAcademicYear() + "'" +
            ", year=" + getYear() +
            "}";
    }
}
