package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.myriadquest.kreiscms.config.TenantContext;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SslcResult.
 */
@Entity
@Table(name = "sslc_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SslcResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @NotNull
    @Column(name = "quality_result", nullable = false)
    private Double qualityResult;

    @NotNull
    @Column(name = "top_result", nullable = false)
    private Double topResult;

    @Column(name = "tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public SslcResult academicYear(String academicYear) {
        this.academicYear = academicYear;
        return this;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Double getQualityResult() {
        return qualityResult;
    }

    public SslcResult qualityResult(Double qualityResult) {
        this.qualityResult = qualityResult;
        return this;
    }

    public void setQualityResult(Double qualityResult) {
        this.qualityResult = qualityResult;
    }

    public Double getTopResult() {
        return topResult;
    }

    public SslcResult topResult(Double topResult) {
        this.topResult = topResult;
        return this;
    }

    public void setTopResult(Double topResult) {
        this.topResult = topResult;
    }

    public String getTenantId() {
        return tenantId;
    }

    public SslcResult tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public void setTenantId(String tenantId) {
    	  this.tenantId = TenantContext.getCurrentTenant();
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SslcResult)) {
            return false;
        }
        return id != null && id.equals(((SslcResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SslcResult{" +
            "id=" + getId() +
            ", academicYear='" + getAcademicYear() + "'" +
            ", qualityResult=" + getQualityResult() +
            ", topResult=" + getTopResult() +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
