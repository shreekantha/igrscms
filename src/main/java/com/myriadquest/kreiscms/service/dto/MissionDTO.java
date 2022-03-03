package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Mission} entity.
 */
public class MissionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String details;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MissionDTO)) {
            return false;
        }

        return id != null && id.equals(((MissionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MissionDTO{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
