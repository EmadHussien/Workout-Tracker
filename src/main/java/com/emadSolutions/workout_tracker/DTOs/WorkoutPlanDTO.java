package com.emadSolutions.workout_tracker.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class WorkoutPlanDTO {
    @NotNull(message = "Plan Name Cannot be null")
    @NotBlank(message = "Plan Name Cannot be Blank")
    private String planName;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotEmpty(message = "Exercises list cannot be empty")
    @Valid  // Ensures validation is performed on each WorkoutPlanExerciseDTO
    private List<WorkoutPlanExerciseDTO> exercises;
}
