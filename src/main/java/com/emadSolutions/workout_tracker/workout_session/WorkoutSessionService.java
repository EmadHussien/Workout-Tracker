package com.emadSolutions.workout_tracker.workout_session;

import com.emadSolutions.workout_tracker.DTOs.WorkoutSessionDTO;
import com.emadSolutions.workout_tracker.DTOs.WorkoutSessionMapper;
import com.emadSolutions.workout_tracker.DTOs.WorkoutSessionResponseDTO;
import com.emadSolutions.workout_tracker.user.User;
import com.emadSolutions.workout_tracker.user.UserService;
import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutSessionService {
    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserService userService;
    private final WorkoutPlanService workoutPlanService;

    @Autowired
    public WorkoutSessionService(WorkoutSessionRepository workoutSessionRepository, UserService userService, WorkoutPlanService workoutPlanService) {
        this.workoutSessionRepository = workoutSessionRepository;
        this.userService = userService;
        this.workoutPlanService = workoutPlanService;
    }

    public List<WorkoutSessionResponseDTO> getUserWorkoutSessions(Long userId)
    {
        List<WorkoutSession> workoutSessions = workoutSessionRepository
        .findByUserIdAndScheduledAtAfterOrderByScheduledAtDesc(userId, LocalDateTime.now());

        return workoutSessions.stream()
                .map(WorkoutSessionMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public WorkoutSessionResponseDTO scheduleWorkoutSession(WorkoutSessionDTO workoutSessionDTO)
    {
        User theUser = userService.getUser(workoutSessionDTO.getUserId());
        WorkoutPlan theWorkoutPlan = workoutPlanService.getWorkoutPlan(workoutSessionDTO.getPlanId());
        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setScheduledAt(workoutSessionDTO.getScheduledAt());
        if(workoutSessionDTO.getDuration() != null)
        {
            workoutSession.setDuration(workoutSessionDTO.getDuration());
        }
        workoutSession.setStatus(WorkoutSession.Status.SCHEDULED);

        theUser.addWorkoutSession(workoutSession);
        theWorkoutPlan.addWorkoutSession(workoutSession);
        var savedWorkoutSession =  workoutSessionRepository.save(workoutSession);

        return WorkoutSessionMapper.INSTANCE.toDTO(savedWorkoutSession);
    }
    public long getUserWorkoutSessionReport(Long userId)
    {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)); // Start of the week
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY)); // End of the week
        System.out.println(startOfWeek +" "+ endOfWeek);

        return workoutSessionRepository.countByUserIdAndScheduledAtBetween(
                userId,
                startOfWeek,
                endOfWeek
        );
    }

}
