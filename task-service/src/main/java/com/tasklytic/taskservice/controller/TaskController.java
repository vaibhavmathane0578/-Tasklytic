package com.tasklytic.taskservice.controller;


import com.tasklytic.taskservice.constants.Constants;
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
	    @GetMapping("/all")
	    public ResponseEntity<List<TaskDTO>> getAllTasks() {
	        List<TaskDTO> taskDTOs = taskService.getAllTasks();
	        return ResponseEntity.ok(taskDTOs);
	    }

	    // Get task by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
	        TaskDTO taskDTO = taskService.getTaskById(id);
	        return ResponseEntity.ok(taskDTO);
	    }

	    // Create new task
	    @PostMapping("/new")
	    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO) {
	        taskService.createTask(taskDTO);
	        return ResponseEntity.ok(Constants.TASK_CREATED);
	    }

	    // Update an existing task
	    @PutMapping("/{id}")
	    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
	        taskService.updateTask(id, taskDTO);
	        return ResponseEntity.ok(Constants.TASK_UPDATED);
	    }

	    // Delete task by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
	        taskService.deleteTask(id);
	        return ResponseEntity.ok(Constants.TASK_DELETED);
	    }	}


