package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.myriadquest.kreiscms.domain.enumeration.GraduationType;
import com.myriadquest.kreiscms.domain.enumeration.DegreeOrDeptType;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Degree} entity.
 */
public class DegreeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String alias;

    @NotNull
    private GraduationType graduationType;

    @NotNull
    private DegreeOrDeptType type;

    @NotNull
    private Integer numOfYears;

    private Boolean multiBatch;

    
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public GraduationType getGraduationType() {
        return graduationType;
    }

    public void setGraduationType(GraduationType graduationType) {
        this.graduationType = graduationType;
    }

    public DegreeOrDeptType getType() {
        return type;
    }

    public void setType(DegreeOrDeptType type) {
        this.type = type;
    }

    public Integer getNumOfYears() {
        return numOfYears;
    }

    public void setNumOfYears(Integer numOfYears) {
        this.numOfYears = numOfYears;
    }

    public Boolean isMultiBatch() {
        return multiBatch;
    }

    public void setMultiBatch(Boolean multiBatch) {
        this.multiBatch = multiBatch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DegreeDTO)) {
            return false;
        }

        return id != null && id.equals(((DegreeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DegreeDTO{" +
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
