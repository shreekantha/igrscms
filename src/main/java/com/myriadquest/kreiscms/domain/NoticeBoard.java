package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.myriadquest.kreiscms.config.TenantContext;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A NoticeBoard.
 */
@Entity
@Table(name = "notice_board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NoticeBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "details", nullable = false)
    private String details;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public NoticeBoard details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Boolean isActive() {
        return active;
    }

    public NoticeBoard active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTenantId() {
        return tenantId;
    }

    public NoticeBoard tenantId(String tenantId) {
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
        if (!(o instanceof NoticeBoard)) {
            return false;
        }
        return id != null && id.equals(((NoticeBoard) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeBoard{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", active='" + isActive() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
