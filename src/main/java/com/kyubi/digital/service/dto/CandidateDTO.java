package com.kyubi.digital.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.kyubi.digital.domain.Candidate} entity.
 */
public class CandidateDTO implements Serializable {
    
    private Long id;


    private Long electionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof CandidateDTO)) {
            return false;
        }

        return id != null && id.equals(((CandidateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateDTO{" +
            "id=" + getId() +
            ", electionId=" + getElectionId() +
            "}";
    }
}
