package com.tasklytic.collaborationservice.repository;

import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessageEntity, String> {

    // Custom query to find all messages in a specific collaboration session
    List<ChatMessageEntity> findByCollaborationSessionId(String collaborationSessionId);

    // Custom query to find messages sent by a specific sender in a session
    List<ChatMessageEntity> findByCollaborationSessionIdAndSenderId(String collaborationSessionId, String senderId);
}
