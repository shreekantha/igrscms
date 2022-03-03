package com.myriadquest.kreiscms.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.myriadquest.kreiscms.domain.ContactDetails} entity.
 */
public class ContactDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String address;

    private String email;

    @NotNull
    private String contact;

    private String mapLink;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((ContactDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDetailsDTO{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", contact='" + getContact() + "'" +
            ", mapLink='" + getMapLink() + "'" +
            "}";
    }
}
