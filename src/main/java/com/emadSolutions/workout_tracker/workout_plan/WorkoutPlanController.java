package com.emadSolutions.workout_tracker.workout_plan;

import com.emadSolutions.workout_tracker.DTOs.PaginatedWorkoutPlans;
import com.emadSolutions.workout_tracker.DTOs.SuccessResponse;
import com.emadSolutions.workout_tracker.DTOs.WorkoutPlanDTO;
import com.emadSolutions.workout_tracker.DTOs.WorkoutPlanUpdateDTO;
import com.emadSolutions.workout_tracker.Exceptions.EmptyRequestException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class WorkoutPlanController {
    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }


    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<?> getSingleWorkoutPlan(@PathVariable Long workoutId) {
        System.out.println(workoutId);
        WorkoutPlan workoutPlan = workoutPlanService.getWorkoutPlan(workoutId);
        SuccessResponse<?> response = new
                SuccessResponse<>(SuccessResponse.ResponseStatus.success,workoutPlan);
        return new ResponseEntity<>(workoutPlan,HttpStatus.OK);
    }
    @PostMapping("/workouts")
    public ResponseEntity<WorkoutPlan> createWorkoutPlan(@Valid @RequestBody WorkoutPlanDTO workoutPlanDTO)
    {
        WorkoutPlan workoutPlan = workoutPlanService.createWorkoutPlan(
                workoutPlanDTO.getPlanName(),
                workoutPlanDTO.getUserId(),
                workoutPlanDTO.getExercises()
                );
        return new ResponseEntity<>(workoutPlan, HttpStatus.CREATED);
    }

    @PatchMapping("/workouts/{workoutId}")
    public ResponseEntity<?> updateWorkoutPlan(@Valid @RequestBody WorkoutPlanUpdateDTO workoutPlanUpdateDTO, @PathVariable Long workoutId)
    {
        if(isWorkoutPlanUpdateDTOEmpty(workoutPlanUpdateDTO))
        {
            throw new EmptyRequestException("At least one field must be provided");
        }

        WorkoutPlan workoutPlan = workoutPlanService.updateWorkoutPlan(
                workoutId,
                workoutPlanUpdateDTO
        );

        return new ResponseEntity<>(workoutPlan,HttpStatus.OK);
    }
    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<?> deleteWorkoutPlan(@PathVariable Long workoutId)
    {
        workoutPlanService.deleteWorkoutPlan(workoutId);
        SuccessResponse<?> response =
                new SuccessResponse<>(SuccessResponse.ResponseStatus.success,
                        "Workout Plan with id: "+ workoutId +" is deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isWorkoutPlanUpdateDTOEmpty(WorkoutPlanUpdateDTO dto) {
        return dto.getPlanName() == null && dto.getExercises() == null;

    }

}
