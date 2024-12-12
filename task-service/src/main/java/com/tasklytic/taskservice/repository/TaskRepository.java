package com.tasklytic.taskservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tasklytic.taskservice.model.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

}
