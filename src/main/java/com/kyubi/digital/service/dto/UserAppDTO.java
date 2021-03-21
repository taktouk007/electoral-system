package com.kyubi.digital.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kyubi.digital.domain.UserApp} entity.
 */
public class UserAppDTO implements Serializable {
    
    private Long id;

    private String city;

    private String region;

    private String country;


    private Long internalUserId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getInternalUserId() {
        return internalUserId;
    }

    public void setInternalUserId(Long userId) {
        this.internalUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAppDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAppDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAppDTO{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", country='" + getCountry() + "'" +
            ", internalUserId=" + getInternalUserId() +
            "}";
    }
}
