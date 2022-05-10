package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.VisionAndMission} entity.
 */
public class VisionAndMissionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String vision;

    @NotNull
    private String mission;

    private String tenantId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
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
        if (!(o instanceof VisionAndMissionDTO)) {
            return false;
        }

        return id != null && id.equals(((VisionAndMissionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisionAndMissionDTO{" +
            "id=" + getId() +
            ", vision='" + getVision() + "'" +
            ", mission='" + getMission() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
