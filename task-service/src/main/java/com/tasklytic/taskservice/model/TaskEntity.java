package com.tasklytic.taskservice.model;

import java.sql.Date;
import java.sql.Timestamp;

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
	
	@Column(nullable = false)
	private String status;
	
	@Column(nullable = false)
	private String priority;   //Low,  Medium, High
	
	@Column(nullable = true)
	private Date dueDate;
	
	@Column(nullable = false)
	private Long assigneeId;
	
	@Column(nullable = false)
	private Long createdBy;
	
	@Column(nullable = false)
	private Timestamp createdAt;
	
	@Column(nullable = false)
	private Timestamp updatedAt;
	
	// To-Do, In-Progress, Completed

}
