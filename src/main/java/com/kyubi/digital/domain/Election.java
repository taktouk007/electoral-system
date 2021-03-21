package com.kyubi.digital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.kyubi.digital.domain.enumeration.Scope;

/**
 * A Election.
 */
@Entity
@Table(name = "election")
public class Election implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "target_function", nullable = false)
    private String targetFunction;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope")
    private Scope scope;

    @Column(name = "city")
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @OneToMany(mappedBy = "election")
    private Set<Vote> votes = new HashSet<>();

    @OneToMany(mappedBy = "election")
    private Set<Candidate> candidates = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "electionsMades", allowSetters = true)
    private UserApp userApp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetFunction() {
        return targetFunction;
    }

    public Election targetFunction(String targetFunction) {
        this.targetFunction = targetFunction;
        return this;
    }

    public void setTargetFunction(String targetFunction) {
        this.targetFunction = targetFunction;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Election startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Election endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Scope getScope() {
        return scope;
    }

    public Election scope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getCity() {
        return city;
    }

    public Election city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public Election region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public Election country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Election votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Election addVotes(Vote vote) {
        this.votes.add(vote);
        vote.setElection(this);
        return this;
    }

    public Election removeVotes(Vote vote) {
        this.votes.remove(vote);
        vote.setElection(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }

    public Election candidates(Set<Candidate> candidates) {
        this.candidates = candidates;
        return this;
    }

    public Election addCandidates(Candidate candidate) {
        this.candidates.add(candidate);
        candidate.setElection(this);
        return this;
    }

    public Election removeCandidates(Candidate candidate) {
        this.candidates.remove(candidate);
        candidate.setElection(null);
        return this;
    }

    public void setCandidates(Set<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Election userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Election)) {
            return false;
        }
        return id != null && id.equals(((Election) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Election{" +
            "id=" + getId() +
            ", targetFunction='" + getTargetFunction() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", scope='" + getScope() + "'" +
            ", city='" + getCity() + "'" +
            ", region='" + getRegion() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
}
