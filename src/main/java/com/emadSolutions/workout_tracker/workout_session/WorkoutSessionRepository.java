package com.emadSolutions.workout_tracker.workout_session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession,Long> {


    List<WorkoutSession> findByUserIdAndScheduledAtAfterOrderByScheduledAtDesc(Long userId, LocalDateTime now);
    long countByUserIdAndScheduledAtBetween(Long userId, LocalDateTime startOfWeek, LocalDateTime endOfWeek);

}
