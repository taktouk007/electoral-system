package com.kyubi.digital.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserApp.
 */
@Entity
@Table(name = "user_app")
public class UserApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "userApp")
    private Set<Election> electionsMades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public UserApp city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public UserApp region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public UserApp country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public UserApp internalUser(User user) {
        this.internalUser = user;
        return this;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public Set<Election> getElectionsMades() {
        return electionsMades;
    }

    public UserApp electionsMades(Set<Election> elections) {
        this.electionsMades = elections;
        return this;
    }

    public UserApp addElectionsMade(Election election) {
        this.electionsMades.add(election);
        election.setUserApp(this);
        return this;
    }

    public UserApp removeElectionsMade(Election election) {
        this.electionsMades.remove(election);
        election.setUserApp(null);
        return this;
    }

    public void setElectionsMades(Set<Election> elections) {
        this.electionsMades = elections;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApp)) {
            return false;
        }
        return id != null && id.equals(((UserApp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApp{" +
            "id=" + getId() +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
