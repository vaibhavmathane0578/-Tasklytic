package com.tasklytic.taskservice.controller;


import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/tasks")
public class TaskController {
	
	    @Autowired
	    private TaskService taskService;

	    // Get all tasks
	    @GetMapping
	    public ResponseEntity<List<TaskDTO>> getAllTasks() {
	        return ResponseEntity.ok(taskService.getAllTasks());
	    }

	    // Get task by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
	        return ResponseEntity.ok(taskService.getTaskById(id));
	    }

	    // Create a new task
	    @PostMapping
	    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
	        return ResponseEntity.ok(taskService.createTask(taskDTO));
	    }

	    // Update a task
	    @PutMapping("/{id}")
	    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
	        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
	    }

	    // Delete a task
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
	        taskService.deleteTask(id);
	        return ResponseEntity.noContent().build();
	    }
	}


