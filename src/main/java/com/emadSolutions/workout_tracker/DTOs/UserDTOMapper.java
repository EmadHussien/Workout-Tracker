package com.emadSolutions.workout_tracker.DTOs;

import com.emadSolutions.workout_tracker.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);
    UserDataDTO toDTO(User entity);

}
