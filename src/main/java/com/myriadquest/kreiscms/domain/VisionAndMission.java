package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.myriadquest.kreiscms.config.TenantContext;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VisionAndMission.
 */
@Entity
@Table(name = "vision_and_mission")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VisionAndMission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "vision", nullable = false)
    private String vision;

    @NotNull
    @Column(name = "mission", nullable = false)
    private String mission;

    @Column(name = "tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVision() {
        return vision;
    }

    public VisionAndMission vision(String vision) {
        this.vision = vision;
        return this;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public VisionAndMission mission(String mission) {
        this.mission = mission;
        return this;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getTenantId() {
        return tenantId;
    }

    public VisionAndMission tenantId(String tenantId) {
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
        if (!(o instanceof VisionAndMission)) {
            return false;
        }
        return id != null && id.equals(((VisionAndMission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisionAndMission{" +
            "id=" + getId() +
            ", vision='" + getVision() + "'" +
            ", mission='" + getMission() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
