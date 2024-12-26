package com.tasklytic.collaborationservice.repository;

import com.tasklytic.collaborationservice.model.CollaborationSessionEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollaborationSessionRepository extends CassandraRepository<CollaborationSessionEntity, String> {

    // Custom query to find a collaboration session by its ID
    Optional<CollaborationSessionEntity> findById(String id);

    // Custom query to find a collaboration session by task ID (to relate sessions to tasks)
    Optional<CollaborationSessionEntity> findByTaskId(String taskId);
}
