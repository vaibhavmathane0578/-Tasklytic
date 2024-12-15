package com.tasklytic.taskservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklytic.taskservice.model.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
	List<TaskEntity> findByAssigneeId(Long assigneeId);

	List<TaskEntity> findByStatus(String status);

	List<TaskEntity> findByPriority(String priority);
}
