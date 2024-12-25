package com.tasklytic.collaborationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "chat_messages")
public class ChatMessageEntity {

    @Id
    private String id;

    private Long sessionId;
    private Long senderId;
    private String message;
    private Instant timestamp;
    private boolean isDelivered;
    private boolean isRead;
}
