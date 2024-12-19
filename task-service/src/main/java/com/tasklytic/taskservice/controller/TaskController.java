package com.tasklytic.taskservice.controller;

import com.tasklytic.shared.constants.Constants;
import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.service.TaskService;

import jakarta.validation.Valid;

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

    // Get tasks by Assignee
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<TaskDTO>> getTasksByAssignee(@PathVariable Long assigneeId) {
        List<TaskDTO> tasks = taskService.getTasksByAssignee(assigneeId);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(@PathVariable String status) {
        List<TaskDTO> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by Priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(@PathVariable String priority) {
        List<TaskDTO> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    // Create new task
    @PostMapping("/new")
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        taskService.createTask(taskDTO);
        return ResponseEntity.ok(Constants.TASK_CREATED);
    }

    // Update an existing task
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id,@Valid @RequestBody TaskDTO taskDTO) {
        taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(Constants.TASK_UPDATED);
    }

    // Delete task by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(Constants.TASK_DELETED);
    }
}
