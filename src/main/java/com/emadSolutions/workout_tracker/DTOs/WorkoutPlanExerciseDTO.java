package com.emadSolutions.workout_tracker.DTOs;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutPlanExerciseDTO {
    @NotNull(message = "Exercise ID cannot be null")
    private Long exerciseId;

    private Integer  sets;

    private Integer  repetitions;

    private Integer  weight;

}
