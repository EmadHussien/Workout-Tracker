package com.emadSolutions.workout_tracker.DTOs;

import com.emadSolutions.workout_tracker.workout_session.WorkoutSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutSessionMapper {
    WorkoutSessionMapper INSTANCE = Mappers.getMapper(WorkoutSessionMapper.class);
    @Mapping(source = "workoutPlan.planName", target = "planName")
    WorkoutSessionResponseDTO toDTO(WorkoutSession entity);


}
/*
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutSessionMapper {
    WorkoutSessionMapper INSTANCE = Mappers.getMapper(WorkoutSessionMapper.class);

    WorkoutSessionDTO toDTO(WorkoutSessionEntity entity);

    WorkoutSessionEntity toEntity(WorkoutSessionDTO dto);
}

 */