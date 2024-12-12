package com.tasklytic.taskservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasklytic.taskservice.model.TaskEntity;
import com.tasklytic.taskservice.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	//Add new Task
	public TaskEntity createTask(TaskEntity taskEntity) {	
		return taskRepository.save(taskEntity);
	}
	
	//Get all task
	public List<TaskEntity> getAllTasks() {
		return taskRepository.findAll();
	}
	
	//Get task by Id
	public Optional<TaskEntity> getById(Long id) {
		return taskRepository.findById(id);
	}
	
	//Update a Task
	public TaskEntity updateTask(Long id, TaskEntity updatedTask) {
		Optional<TaskEntity> existingTask = taskRepository.findById(id);
		if (existingTask.isPresent()) {
			TaskEntity task = existingTask.get();
			task.setTitle(updatedTask.getTitle());
			task.setDescription(updatedTask.getDescription());
			task.setStatus(updatedTask.getStatus());
			task.setDueDate(updatedTask.getDueDate());
			return taskRepository.save(task);
		}
		throw new RuntimeException("Task not found with Id: "+ id);
		
	}
	
	//Delete a Task
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}
	
	
	
}
