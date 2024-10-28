package com.emadSolutions.workout_tracker.workout_plan;

import com.emadSolutions.workout_tracker.DTOs.WorkoutPlanExerciseDTO;
import com.emadSolutions.workout_tracker.DTOs.WorkoutPlanUpdateDTO;
import com.emadSolutions.workout_tracker.DTOs.WorkoutPlanUpdateExerciseDTO;
import com.emadSolutions.workout_tracker.Exceptions.ConflictException;
import com.emadSolutions.workout_tracker.exercise.Exercise;
import com.emadSolutions.workout_tracker.exercise.ExerciseService;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.user.UserService;
import com.emadSolutions.workout_tracker.workout_plan_exercise.WorkoutPlanExercise;
import com.emadSolutions.workout_tracker.workout_plan_exercise.WorkoutPlanExerciseService;
import com.emadSolutions.workout_tracker.workout_session.WorkoutSession;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService {
     // TO EDIT
    private WorkoutPlanRepository workoutPlanRepository;
    private UserService userService;
    private ExerciseService exerciseService;
    private WorkoutPlanExerciseService workoutPlanExerciseService;

    @Autowired
    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository, UserService userService, ExerciseService exerciseService,WorkoutPlanExerciseService workoutPlanExerciseService) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.workoutPlanExerciseService = workoutPlanExerciseService;
    }

    public WorkoutPlan getWorkoutPlan(Long workoutId)
    {
        return workoutPlanRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Did not find workout with id - " + workoutId));
    }

    @Transactional
    public WorkoutPlan createWorkoutPlan(String planName, Long userId, List<WorkoutPlanExerciseDTO> workoutPlanExercisesDTO)
    {
        User theUser = userService.getUser(userId);
        WorkoutPlan theWorkoutPlan = new WorkoutPlan();

        theWorkoutPlan.setPlanName(planName);
        theWorkoutPlan.setUser(theUser);

        List<WorkoutPlanExercise> workoutPlanExercises = new ArrayList<>();
        for (WorkoutPlanExerciseDTO exerciseDto : workoutPlanExercisesDTO)
        {
            WorkoutPlanExercise theWorkoutPlanExercise = new WorkoutPlanExercise();
            theWorkoutPlanExercise.setSets(exerciseDto.getSets());
            theWorkoutPlanExercise.setWeight(exerciseDto.getWeight());
            theWorkoutPlanExercise.setRepetitions(exerciseDto.getRepetitions());
            Exercise theExercise = exerciseService.getExercise(exerciseDto.getExerciseId());
            theExercise.addWorkoutPlanExercise(theWorkoutPlanExercise);
            theWorkoutPlanExercise.setWorkoutPlan(theWorkoutPlan);
            workoutPlanExercises.add(theWorkoutPlanExercise);
        }

        theWorkoutPlan.setWorkoutPlanExercises(workoutPlanExercises);
        return workoutPlanRepository.save(theWorkoutPlan);
    }

    @Transactional
    public WorkoutPlan updateWorkoutPlan(Long workoutId, WorkoutPlanUpdateDTO workoutPlanUpdateDTO)
    {
        WorkoutPlan foundWorkoutPlan = getWorkoutPlan(workoutId);
        if(workoutPlanUpdateDTO.getPlanName() != null)
        {
            foundWorkoutPlan.setPlanName(workoutPlanUpdateDTO.getPlanName());
        }

        if(workoutPlanUpdateDTO.getExercises() != null && !workoutPlanUpdateDTO.getExercises().isEmpty()) {
            for (WorkoutPlanUpdateExerciseDTO exerciseDTO : workoutPlanUpdateDTO.getExercises())
            {
                if(exerciseDTO.getAction().equals("ADD")) {
                    addExerciseToPlan(foundWorkoutPlan, exerciseDTO);
                } else if (exerciseDTO.getAction().equals("UPDATE")) {
                    updateExerciseInPlan(foundWorkoutPlan, exerciseDTO);
                } else if (exerciseDTO.getAction().equals("DELETE")) {
                    deleteExerciseFromPlan(foundWorkoutPlan, exerciseDTO.getExerciseId());
                }
            }

        }
        return workoutPlanRepository.save(foundWorkoutPlan);
    }

   @Transactional
    public void deleteWorkoutPlan(Long planId)
    {
        Optional<WorkoutPlan> planToDelete = workoutPlanRepository.findById(planId);
        if (planToDelete.isPresent()) {
            WorkoutPlan thePlan = planToDelete.get();
            thePlan.removeSessionsAssociation();
            workoutPlanRepository.delete(planToDelete.get());
        } else {
            throw new EntityNotFoundException("Workout plan with ID " + planId + " not found");
        }
    }

    private void addExerciseToPlan(WorkoutPlan workoutPlan, WorkoutPlanUpdateExerciseDTO exerciseUpdate) {

        if (workoutPlan.getWorkoutPlanExercises().size() >= 8) {
            throw new IllegalArgumentException("A workout plan can have a maximum of 8 exercises.");
        }

        WorkoutPlanExercise foundExercise = workoutPlan.getWorkoutPlanExercises().stream()
                .filter(exercise -> exercise.getExercise().getId().equals(exerciseUpdate.getExerciseId()))  // Filter by exercise ID
                .findFirst()  // Find the first match
                .orElse(null);  // Return null if not found

        if(foundExercise != null)
        {
            throw new ConflictException("Exercise with id: " + exerciseUpdate.getExerciseId() + " already exists");
        }

        Exercise exercise = exerciseService.getExercise(exerciseUpdate.getExerciseId());
        WorkoutPlanExercise workoutPlanExercise = new WorkoutPlanExercise();

        Optional.ofNullable(exerciseUpdate.getSets()).ifPresent(workoutPlanExercise::setSets);
        Optional.ofNullable(exerciseUpdate.getRepetitions()).ifPresent(workoutPlanExercise::setRepetitions);
        Optional.ofNullable(exerciseUpdate.getWeight()).ifPresent(workoutPlanExercise::setWeight);

        // bidirectional set
        exercise.addWorkoutPlanExercise(workoutPlanExercise);
        workoutPlan.addWorkoutPlanExercise(workoutPlanExercise);
    }

    private void updateExerciseInPlan(WorkoutPlan workoutPlan, WorkoutPlanUpdateExerciseDTO exerciseUpdate) {
        WorkoutPlanExercise workoutPlanExercise = workoutPlan.getWorkoutPlanExercises().stream()
                .filter(wpe -> wpe.getExercise().getId().equals(exerciseUpdate.getExerciseId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find Exercise with id: "+  exerciseUpdate.getExerciseId()));

        workoutPlanExercise.setSets(exerciseUpdate.getSets());
        workoutPlanExercise.setRepetitions(exerciseUpdate.getRepetitions());
        workoutPlanExercise.setWeight(exerciseUpdate.getWeight());
    }

    private void deleteExerciseFromPlan(WorkoutPlan workoutPlan, Long exerciseId) {
        WorkoutPlanExercise workoutPlanExercise = workoutPlan.getWorkoutPlanExercises().stream()
                .filter(wpe -> wpe.getExercise().getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cannot find Exercise with id: "+  exerciseId));

        Exercise exercise = workoutPlanExercise.getExercise();
        exercise.removeWorkoutPlanExercise(workoutPlanExercise);
        workoutPlan.removeWorkoutPlanExercise(workoutPlanExercise);
        workoutPlanExerciseService.deleteWorkoutPlanExercise(workoutPlanExercise.getId());
    }

}
