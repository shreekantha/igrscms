package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.Vision} entity.
 */
public class VisionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String detail;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisionDTO)) {
            return false;
        }

        return id != null && id.equals(((VisionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisionDTO{" +
            "id=" + getId() +
            ", detail='" + getDetail() + "'" +
            "}";
    }
}
