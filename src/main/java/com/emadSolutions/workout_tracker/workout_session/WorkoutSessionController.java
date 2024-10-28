package com.emadSolutions.workout_tracker.workout_session;

import com.emadSolutions.workout_tracker.DTOs.SuccessResponse;
import com.emadSolutions.workout_tracker.DTOs.WorkoutSessionDTO;
import com.emadSolutions.workout_tracker.DTOs.WorkoutSessionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
public class WorkoutSessionController {
    private final WorkoutSessionService workoutSessionService;
    public WorkoutSessionController(WorkoutSessionService workoutSessionService) {
        this.workoutSessionService = workoutSessionService;
    }

    @PostMapping("/workout-sessions")
    public ResponseEntity<?> scheduleWorkoutSession(@Valid @RequestBody WorkoutSessionDTO workoutSessionDTO)
    {
        WorkoutSessionResponseDTO theWorkoutSession = workoutSessionService.scheduleWorkoutSession(workoutSessionDTO);
        SuccessResponse<WorkoutSessionResponseDTO> successResponse = new SuccessResponse <>(
                SuccessResponse.ResponseStatus.success,
                theWorkoutSession);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/workout-sessions/user/{userId}")
    public ResponseEntity<?> getUserWorkoutSessions(@PathVariable Long userId)
    {
        List <WorkoutSessionResponseDTO> workoutSessions  = workoutSessionService.getUserWorkoutSessions(userId);
        SuccessResponse<WorkoutSessionResponseDTO> successResponse = new SuccessResponse<WorkoutSessionResponseDTO>(
                SuccessResponse.ResponseStatus.success
                ,workoutSessions
        );
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/reports/user/{userId}")
    public ResponseEntity<?> getUserWorkoutSessionsReport(@PathVariable Long userId)
    {
        long numOfSessions = workoutSessionService.getUserWorkoutSessionReport(userId);
        SuccessResponse<Object> successResponse = new SuccessResponse <Object>(
                SuccessResponse.ResponseStatus.success
                ,"Number Of Sessions Completed this week is: "+numOfSessions
        );
        successResponse.setData(numOfSessions);
      return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }


}
