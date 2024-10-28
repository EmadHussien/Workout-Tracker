package com.emadSolutions.workout_tracker.DTOs;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutSessionDTO {
    @NotNull(message = "User ID must not be null")
    private Long userId;
    @NotNull(message = "Plan ID must not be null")
    private Long planId;
    @NotNull(message = "Schedule date must not be null")
    @Future(message = "Schedule date must be in the future" )
    private LocalDateTime scheduledAt;
    @Min(value = 5, message = "Duration must be at least 5 minutes")
    private Float duration;

}
