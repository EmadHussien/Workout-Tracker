package com.emadSolutions.workout_tracker.DTOs;
import com.emadSolutions.workout_tracker.workout_plan.WorkoutPlan;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PaginatedWorkoutPlans {
    private List<WorkoutPlan> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public PaginatedWorkoutPlans(Page<WorkoutPlan> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }

}