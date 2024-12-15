package com.tasklytic.taskservice.dto;

import java.sql.Date;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    @NotNull(message = "Task title is mandatory")
    @Size(min = 5, max = 100, message = "Task title should be between 5 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String description;

    @NotNull(message = "Task status is mandatory")
    @Pattern(regexp = "^(To-Do|In-Progress|Completed)$", message = "Invalid task status")
    private String status;

    @NotNull(message = "Priority is mandatory")
    @Pattern(regexp = "^(Low|Medium|High)$", message = "Priority must be Low, Medium, or High")
    private String priority;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private Date dueDate;

    @NotNull(message = "Assignee ID is mandatory")
    private Long assigneeId;

    @NotNull(message = "Created By is mandatory")
    private Long createdBy;

    private Long updatedBy;


}
