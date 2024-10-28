package com.emadSolutions.workout_tracker.user;

import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    @Query("SELECT w FROM WorkoutPlan w WHERE w.user.id = :userId")
    Page<WorkoutPlan> findMenuItemsByRestaurantId(@Param("userId") Long restaurantId, Pageable pageable);
}
