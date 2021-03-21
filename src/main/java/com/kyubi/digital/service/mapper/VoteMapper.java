package com.kyubi.digital.service.mapper;


import com.kyubi.digital.domain.*;
import com.kyubi.digital.service.dto.VoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vote} and its DTO {@link VoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ElectionMapper.class})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {

    @Mapping(source = "election.id", target = "electionId")
    VoteDTO toDto(Vote vote);

    @Mapping(source = "electionId", target = "election")
    Vote toEntity(VoteDTO voteDTO);

    default Vote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vote vote = new Vote();
        vote.setId(id);
        return vote;
    }
}
