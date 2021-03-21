package com.kyubi.digital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Vote.
 */
@Entity
@Table(name = "vote")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "submission_date")
    private Instant submissionDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "votes", allowSetters = true)
    private Election election;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public Vote candidateId(String candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

    public Vote submissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
        return this;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Election getElection() {
        return election;
    }

    public Vote election(Election election) {
        this.election = election;
        return this;
    }

    public void setElection(Election election) {
        this.election = election;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vote)) {
            return false;
        }
        return id != null && id.equals(((Vote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vote{" +
            "id=" + getId() +
            ", candidateId='" + getCandidateId() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            "}";
    }
}
