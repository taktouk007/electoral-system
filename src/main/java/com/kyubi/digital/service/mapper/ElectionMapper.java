package com.kyubi.digital.service.mapper;


import com.kyubi.digital.domain.*;
import com.kyubi.digital.service.dto.ElectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Election} and its DTO {@link ElectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserAppMapper.class})
public interface ElectionMapper extends EntityMapper<ElectionDTO, Election> {

    @Mapping(source = "userApp.id", target = "userAppId")
    ElectionDTO toDto(Election election);

    @Mapping(target = "votes", ignore = true)
    @Mapping(target = "removeVotes", ignore = true)
    @Mapping(target = "candidates", ignore = true)
    @Mapping(target = "removeCandidates", ignore = true)
    @Mapping(source = "userAppId", target = "userApp")
    Election toEntity(ElectionDTO electionDTO);

    default Election fromId(Long id) {
        if (id == null) {
            return null;
        }
        Election election = new Election();
        election.setId(id);
        return election;
    }
}
