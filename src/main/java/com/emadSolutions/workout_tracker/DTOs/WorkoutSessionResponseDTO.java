package com.emadSolutions.workout_tracker.DTOs;


import com.emadSolutions.workout_tracker.workout_session.WorkoutSession;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkoutSessionResponseDTO {

    private Long id;
    private String planName;
    private Float Duration;
    private WorkoutSession.Status status;
    private LocalDateTime scheduledAt ;
}
