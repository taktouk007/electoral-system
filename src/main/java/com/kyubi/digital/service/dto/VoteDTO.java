package com.kyubi.digital.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.kyubi.digital.domain.Vote} entity.
 */
public class VoteDTO implements Serializable {
    
    private Long id;

    private String candidateId;

    private Instant submissionDate;


    private Long electionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public Instant getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Instant submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteDTO)) {
            return false;
        }

        return id != null && id.equals(((VoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoteDTO{" +
            "id=" + getId() +
            ", candidateId='" + getCandidateId() + "'" +
            ", submissionDate='" + getSubmissionDate() + "'" +
            ", electionId=" + getElectionId() +
            "}";
    }
}
