package com.emadSolutions.workout_tracker.exercise;


import com.emadSolutions.workout_tracker.workout_plan_exercise.WorkoutPlanExercise;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "exercises")

public class Exercise {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url_photo")
    private String urlPhoto;

    @Column(name = "muscle")
    private String muscle;

    @Column(name = "category")
    private String category;


    @OneToMany(mappedBy = "exercise",fetch= FetchType.LAZY , cascade = CascadeType.ALL)
    private List<WorkoutPlanExercise> workoutPlanExercises;


    public void addWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise) {
        if (this.workoutPlanExercises == null) {
            this.workoutPlanExercises = new ArrayList<>();
        }
        this.workoutPlanExercises.add(workoutPlanExercise);
        workoutPlanExercise.setExercise(this);  // Maintain the bidirectional relationship
    }

    public void removeWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise) {

        this.workoutPlanExercises.remove(workoutPlanExercise);
        workoutPlanExercise.setExercise(null);  // Maintain the bidirectional relationship
    }





    // relations
    // helper functions
    // args constructor
}
