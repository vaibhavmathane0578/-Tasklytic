package com.tasklytic.collaborationservice.controller;

import com.tasklytic.collaborationservice.model.ChatMessageEntity;
import com.tasklytic.collaborationservice.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessageEntity sendMessage(ChatMessageEntity message) {
        message.setTimestamp(Instant.now());
        message.setDelivered(false);
        message.setRead(false);
        return chatMessageRepository.save(message);
    }
}
