package com.tasklytic.taskservice.service;

import com.tasklytic.taskservice.constants.Constants.TaskNotFoundException;
import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.model.TaskEntity;
import com.tasklytic.taskservice.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    //Get all tasks
    public List<TaskDTO> getAllTasks() {
        List<TaskEntity> tasks = taskRepository.findAll();  // Fetch all tasks from the database
        return tasks.stream()  // Convert each TaskEntity to TaskDTO
                    .map(task -> modelMapper.map(task, TaskDTO.class))
                    .collect(Collectors.toList());
    }


    // Get task by ID
    
    public TaskDTO getTaskById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with ID %d not found.", id)));
        return modelMapper.map(task, TaskDTO.class);
    }

    // Create a new task
    public void createTask(TaskDTO taskDTO) {
        TaskEntity taskEntity = modelMapper.map(taskDTO, TaskEntity.class);
        taskRepository.save(taskEntity);
    }

    // Update an existing task
    public void updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with ID %d not found.", id)));
        modelMapper.map(taskDTO, taskEntity);
        taskRepository.save(taskEntity);
    }

    // Delete task
    public void deleteTask(Long id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with ID %d not found.", id)));
        taskRepository.delete(taskEntity);
    }
}
