package com.tasklytic.collaborationservice.repository;

import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {
    List<ChatMessageEntity> findBySessionId(Long sessionId);
}
