package com.tasklytic.taskservice.service;


import com.tasklytic.taskservice.dto.TaskDTO;
import com.tasklytic.taskservice.model.TaskEntity;
import com.tasklytic.taskservice.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//Add new Task
	public TaskDTO createTask(TaskDTO taskDTO) {
		TaskEntity task = modelMapper.map(taskDTO, TaskEntity.class);
		TaskEntity savedTask = taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDTO.class);
	}
	
	//Get all task
	public List<TaskDTO> getAllTasks() {
		return taskRepository.findAll()
				.stream()
				.map(task -> modelMapper.map(task, TaskDTO.class))
				.collect(Collectors.toList());
	}
	
	//Get task by Id
	public TaskDTO getTaskById(Long id) {
		TaskEntity task = taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
		return modelMapper.map(task, TaskDTO.class);
	}
	
	//Update a Task
	public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        TaskEntity existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setStatus(taskDTO.getStatus());
        TaskEntity updatedTask = taskRepository.save(existingTask);
        return modelMapper.map(updatedTask, TaskDTO.class);
    }
	
	//Delete a Task
	  public void deleteTask(Long id) {
	        if (!taskRepository.existsById(id)) {
	            throw new RuntimeException("Task not found with ID: " + id);
	        }
	        taskRepository.deleteById(id);
	    }
	
	
	//To-do: JWT logged in
	// Collaborators after user entity
	//
	
	
	
}
