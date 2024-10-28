package com.emadSolutions.workout_tracker.DTOs;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutPlanUpdateExerciseDTO {

    @NotNull(message="action Must be provided")
    @Pattern(regexp = "ADD|UPDATE|DELETE", message = "Action must be 'ADD', 'UPDATE', or 'DELETE'")
    private String  action; // "ADD", "UPDATE", or "DELETE"

    @NotNull(message = "Exercise ID must not be null")
    @Min(value = 1, message = "Exercise ID must be a positive number")
    private Long exerciseId;

    private Integer  sets;

    private Integer  repetitions;

    private Integer  weight;

}


