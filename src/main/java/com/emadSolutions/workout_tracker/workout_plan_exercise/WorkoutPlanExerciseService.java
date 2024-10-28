package com.emadSolutions.workout_tracker.workout_plan_exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class WorkoutPlanExerciseService {
    private final WorkoutPlanExerciseRepository workoutPlanExerciseRepository;

    @Autowired
    public WorkoutPlanExerciseService(WorkoutPlanExerciseRepository workoutPlanExerciseRepository) {
        this.workoutPlanExerciseRepository = workoutPlanExerciseRepository;
    }

    @Transactional
    public void deleteWorkoutPlanExercise(Long workoutPlanExerciseId)
    {
        workoutPlanExerciseRepository.deleteById(workoutPlanExerciseId);
    }
}
