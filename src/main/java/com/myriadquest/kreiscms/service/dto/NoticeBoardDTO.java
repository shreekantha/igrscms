package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.NoticeBoard} entity.
 */
public class NoticeBoardDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private String details;

    @NotNull
    private Boolean active;

    private String tenantId;

    
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (!(o instanceof NoticeBoardDTO)) {
            return false;
        }

        return id != null && id.equals(((NoticeBoardDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeBoardDTO{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", active='" + isActive() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
