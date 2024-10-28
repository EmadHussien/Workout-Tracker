package com.emadSolutions.workout_tracker.user;

import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import com.emadSolutions.workout_tracker.workout_session.WorkoutSession;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "first name cannot be empty")
    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private Integer age;
    @Column(name = "height")
    private Float height;
    @Column(name = "weight")
    private Float weight;
    @Size(min = 8, message = "Username must be at least 8 characters long")
    @NotEmpty(message="Username cannot be empty")
    @Column(name = "username")
    private String username;

    @Transient
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter, and one special character")
    @NotEmpty(message="Password cannot be empty")
    private String password;


    @Column(name ="password")
    private String encryptedPassword;


    @OneToMany(mappedBy = "user",fetch= FetchType.LAZY , cascade = CascadeType.ALL)
    private List<WorkoutPlan> workoutPlans;


    @OneToMany(mappedBy = "user",fetch= FetchType.LAZY ,  cascade = CascadeType.ALL)
    private List<WorkoutSession> workoutSessions;


    public void addWorkoutSession(WorkoutSession workoutSession)
    {
        if(this.workoutSessions == null)
        {
            this.workoutSessions = new ArrayList<WorkoutSession>();
        }
        workoutSessions.add(workoutSession);
        workoutSession.setUser(this);
    }


    // relations
    // helper functions
    // args constructor

}
