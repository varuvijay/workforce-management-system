package com.varun.workforce_management.performance_module.dto;

import com.varun.workforce_management.performance_module.entity.Quarter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PerformanceReviewRequest(
        @NotNull(message = "Quarter is required") Quarter quarter,

        @NotNull(message = "Rating is required") @Min(value = 1, message = "Rating must be at least 1") @Max(value = 5, message = "Rating must be at most 5") Integer rating,

        String comments) {
}
