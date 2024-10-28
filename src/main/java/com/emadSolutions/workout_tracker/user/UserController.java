package com.emadSolutions.workout_tracker.user;

import com.emadSolutions.workout_tracker.DTOs.PaginatedWorkoutPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/workouts")
    public PaginatedWorkoutPlans getPaginatedWorkoutPlans(@PathVariable Long userId,
                                                          @RequestParam(defaultValue = "0") int pageNumber,
                                                          @RequestParam(defaultValue = "10") int pageSize) {
        return userService.getPaginatedUserWorkoutPlans(userId, pageNumber, pageSize);
    }

}
