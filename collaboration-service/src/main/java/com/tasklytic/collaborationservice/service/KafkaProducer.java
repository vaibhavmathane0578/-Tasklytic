package com.tasklytic.collaborationservice.service;

import com.tasklytic.collaborationservice.dto.ChatMessageDTO;
import com.tasklytic.collaborationservice.dto.CollaborationSessionDTO;

public interface KafkaProducer {

    // Publish collaboration session creation to Kafka topic
    void sendCollaborationSessionEvent(CollaborationSessionDTO collaborationSessionDTO);

    // Publish chat message to Kafka topic
    void sendChatMessageEvent(ChatMessageDTO chatMessageDTO);
}
