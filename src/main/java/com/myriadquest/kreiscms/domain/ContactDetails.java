package com.myriadquest.kreiscms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.myriadquest.kreiscms.config.TenantContext;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContactDetails.
 */
@Entity
@Table(name = "contact_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "map_link")
    private String mapLink;

    @Column(name = "tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public ContactDetails address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public ContactDetails email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public ContactDetails contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMapLink() {
        return mapLink;
    }

    public ContactDetails mapLink(String mapLink) {
        this.mapLink = mapLink;
        return this;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    public String getTenantId() {
        return tenantId;
    }

    public ContactDetails tenantId(String tenantId) {
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
        if (!(o instanceof ContactDetails)) {
            return false;
        }
        return id != null && id.equals(((ContactDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDetails{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact='" + getContact() + "'" +
            ", mapLink='" + getMapLink() + "'" +
            ", tenantId='" + getTenantId() + "'" +
            "}";
    }
}
