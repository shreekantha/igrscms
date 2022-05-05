package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.myriadquest.kreiscms.domain.enumeration.TimeTableGenType;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.ClassTimeTableConfig} entity.
 */
public class ClassTimeTableConfigDTO implements Serializable {
    
    private Long id;

    @NotNull
    private TimeTableGenType timeTableGenType;

    private String tenantId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeTableGenType getTimeTableGenType() {
        return timeTableGenType;
    }

    public void setTimeTableGenType(TimeTableGenType timeTableGenType) {
        this.timeTableGenType = timeTableGenType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassTimeTableConfigDTO)) {
            return false;
        }

        return id != null && id.equals(((ClassTimeTableConfigDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTableConfigDTO{" +
            "id=" + getId() +
            ", timeTableGenType='" + getTimeTableGenType() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
