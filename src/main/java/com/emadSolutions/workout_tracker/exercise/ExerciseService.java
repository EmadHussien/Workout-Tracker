package com.emadSolutions.workout_tracker.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    private ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise getExercise(Long exerciseId)
    {
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Did not find exercise with id - " + exerciseId));
    }
}
