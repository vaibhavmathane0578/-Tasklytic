package com.tasklytic.taskservice.service.impl;

import com.tasklytic.shared.constants.Constants;
import com.tasklytic.shared.constants.Constants.Exceptions;
import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.model.TaskEntity;
import com.tasklytic.taskservice.repository.TaskRepository;
import com.tasklytic.taskservice.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        // The @Valid annotation in the controller ensures validation before reaching here
        TaskEntity task = modelMapper.map(taskDTO, TaskEntity.class);
        task.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        task.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        TaskEntity savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDTO.class);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new Exceptions.TaskNotFoundException(String.format(Constants.TASK_NOT_FOUND, id)));
        modelMapper.map(taskDTO, existingTask);  // Map only the updated fields
        existingTask.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        TaskEntity updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDTO.class);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new Exceptions.TaskNotFoundException(String.format(Constants.TASK_NOT_FOUND, id)));
        return modelMapper.map(task, TaskDTO.class);
    }

    @Override
    public List<TaskDTO> getTasksByAssignee(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status).stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByPriority(String priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<TaskEntity> tasks = taskRepository.findAll();
        return tasks.stream()
                    .map(task -> modelMapper.map(task, TaskDTO.class))
                    .collect(Collectors.toList());
    }
}
