package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.myriadquest.kreiscms.config.TenantContext;
import com.myriadquest.kreiscms.domain.enumeration.TimeTableGenType;

/**
 * A ClassTimeTableConfig.
 */
@Entity
@Table(name = "class_time_table_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClassTimeTableConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_table_gen_type", nullable = false)
    private TimeTableGenType timeTableGenType;

    @Column(name = "tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeTableGenType getTimeTableGenType() {
        return timeTableGenType;
    }

    public ClassTimeTableConfig timeTableGenType(TimeTableGenType timeTableGenType) {
        this.timeTableGenType = timeTableGenType;
        return this;
    }

    public void setTimeTableGenType(TimeTableGenType timeTableGenType) {
        this.timeTableGenType = timeTableGenType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public ClassTimeTableConfig tenantId(String tenantId) {
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
        if (!(o instanceof ClassTimeTableConfig)) {
            return false;
        }
        return id != null && id.equals(((ClassTimeTableConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassTimeTableConfig{" +
            "id=" + getId() +
            ", timeTableGenType='" + getTimeTableGenType() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
