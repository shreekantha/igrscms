package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.myriadquest.kreiscms.domain.enumeration.DegreeOrDeptType;
import com.myriadquest.kreiscms.domain.enumeration.DeptSubType;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Department} entity.
 */
public class DepartmentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String code;

    @NotNull
    private String alias;

    private Integer intake;

    @NotNull
    private DegreeOrDeptType type;

    @NotNull
    private DeptSubType subType;


    private Long degreeId;

    private String degreeName;
    
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getIntake() {
        return intake;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    public DegreeOrDeptType getType() {
        return type;
    }

    public void setType(DegreeOrDeptType type) {
        this.type = type;
    }

    public DeptSubType getSubType() {
        return subType;
    }

    public void setSubType(DeptSubType subType) {
        this.subType = subType;
    }

    public Long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Long degreeId) {
        this.degreeId = degreeId;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentDTO)) {
            return false;
        }

        return id != null && id.equals(((DepartmentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", alias='" + getAlias() + "'" +
            ", intake=" + getIntake() +
            ", type='" + getType() + "'" +
            ", subType='" + getSubType() + "'" +
            ", degreeId=" + getDegreeId() +
            ", degreeName='" + getDegreeName() + "'" +
            "}";
    }
}
