package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    ProfileDTO toDto(Profile profile);

    @Mapping(source = "userId", target = "user")
    Profile toEntity(ProfileDTO profileDTO);

    default Profile fromId(Long id) {
        if (id == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(id);
        return profile;
    }
}
