package com.emadSolutions.workout_tracker.workout_plan_exercise;

import com.emadSolutions.workout_tracker.exercise.Exercise;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "workout_plans_exercises")

public class WorkoutPlanExercise {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sets")
    private int sets;

    @Column(name = "repetitions")
    private int repetitions;

    @Column(name = "weight")
    private int weight;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name = "planid")
    private WorkoutPlan workoutPlan;


    @ManyToOne(fetch = FetchType.EAGER ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name = "exerciseid")
    private Exercise exercise;


    // relations
    // helper functions
    // args constructor

}
