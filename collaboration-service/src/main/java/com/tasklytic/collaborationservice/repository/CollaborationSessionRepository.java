package com.tasklytic.collaborationservice.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import com.tasklytic.collaborationservice.model.CollaborationSessionEntity;

public interface CollaborationSessionRepository extends CassandraRepository<CollaborationSessionEntity, Long> {

	Optional<CollaborationSessionEntity> findById(String sessionId);
    // Custom query methods if needed
}
