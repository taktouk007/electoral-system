package com.kyubi.digital.service.mapper;


import com.kyubi.digital.domain.*;
import com.kyubi.digital.service.dto.UserAppDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserApp} and its DTO {@link UserAppDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserAppMapper extends EntityMapper<UserAppDTO, UserApp> {

    @Mapping(source = "internalUser.id", target = "internalUserId")
    UserAppDTO toDto(UserApp userApp);

    @Mapping(source = "internalUserId", target = "internalUser")
    @Mapping(target = "electionsMades", ignore = true)
    @Mapping(target = "removeElectionsMade", ignore = true)
    UserApp toEntity(UserAppDTO userAppDTO);

    default UserApp fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApp userApp = new UserApp();
        userApp.setId(id);
        return userApp;
    }
}
