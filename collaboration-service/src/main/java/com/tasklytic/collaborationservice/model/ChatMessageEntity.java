package com.tasklytic.collaborationservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

import java.time.LocalDateTime;

@Document(collection = "chat_messages")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageEntity {

    @Id
    private String id;
    private String collaborationSessionId; 
    private String senderId; 
    private String message; 
    private LocalDateTime sentTimestamp;
    private LocalDateTime readTimestamp;
    private boolean read;
    private boolean delivered;
}
