package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.SslcResult} entity.
 */
public class SslcResultDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String academicYear;

    @NotNull
    private Double qualityResult;

    @NotNull
    private Double topResult;

    private String tenantId;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Double getQualityResult() {
        return qualityResult;
    }

    public void setQualityResult(Double qualityResult) {
        this.qualityResult = qualityResult;
    }

    public Double getTopResult() {
        return topResult;
    }

    public void setTopResult(Double topResult) {
        this.topResult = topResult;
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
        if (!(o instanceof SslcResultDTO)) {
            return false;
        }

        return id != null && id.equals(((SslcResultDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SslcResultDTO{" +
            "id=" + getId() +
            ", academicYear='" + getAcademicYear() + "'" +
            ", qualityResult=" + getQualityResult() +
            ", topResult=" + getTopResult() +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
