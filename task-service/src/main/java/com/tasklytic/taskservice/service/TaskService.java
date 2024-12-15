package com.tasklytic.taskservice.service;

import com.tasklytic.taskservice.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    // Create a new task
    TaskDTO createTask(TaskDTO taskDTO);

    // Update an existing task
    TaskDTO updateTask(Long id, TaskDTO taskDTO);

    // Delete a task by ID
    void deleteTask(Long id);

    // Get task by ID
    TaskDTO getTaskById(Long id);

    // Get all tasks assigned to a specific user (Assignee)
    List<TaskDTO> getTasksByAssignee(Long assigneeId);

    // Get all tasks with a specific status (e.g., To-Do, In Progress, Completed)
    List<TaskDTO> getTasksByStatus(String status);

    // Get all tasks with a specific priority (e.g., Low, Medium, High)
    List<TaskDTO> getTasksByPriority(String priority);

    List<TaskDTO> getAllTasks();


}
