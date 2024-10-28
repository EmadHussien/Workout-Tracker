package com.emadSolutions.workout_tracker.workout_plan;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan,Long> {
}
