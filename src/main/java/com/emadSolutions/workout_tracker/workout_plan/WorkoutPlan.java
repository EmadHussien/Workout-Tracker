package com.emadSolutions.workout_tracker.workout_plan;

import com.emadSolutions.workout_tracker.comment.Comment;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.workout_plan_exercise.WorkoutPlanExercise;
import com.emadSolutions.workout_tracker.workout_session.WorkoutSession;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "workout_plans")
public class WorkoutPlan {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JoinColumn(name = "userid")
    private User user;

    @OneToMany(mappedBy = "workoutPlan",fetch= FetchType.LAZY , cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    private List<WorkoutSession> workoutSessions;

    @OneToMany(mappedBy = "workoutPlan",fetch= FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "workoutPlan",fetch= FetchType.LAZY , cascade = CascadeType.ALL)
    private List<WorkoutPlanExercise> workoutPlanExercises;


    public void addWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise) {
        if (this.workoutPlanExercises == null) {
            this.workoutPlanExercises = new ArrayList<>();
        }
        this.workoutPlanExercises.add(workoutPlanExercise);
        workoutPlanExercise.setWorkoutPlan(this);  // Maintain the bidirectional relationship
    }

    public void addWorkoutSession(WorkoutSession workoutSession)
    {
        if(this.workoutSessions == null)
        {
            this.workoutSessions = new ArrayList<WorkoutSession>();
        }
        workoutSessions.add(workoutSession);
        workoutSession.setWorkoutPlan(this);
    }

    public void removeWorkoutPlanExercise(WorkoutPlanExercise workoutPlanExercise) {
        this.workoutPlanExercises.remove(workoutPlanExercise);
        workoutPlanExercise.setWorkoutPlan(null);  // Maintain the bidirectional relationship
    }
    public void removeSessionsAssociation()
    {
        // Get all sessions related to the plan and disassociate them
        for (WorkoutSession session : workoutSessions) {
            session.setWorkoutPlan(null);
        }

    }



    // relations
    // helper functions
    // args constructor

}
