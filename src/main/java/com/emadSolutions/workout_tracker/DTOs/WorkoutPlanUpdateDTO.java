package com.emadSolutions.workout_tracker.DTOs;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutPlanUpdateDTO {
    private String planName;
    @Valid
    private List<WorkoutPlanUpdateExerciseDTO> exercises;
}
