package com.kyubi.digital.service.mapper;


import com.kyubi.digital.domain.*;
import com.kyubi.digital.service.dto.CandidateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {ElectionMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {

    @Mapping(source = "election.id", target = "electionId")
    CandidateDTO toDto(Candidate candidate);

    @Mapping(target = "figures", ignore = true)
    @Mapping(target = "removeFigures", ignore = true)
    @Mapping(source = "electionId", target = "election")
    Candidate toEntity(CandidateDTO candidateDTO);

    default Candidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(id);
        return candidate;
    }
}
