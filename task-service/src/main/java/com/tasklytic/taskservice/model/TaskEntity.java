package com.tasklytic.taskservice.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(length = 500)
	private String description;
	
	@Column(nullable = true)
	private LocalDate dueDate;
	
	@Column(nullable = false)
	private String status; // To-Do, In-Progress, Completed

}
