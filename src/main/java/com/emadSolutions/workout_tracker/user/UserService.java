package com.emadSolutions.workout_tracker.user;

import com.emadSolutions.workout_tracker.DTOs.PaginatedWorkoutPlans;
import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long userId)
    {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Did not find User with id - " + userId));
    }
    public User savedUser(User user)
    {
        return userRepository.save(user);
    }
    public Optional<User> getUserByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public PaginatedWorkoutPlans getPaginatedUserWorkoutPlans(Long userId, int pageNumber, int pageSize) {

        User theUser = getUser(userId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<WorkoutPlan> workoutPlans = userRepository.findMenuItemsByRestaurantId(userId, pageable);
        return new PaginatedWorkoutPlans(workoutPlans);
    }

}
