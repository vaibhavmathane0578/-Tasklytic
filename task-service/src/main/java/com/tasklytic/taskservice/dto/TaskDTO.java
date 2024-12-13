package com.tasklytic.taskservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
	private Long id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
}
